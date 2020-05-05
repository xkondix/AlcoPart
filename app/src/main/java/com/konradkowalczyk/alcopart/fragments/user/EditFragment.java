package com.konradkowalczyk.alcopart.fragments.user;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.konradkowalczyk.alcopart.R;
import com.konradkowalczyk.alcopart.fragments.main.HelperObj;
import com.konradkowalczyk.alcopart.fragments.main.MainFragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class EditFragment extends Fragment implements View.OnClickListener, ZmienDaneDialog.OnRefreshZmien{

    private Button log,pass,eme;
    private TextView loginText,emailTExt,passwordText;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().setTitle("Twoje dane");
    }

    public EditFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_edit, container, false);


        log = view.findViewById(R.id.zmienNick);
        log.setOnClickListener(this);
        pass = view.findViewById(R.id.hButton);
        pass.setOnClickListener(this);
        eme = view.findViewById(R.id.eButton);
        eme.setOnClickListener(this);
        loginText = view.findViewById(R.id.nickWypis);
        emailTExt = view.findViewById(R.id.emailZmien);
        passwordText = view.findViewById(R.id.hasłoZmien);


        return view;
    }

    @Override
    public void onClick(View v) {

        switch(v.getId()) {
            case R.id.zmienNick:

                ZmienDaneDialog dialog = new ZmienDaneDialog("nick");
                dialog.setTargetFragment(EditFragment.this, 1);
                dialog.show(getFragmentManager(), "Dialog 4");

                break;
            case R.id.hButton:

                ZmienDaneDialog dialog1 = new ZmienDaneDialog("password");
                dialog1.setTargetFragment(EditFragment.this, 1);
                dialog1.show(getFragmentManager(), "Dialog 5");

                break;
            case R.id.eButton:

                ZmienDaneDialog dialog2 = new ZmienDaneDialog("email");
                dialog2.setTargetFragment(EditFragment.this, 1);
                dialog2.show(getFragmentManager(), "Dialog 6");

                break;
        }

    }

    @Override
    public void onStart()
    {
        super.onStart();

        if(User.iflog)
        {
            new UpdateView().execute();
        }
    }

    @Override
    public void respondDialog() {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        new UpdateView().execute();

    }


    private class UpdateView extends AsyncTask<Void,Void,Boolean>
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

        @SuppressLint("WrongThread")
        @Override
        protected Boolean doInBackground(Void... voids) {


            try {

                  emailTExt.setText(user.getEmail());


                DocumentReference dbRef = db.collection("User").document(user.getUid());
                dbRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document.exists()) {

                                loginText.setText(document.get("Nick").toString());
                                passwordText.setText(document.get("password").toString());

                            }
                        } else {


                        }

                    }
                });

                return true;


            }catch (Exception e) {
                return false;
            }
        }

        public void onPostExecute(Boolean success)
        {


        }


    }
}
