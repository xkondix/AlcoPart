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

/**
 * A simple {@link Fragment} subclass.
 */
public class AlcoDialogFragment extends DialogFragment {

    public AlcoDialogFragment() {
        // Required empty public constructor
    }



    public interface OnRespoundSend{
        void respondData(String inputProcenty, String input, GregorianCalendar date);
    }

    public OnRespoundSend onRespoundSend;

    private TextView ok,cancel;
    private EditText procenty,ml;
    private TimePicker timePicker;
    private DatePicker datePicker;


    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_alco_dialog, container, false);

        //widoki
        ok = view.findViewById(R.id.ok);
        cancel = view.findViewById(R.id.cancel);
        procenty = view.findViewById(R.id.procenty);
        ml = view.findViewById(R.id.ml);
        timePicker = view.findViewById(R.id.time);
        datePicker = view.findViewById(R.id.data);


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
                String inputProcenty = procenty.getText().toString();
                String inputMl = ml.getText().toString();
                //date
                int hour;
                int minutes;
                int year = datePicker.getYear();
                int month = datePicker.getMonth();
                int day = datePicker.getDayOfMonth();

                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                    hour = timePicker.getHour();
                    minutes = timePicker.getMinute();
                }
                else
                {
                    hour = timePicker.getCurrentHour();
                    minutes = timePicker.getCurrentMinute();
                }
                GregorianCalendar date = new GregorianCalendar(year,month,day,hour,minutes,0);
                System.out.println(year+"/"+month+"/"+day);
                System.out.println(hour+":"+minutes);


                if(!inputProcenty.equals("") && !inputMl.equals("")){

                    onRespoundSend.respondData(inputProcenty,inputMl,date);
                }


                getDialog().dismiss();
            }
        });

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try{
            onRespoundSend = (OnRespoundSend) getTargetFragment();
        }catch (ClassCastException e){
        }
    }
}


