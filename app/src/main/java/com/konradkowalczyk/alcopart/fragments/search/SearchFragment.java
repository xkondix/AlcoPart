package com.konradkowalczyk.alcopart.fragments.search;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.fragment.app.Fragment;

import com.konradkowalczyk.alcopart.AlcoViewActivity;
import com.konradkowalczyk.alcopart.R;


public class SearchFragment extends Fragment implements  View.OnClickListener {


    private EditText nazwa,marka,pojemnosc;
    private Spinner spinner;
    private Button find;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().setTitle("Szukaj alko");


    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_search, container, false);

        nazwa = view.findViewById(R.id.wyszukaj_nazwa);
        marka = view.findViewById(R.id.wyszukaj_marka);
        pojemnosc = view.findViewById(R.id.wyszukaj_pojemnosc);
        spinner = view.findViewById(R.id.wyszukaj_spinner);
        find = view.findViewById(R.id.wyszukaj);
        find.setOnClickListener(this);



        return view;
    }


    @Override
    public void onClick(View v) {

        switch(v.getId())
        {
            case R.id.wyszukaj:
                onClikWyszukaj();
                break;
        }



    }

    public void onClikWyszukaj()
    {

        String getName = nazwa.getText().toString().trim();
        if (getName.matches("")) {
            getName="None";
        }

        String getBrand = marka.getText().toString().trim();
        if (getBrand.matches("")) {
            getBrand="None";
        }

        String getType = spinner.getSelectedItem().toString().trim();

        String getPoje = pojemnosc.getText().toString().trim();
        if (getPoje.matches("")) {
            getPoje="None";
        }



        //SQLite nie rozróżnia dużych i małych liter
        String query = getName+ "==" + getBrand+"==" + getType +"=="+ getPoje;

        System.out.println(query);

        Intent intent = new Intent(getActivity(), SearchActivity.class);
        intent.putExtra(AlcoViewActivity.EXTRA_DRINKID, query);
        getActivity().startActivity(intent);
    }

}
