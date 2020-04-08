package com.konradkowalczyk.alcopart.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
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
import android.widget.TextView;
import android.widget.Toast;

import com.konradkowalczyk.alcopart.AlcoDatabaseHelper;
import com.konradkowalczyk.alcopart.AlcoViewActivity;
import com.konradkowalczyk.alcopart.R;
import com.konradkowalczyk.alcopart.RecyclerViewAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * A simple {@link Fragment} subclass.
 */
public class Search2Fragment extends Fragment {

    private String query;


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

    public Search2Fragment(String query) {
        this.query=query;
    }





    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().setTitle("Wyszukane");
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




            setCursor();


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




    public void setCursor()
    {


        String tab[] = query.split("==");
        int counter = 0;
        //name brand type pojemnosc

        Map<String, String> map  = new TreeMap<>();
        if(!tab[0].equals("None"))
        {
            String name = tab[0].toLowerCase();
            name = name.substring(0, 1).toUpperCase() + name.substring(1).trim();
            map.put("Name = ?",name);
            counter++;
        }
        if(!tab[1].equals("None"))
        {
            String brand = tab[1].toLowerCase();
            brand = brand.substring(0, 1).toUpperCase() + brand.substring(1).trim();
            map.put("BRAND = ?",brand);
            counter++;
        }
        if(!tab[2].equals("None"))
        {
            map.put("TYPE = ?",String.valueOf(getResources().getIdentifier(tab[2].toLowerCase(), "string",
                    getContext().getPackageName())));
            counter++;
        }
        if(!tab[3].equals("None"))
        {
            map.put("CAPACITY = ?",tab[3]);
            counter++;
        }

        String key =  null;


        if(counter>1) {
            StringBuilder m = new StringBuilder();
            int cout = 0;
            for (String k : map.keySet()) {
                if(cout<counter-1) {
                    m.append(k + " AND ");
                }
                else
                {
                    m.append(k);
                }
                cout++;
            }

            key = m.toString();

        }
        else if(counter==1)
        {
            key=map.keySet().toArray()[0].toString();
        }

        String[] result = map.values().toArray(new String[0]);


        cursor = db.query("ALCOHOL",
                new String[]{"_id", "NAME", "BRAND", "IMAGE_ID"},
                key, result,
                null, null, null);



    }



}
