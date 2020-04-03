package com.konradkowalczyk.alcopart;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


public class RecyclerViewFragment extends Fragment {

    private String[] titels;
    private Class[] classes;

    public RecyclerViewFragment(String[] titels, Class[] classes) {
        this.titels=titels;
        this.classes=classes;
        //zapobiega utraceniu danych -> zamiast onSaveInstanceState w Activity nadrzędnym
        setRetainInstance(true);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        RecyclerView menuView = (RecyclerView) inflater.inflate(
                R.layout.fragment_recycler_view,container,false);

        //stworzenie i ustawienie adaptera
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(titels);
        menuView.setAdapter(adapter);

        //dodanie jak ma wygladac layout (cardView - rodzaj wyswietlania)
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
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
