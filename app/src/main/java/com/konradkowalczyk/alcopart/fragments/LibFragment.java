package com.konradkowalczyk.alcopart.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.konradkowalczyk.alcopart.R;
import com.konradkowalczyk.alcopart.RecyclerViewAdapter;


public class LibFragment extends Fragment {


    static String[] list = {"Kinematyka","Mechanika","Ruch Falowy","Fizyka Jądrowa","Zjawisko Fotoelektryczne"};
    static Class[] classes = {BeerFragment.class};

    public LibFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        RecyclerView menuView = (RecyclerView) inflater.inflate(
                R.layout.fragment_recycler_view,container,false);

        //stworzenie i ustawienie adaptera
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(list);
        menuView.setAdapter(adapter);

        //dodanie jak ma wygladac layout (cardView - rodzaj wyswietlania)
        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(),2);
        menuView.setLayoutManager(layoutManager);

        //onClick - reakcja na przycisniecie cardView
        adapter.setListener(new RecyclerViewAdapter.Listener() {
            @Override
            public void onClick(int position) {

                Intent intent = new Intent(getActivity(), classes[position] );
                getActivity().startActivity(intent);
            }
        });

        return menuView;

    }
}
