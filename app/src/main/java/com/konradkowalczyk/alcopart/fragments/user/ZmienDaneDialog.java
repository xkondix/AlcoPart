package com.konradkowalczyk.alcopart.fragments.user;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.sqlite.SQLiteException;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;
import com.konradkowalczyk.alcopart.R;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class ZmienDaneDialog extends DialogFragment {

    String zmiana;

    public ZmienDaneDialog() {
        // Required empty public constructor
    }

    public ZmienDaneDialog(String zmianna) {
        this.zmiana=zmianna;
    }



    public interface OnRefreshZmien{
        void respondDialog();
    }


    private TextView ok,cancel;
    private EditText text;
    private OnRefreshZmien onSend;





    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_zmien_dane_dialog, container, false);


        //widoki
        ok = view.findViewById(R.id.okZmienDialog);
        cancel = view.findViewById(R.id.cancelZmienDialog);
        text = view.findViewById(R.id.textZmienDialog);

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialog().dismiss();
            }
        });

        if (text.getText().toString().trim().equalsIgnoreCase("")) {
            text.setError("Pole nie może być puste");
        }

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //zmienne
                if(!text.getText().toString().trim().equalsIgnoreCase("")){
                    String zmienna = text.getText().toString().trim();
                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                    if(zmiana.equals("email"))
                    {
                        new UpdateEmail().execute();
                    }
                    else if(zmiana.equals("nick"))
                    {
                        new UpdateNick().execute();
                    }
                    else if(zmiana.equals("password"))
                    {
                        new UpdatePassword().execute();
                    }
                    getDialog().dismiss();}
            }
        });


        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try{
            onSend = (ZmienDaneDialog.OnRefreshZmien) getTargetFragment();
        }catch (ClassCastException e){
        }
    }


 public class UpdatePassword extends AsyncTask<Void,Void,Boolean>
{
    private FirebaseAuth auth;
    private FirebaseUser user;
    private FirebaseFirestore db;
    private Map<String,Object> map;

    @Override
    protected void onPreExecute()
    {
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        db = FirebaseFirestore.getInstance();
        map = new HashMap<>();


    }

    @SuppressLint("WrongThread")
    @Override
    protected Boolean doInBackground(Void... voids) {

        try {
            user.updatePassword(text.getText().toString().trim());
            map.put("password",text.getText().toString().trim());
            db.collection("User")
                    .document(user.getUid())
                    .update(map);
            onSend.respondDialog();

            return true;

        }catch (SQLiteException e) {
            return false;
        }
    }
    public void onPostExecute(Boolean success)
    {

    }
}


    public class UpdateEmail extends AsyncTask<Void,Void,Boolean>
    {
        private FirebaseAuth auth;
        private FirebaseUser user;
        private FirebaseFirestore db;
        private Map<String,Object> map;

        @Override
        protected void onPreExecute()
        {
            auth = FirebaseAuth.getInstance();
            user = auth.getCurrentUser();
            db = FirebaseFirestore.getInstance();
            map = new HashMap<>();


        }

        @SuppressLint("WrongThread")
        @Override
        protected Boolean doInBackground(Void... voids) {

            try {
                user.updateEmail(text.getText().toString().trim());
                map.put("Email",text.getText().toString().trim());
                db.collection("User")
                        .document(user.getUid())
                        .update(map);
                onSend.respondDialog();

                return true;

            }catch (SQLiteException e) {
                return false;
            }
        }
        public void onPostExecute(Boolean success)
        {

        }
    }

    public class UpdateNick extends AsyncTask<Void,Void,Boolean>
    {
        private FirebaseAuth auth;
        private FirebaseUser user;
        private FirebaseFirestore db;
        private Map<String,Object> map;

        @Override
        protected void onPreExecute()
        {
            auth = FirebaseAuth.getInstance();
            user = auth.getCurrentUser();
            db = FirebaseFirestore.getInstance();
            map = new HashMap<>();


        }

        @SuppressLint("WrongThread")
        @Override
        protected Boolean doInBackground(Void... voids) {

            try {
                map.put("Nick",text.getText().toString().trim());
                db.collection("User")
                        .document(user.getUid())
                        .update(map);
                onSend.respondDialog();

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


