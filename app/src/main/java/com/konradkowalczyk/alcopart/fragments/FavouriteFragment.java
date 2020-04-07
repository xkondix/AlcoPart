package com.konradkowalczyk.alcopart.fragments;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.konradkowalczyk.alcopart.AlcoDatabaseHelper;
import com.konradkowalczyk.alcopart.AlcoViewActivity;
import com.konradkowalczyk.alcopart.R;
import com.konradkowalczyk.alcopart.RecyclerViewAdapter;

import java.util.ArrayList;
import java.util.List;


public class FavouriteFragment extends Fragment {



    //tablice
    private String[] nameAlco;
    private Integer[] idIntent;
    private int[] resId;
    private String[] brandAlco;

    //baza danych
    private SQLiteOpenHelper alcoDatabaseHelper;
    private SQLiteDatabase db;
    private Cursor cursor;

    //łatwiej dodawać do list
    private List<Integer> res;
    private List<String> name;
    private List<Integer> id;
    private List<String> brand;

    private RecyclerViewAdapter adapter;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().setTitle("Ulubione");
        data();


    }






    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        RecyclerView menuView = (RecyclerView) inflater.inflate(
                R.layout.fragment_recycler_view,container,false);


        //stworzenie i ustawienie adaptera
        adapter = new RecyclerViewAdapter(nameAlco,resId,brandAlco);
        menuView.setAdapter(adapter);

        //dodanie jak ma wygladac layout (cardView - rodzaj wyswietlania)
        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(),2);
        menuView.setLayoutManager(layoutManager);


        //onClick - reakcja na przycisniecie cardView
        adapter.setListener(new RecyclerViewAdapter.Listener() {
            @Override
            public void onClick(int position) {

                Intent intent = new Intent(getActivity(), AlcoViewActivity.class);
                intent.putExtra(AlcoViewActivity.EXTRA_DRINKID, (int) idIntent[position]);
                getActivity().startActivity(intent);
            }
        });

        return menuView;


    }


    @Override
    public void onResume()
    {
        super.onResume();
        data();
        adapter.updateData(nameAlco,resId,brandAlco);
        adapter.notifyDataSetChanged();
    }


    @Override
    public void onDestroy(){
        super.onDestroy();
        if(db.isOpen())
        {
            db.close();
        }

        if(!cursor.isClosed())
        {
            cursor.close();
        }
    }

    public void data()
    {

        res = new ArrayList<>();
        name = new ArrayList<>();
        id = new ArrayList<>();
        brand = new ArrayList<>();

        alcoDatabaseHelper = new AlcoDatabaseHelper(getActivity());

        try {

            db = alcoDatabaseHelper.getReadableDatabase();


            cursor = db.query("ALCOHOL",
                    new String[]{"_id", "NAME", "BRAND", "IMAGE_ID"},
                    "FAVOURITE = ?", new String[]{"1"}, //wszystkie, ktre mamy w kolekcji FAVOURITE == 1
                    null, null, null);


            try {

                if (cursor.moveToFirst()){
                    do{
                        id.add(cursor.getInt(0));
                        name.add(cursor.getString(1));
                        brand.add(cursor.getString(2));
                        res.add(cursor.getInt(3));

                    }while(cursor.moveToNext());
                }
            } catch (SQLiteException e) {
            }

            //resId = res.stream().mapToInt(i -> i).toArray(); api za male na streamy i lambdy?

            nameAlco = name.toArray(new String[name.size()]);
            idIntent = id.toArray(new Integer[id.size()]);
            brandAlco = brand.toArray(new String[brand.size()]);

            resId = new int[res.size()];
            for (int i = 0; i < res.size(); i++) {
                resId[i] = res.get(i);
            }

            db.close();
            cursor.close();


        } catch (SQLiteException e) {
            Toast toast = Toast.makeText(getActivity(), "Baza danych nie dziala", Toast.LENGTH_SHORT);
            toast.show();
        }
    }




}
