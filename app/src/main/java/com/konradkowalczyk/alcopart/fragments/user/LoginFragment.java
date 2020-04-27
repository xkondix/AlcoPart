package com.konradkowalczyk.alcopart.fragments.user;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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
import com.konradkowalczyk.alcopart.R;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends Fragment implements View.OnClickListener {

    private FirebaseAuth auch;
    private FirebaseFirestore db;
    private EditText email, password;
    private Button log;

    public LoginFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().setTitle("Logowanie");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_login, container, false);


        log = view.findViewById(R.id.loguj);
        log.setOnClickListener(this);
        email = view.findViewById(R.id.email2);
        password = view.findViewById(R.id.hasło2);
        db = FirebaseFirestore.getInstance();
        auch = FirebaseAuth.getInstance();

        return view;
    }

    @Override
    public void onClick(View v) {
        if (!User.iflog) {
            login();
        }


    }


    public void login() {

            auch.signInWithEmailAndPassword(email.getText().toString().trim(), password.getText().toString().trim())
                    .addOnCompleteListener(getActivity(), new OnCompleteListener() {
                        @Override
                        public void onComplete(@NonNull Task task) {
                            final FirebaseUser user = auch.getCurrentUser();
                            if (user.isEmailVerified()) {

                                FirebaseFirestore rootRef = FirebaseFirestore.getInstance();
                                DocumentReference docIdRef = rootRef.collection("User").document(user.getUid());
                                docIdRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                        if (task.isSuccessful()) {
                                            DocumentSnapshot document = task.getResult();
                                            if (document.exists()) {
                                                Toast.makeText(getActivity(),"Zalogowano",Toast.LENGTH_SHORT).show();
                                            } else {
                                                Map map = new HashMap<String, Object>();
                                                map.put("Nick", "Konrad");
                                                map.put("Email", email.getText().toString().trim());
                                                map.put("password", password.getText().toString().trim());
                                                map.put("Data", new Timestamp(new Date()));
                                                db.collection("User").document(user.getUid()).set(map);
                                                Toast.makeText(getActivity(),"Gratuluje 1 loginu",Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    }
                                });

                                User.iflog = true;


                            }
                            else
                            {
                                auch.signOut();
                                Toast.makeText(getActivity(),"Potwierdz email",Toast.LENGTH_SHORT).show();

                            }
                        }
                    });
        }

    }


