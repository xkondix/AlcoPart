package com.konradkowalczyk.alcopart.fragments.user;

import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.konradkowalczyk.alcopart.R;

import static androidx.constraintlayout.widget.Constraints.TAG;

/**
 * A simple {@link Fragment} subclass.
 *
 */
public class RegisterFragment extends Fragment implements  View.OnClickListener {

    private FirebaseAuth auch;
    private FirebaseFirestore db;
    private EditText email,password;
    private Button rej;

    public RegisterFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().setTitle("Rejestracja");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_register, container, false);

        rej = view.findViewById(R.id.rejestruj);
        rej.setOnClickListener(this);
        email = view.findViewById(R.id.email);
        password = view.findViewById(R.id.hasło);
        db = FirebaseFirestore.getInstance();
        auch =  FirebaseAuth.getInstance();


        return  view;
    }

    @Override
    public void onClick(View v) {
        if(Patterns.EMAIL_ADDRESS.matcher(email.getText().toString()).matches()
                && !password.getText().toString().isEmpty())
        newUser();
        else
            Toast.makeText(getActivity(),"Złe hasło lub login",Toast.LENGTH_SHORT).show();

    }


    public void newUser()
    {
        auch.createUserWithEmailAndPassword(email.getText().toString().trim(), password.getText().toString().trim())
                .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            final FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser();
                                user.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            Log.i("Success", "Yes");
                                            User.password=password.getText().toString().trim();
                                            User.email=email.getText().toString().trim();
                                            FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                                            ft.replace(R.id.content_frame,new LoginFragment());
                                            ft.commit();
                                            System.out.println("ala ma kota");
                                        } else {
                                            Log.i("Success", "No");
                                        }
                                    }
                                });
                            }
                         else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(getActivity(),"xd",Toast.LENGTH_SHORT).show();
                        }


                    }
                });
    }




}
