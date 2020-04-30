package com.konradkowalczyk.alcopart;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.konradkowalczyk.alcopart.fragments.user.User;

import java.util.HashMap;
import java.util.Map;

public class AlcoViewActivity extends AppCompatActivity {

    public static final String EXTRA_DRINKID = "drinkID";
    private RatingBar ratingBar;
    private Button recenzja;
    private int alcoId;
    private String text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alco_view);

        //pobieranie id alko z intencji
        alcoId = (Integer) getIntent().getExtras().get(EXTRA_DRINKID);

        //tworzenie kursora

        SQLiteOpenHelper alcoDatabaseHelper = new AlcoDatabaseHelper(this);
        SQLiteDatabase db = alcoDatabaseHelper.getReadableDatabase();

        //kursory sluza do zapisu i odczytu z bazy SQLite, cos ala obiekty ResultSet z JDBC
        Cursor cursor = db.query("ALCOHOL",
                new String[]{"NAME","TYPE","BRAND","NUMBER","CAPACITY","PERCENT","FAVOURITE","COLLECT","IMAGE_ID"},
                "_id = ?",
                new String[] {Integer.toString(alcoId)},
                null,null,null);


        try {


          if(cursor.moveToFirst()) {
            //pobieranie szczegolowych informacji z kursora
              String name = cursor.getString(0);
              String type = cursor.getString(1);
              String brand = cursor.getString(2);
              String number = cursor.getString(3);
              String capacity = cursor.getString(4);
              String percent = cursor.getString(5);
              boolean favourite = (cursor.getInt(6) == 1);
              boolean collect = (cursor.getInt(7) == 1);
              String photoId = cursor.getString(8);


            //dodawanie wartosci do xml
            TextView n = findViewById(R.id.name);
            n.setText(name);

            TextView t = findViewById(R.id.type);
            t.setText("typ - " + getResources().getString(Integer.parseInt(type)));

            TextView b = findViewById(R.id.brand);
            b.setText("marka - " + brand);

            TextView c = findViewById(R.id.pojemnosc);
            c.setText("pojemnosc - " + capacity+"ml");

            TextView per = findViewById(R.id.percent);
            per.setText("procenty - " + percent+"%");

            TextView k = findViewById(R.id.kod);
            k.setText("kod - " + number);

            TextView a = findViewById(R.id.kolekcja);
            a.setText("wypite - " + (collect ? "tak" : "nie"));

            ImageView pA = findViewById(R.id.photo_alco);
            pA.setImageResource(getResources().getIdentifier(String.valueOf(photoId), "drawable", getPackageName()));
            pA.setContentDescription(name);

            //ustainie CheckBoxa
            CheckBox favouriteBox = findViewById(R.id.favourite);
            favouriteBox.setChecked(favourite);

            cursor.close();
            db.close();

            recenzja = findViewById(R.id.recenzja);
            ratingBar = findViewById(R.id.stars);



        }
        } catch (SQLiteException e)
        {
            Toast toast = Toast.makeText(AlcoViewActivity.this,"Baza danych nie dziala",Toast.LENGTH_SHORT);
            toast.show();

        }

    }

        public void  onFavouriteClicked(View view)
        {
            int alcoId = (Integer) getIntent().getExtras().get(EXTRA_DRINKID);
            new UpdateAlco().execute(alcoId);


        }

        public void onRecenzjaClicked(View view)
        {
            AlcoRecenzjaFragment dialog = new AlcoRecenzjaFragment(ratingBar.getRating(),alcoId,text);
            dialog.show(this.getSupportFragmentManager(), "Dialog3");
        }

        @Override
        public void onStart()
        {
            super.onStart();

            if(User.iflog)
            {
                if(!ratingBar.isEnabled())
                {
                    ratingBar.setIsIndicator(false);
                    recenzja.setEnabled(true);
                }

                new FireRec().execute();
            }
            else
            {
                ratingBar.setIsIndicator(true);
                recenzja.setEnabled(false);
            }

        }


    private class UpdateAlco extends AsyncTask<Integer,Void,Boolean>
    {
        private ContentValues alcoValues;

        @Override
        protected void onPreExecute()
        {
            CheckBox favourite = findViewById(R.id.favourite);
            alcoValues = new ContentValues();
            alcoValues.put("FAVOURITE",favourite.isChecked());
        }

        @Override
        protected Boolean doInBackground(Integer... integers) {
            int alcoId = integers[0];

            SQLiteOpenHelper alcoDatabaseHelper = new AlcoDatabaseHelper(AlcoViewActivity.this);

            try {
                SQLiteDatabase db = alcoDatabaseHelper.getWritableDatabase();
                db.update("ALCOHOL",alcoValues,"_id = ?",
                        new String[]{Integer.toString(alcoId)});

                db.close();
                return true;


            }catch (SQLiteException e) {
                return false;
            }
        }

        public void onPostExecute(Boolean success)
        {
            if(!success)
            {

                Toast toast = Toast.makeText(AlcoViewActivity.this,"Baza danych nie dziala",Toast.LENGTH_SHORT);
                toast.show();
            }
        }
    }


    private class FireRec extends AsyncTask<Void,Void,Boolean>
    {
        private FirebaseAuth auth;
        private FirebaseUser user;
        private FirebaseFirestore db;

        @Override
        protected void onPreExecute()
        {
            auth = FirebaseAuth.getInstance();
            user = auth.getCurrentUser();
            db = FirebaseFirestore.getInstance();


        }

        @Override
        protected Boolean doInBackground(Void... voids) {


            try {
                FirebaseFirestore rootRef = FirebaseFirestore.getInstance();
                DocumentReference docIdRef = rootRef.collection("User").document(user.getUid());
                docIdRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document.exists()) {
                                Map map = new HashMap<String, Object>();

                                if (document.get("Recenzja"+alcoId) != null) {

                                    map = task.getResult().getData();
                                    Map map2 = (Map) map.get("Recenzja"+alcoId);
                                    ratingBar.setRating( Float.parseFloat((String) map2.get("Ocena")));
                                    text = (String) map2.get("Recenzja");


                                }


                                }
                            } else {

                                text="";

                            }
                        }
                });

                return true;


            }catch (SQLiteException e) {
                return false;
            }
        }

        public void onPostExecute(Boolean success)
        {

        }
    }


}





