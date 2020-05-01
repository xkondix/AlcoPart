package com.konradkowalczyk.alcopart;

import android.database.sqlite.SQLiteException;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 */
public class AlcoRecenzjaFragment extends DialogFragment {

    private float star;
    private int id;
    private String recenzja="";

    public AlcoRecenzjaFragment(float star, int id,String recenzja) {
        this.star = star;
        this.id=id;
        if(recenzja!=null)  this.recenzja=recenzja;

    }

    public AlcoRecenzjaFragment(){}

    private TextView ok, cancel;
    private EditText rec;


    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_alco_recenzja, container, false);


        //widoki
        ok = view.findViewById(R.id.okRec);
        cancel = view.findViewById(R.id.cancelRec);
        rec = view.findViewById(R.id.rec);

        rec.setText((recenzja.trim().length()==0 ? "" : recenzja));

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialog().dismiss();
            }
        });

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //zmienne
                String recenzja = rec.getText().toString();
                new FireRec().execute(star);


                getDialog().dismiss();
            }
        });

        return view;
    }


    private class FireRec extends AsyncTask<Float,Void,Boolean>
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
        protected Boolean doInBackground(Float... floats) {
            final float star = floats[0];


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

                                if (document.get("Recenzja"+id) != null) {
                                    Map map2 = new HashMap<String, Object>();
                                    map2.put("Data",new Timestamp(new Date()));
                                    map2.put("Ocena", String.valueOf(star));
                                    map2.put("Recenzja",rec.getText().toString().trim());
                                    map2.put("Id",id);
                                    map.put("Recenzja"+id,map2);

                                    db.collection("User").document(user.getUid())
                                            .update(map);

                                } else {
                                    Map map2 = new HashMap<String, Object>();
                                    map2.put("Data",new Timestamp(new Date()));
                                    map2.put("Ocena", String.valueOf(star));
                                    map2.put("Recenzja",rec.getText().toString().trim());
                                    map2.put("Id",id);
                                    map.put("Recenzja"+id,map2);

                                    db.collection("User").document(user.getUid())
                                            .set(map, SetOptions.merge());

                                }
                            } else {

                            }
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
