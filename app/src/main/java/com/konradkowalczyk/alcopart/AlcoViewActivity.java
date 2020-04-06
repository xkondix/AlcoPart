package com.konradkowalczyk.alcopart;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class AlcoViewActivity extends AppCompatActivity {

    public static final String EXTRA_DRINKID = "drinkID";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alco_view);

        //pobieranie id alko z intencji
        int alcoId = (Integer) getIntent().getExtras().get(EXTRA_DRINKID);

        //tworzenie kursora

        SQLiteOpenHelper alcoDatabaseHelper = new AlcoDatabaseHelper(this);
        SQLiteDatabase db = alcoDatabaseHelper.getReadableDatabase();

        //kursory sluza do zapisu i odczytu z bazy SQLite, cos ala obiekty ResultSet z JDBC
        Cursor cursor = db.query("ALCOHOL",
                new String[]{"NAME","TYPE","BRAND","NUMBER","CAPACITY","FAVOURITE","COLLECT","IMAGE_ID"},
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
              boolean favourite = (cursor.getInt(5) == 1);
              boolean collect = (cursor.getInt(6) == 1);
              int photoId = cursor.getInt(7);


            //dodawanie wartosci do xml
            TextView n = findViewById(R.id.name);
            n.setText(name);

            TextView t = findViewById(R.id.type);
            t.setText("typ - " + type);

            TextView b = findViewById(R.id.brand);
            b.setText("marka - " + brand);

            TextView c = findViewById(R.id.pojemnosc);
            c.setText("pojemnosc - " + capacity);

            TextView k = findViewById(R.id.kod);
            k.setText("kod - " + number);

            TextView a = findViewById(R.id.kolekcja);
            a.setText("wypite - " + (collect ? "tak" : "nie"));

  //          ImageView pA = findViewById(R.id.photo_alco);
//            pA.setImageResource(photoId);
    //        pA.setContentDescription(name);

            //ustainie CheckBoxa
            CheckBox favouriteBox = findViewById(R.id.favourite);
            favouriteBox.setChecked(favourite);

            cursor.close();
            db.close();


        }
        } catch (SQLiteException e)
        {
            Toast toast = Toast.makeText(this,"Baza danych nie dziala",Toast.LENGTH_SHORT);
            toast.show();

        }

    }

    public void  onFavouriteClicked(View view)
    {
        int alcoId = (Integer) getIntent().getExtras().get(EXTRA_DRINKID);
        new UpdateAlco().execute(alcoId);


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

}





