package com.konradkowalczyk.alcopart.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.konradkowalczyk.alcopart.R;
import com.konradkowalczyk.alcopart.RecyclerViewAdapter;



/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends Fragment {



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().setTitle("AkloPart");

    }

    public MainFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

//        RecyclerView menuView = (RecyclerView) inflater.inflate(
//                R.layout.fragment_recycler_view,container,false);

//        //stworzenie i ustawienie adaptera
//        RecyclerViewAdapter adapter = new RecyclerViewAdapter(list);
//        menuView.setAdapter(adapter);
//
//        //dodanie jak ma wygladac layout (cardView - rodzaj wyswietlania)
//        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
//        menuView.setLayoutManager(layoutManager);
//
//        //onClick - reakcja na przycisniecie cardView
//        adapter.setListener(new RecyclerViewAdapter.Listener() {
//            @Override
//            public void onClick(int position) {
//
//                Intent intent = new Intent(getActivity(), classes[position] );
//                getActivity().startActivity(intent);
//            }
//        });

        return inflater.inflate(R.layout.fragment_search, container, false);

    }
}
