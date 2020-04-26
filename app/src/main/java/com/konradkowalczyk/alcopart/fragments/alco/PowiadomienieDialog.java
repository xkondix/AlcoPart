package com.konradkowalczyk.alcopart.fragments.alco;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import com.konradkowalczyk.alcopart.R;

import java.util.GregorianCalendar;


public class PowiadomienieDialog extends DialogFragment {
    private TextView ok,cancel,text;
    private GregorianCalendar calendar;

    public PowiadomienieDialog(GregorianCalendar calendar) {
        this.calendar=calendar;
    }


    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_powiadomienie_dialog, container, false);

        //widoki
        ok = view.findViewById(R.id.ok);
        cancel = view.findViewById(R.id.cancel);


        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialog().dismiss();
            }
        });

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                getDialog().dismiss();
            }
        });

        return view;
    }



}


