package com.konradkowalczyk.alcopart.fragments.user;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import com.konradkowalczyk.alcopart.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class NickDialogFragment extends DialogFragment {

    public NickDialogFragment() {
        // Required empty public constructor
    }



    public interface OnSend{
        void respondData(String nick);
    }


    private TextView ok,cancel;
    private EditText nick;
    private OnSend onSend;





    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_nick_dialog, container, false);


        //widoki
        ok = view.findViewById(R.id.okNick);
        cancel = view.findViewById(R.id.cancelNick);
        nick = view.findViewById(R.id.nick);

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialog().dismiss();
            }
        });

        if (nick.getText().toString().trim().equalsIgnoreCase("")) {
            nick.setError("Nick nie może być pusty");
        }

        ok.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //zmienne
                    if(!nick.getText().toString().trim().equalsIgnoreCase("")){
                    String pseudo = nick.getText().toString();
                    onSend.respondData(pseudo);
                    getDialog().dismiss();}
                }
            });


        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try{
            onSend = (NickDialogFragment.OnSend) getTargetFragment();
        }catch (ClassCastException e){
        }
    }
}


