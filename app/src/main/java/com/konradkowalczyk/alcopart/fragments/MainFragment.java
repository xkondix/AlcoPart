package com.konradkowalczyk.alcopart.fragments;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.konradkowalczyk.alcopart.AlcoDatabaseHelper;
import com.konradkowalczyk.alcopart.AlcoViewActivity;
import com.konradkowalczyk.alcopart.R;
import com.konradkowalczyk.alcopart.RecyclerViewAdapter;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends Fragment {

    //tablice
    private String[] nameAlco;
    private Integer[] idIntent;
    private int[] resId;
    private List<String> resString = new ArrayList<String>();
    private String[] brandAlco;

    //baza danych
    private SQLiteOpenHelper alcoDatabaseHelper;
    private SQLiteDatabase db;
    private Cursor cursor;

    //łatwiej dodawać do list
    private List<Integer> res = new ArrayList<>();
    private List<String> name = new ArrayList<>();
    private List<Integer> id = new ArrayList<>();
    private List<String> brand = new ArrayList<>();


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().setTitle("MainFragment");

        alcoDatabaseHelper = new AlcoDatabaseHelper(getActivity());

        try {

            db = alcoDatabaseHelper.getReadableDatabase();


            cursor = db.query("ALCOHOL",
                    new String[]{"_id","NAME","BRAND","IMAGE_ID"},
                   null,null,
                    null,null,null);

            cursor.moveToFirst();


            if (cursor == null)
                System.out.println("empty");
            try {
                while (true) {

                    id.add(cursor.getInt(0));
                    name.add(cursor.getString(1));
                    brand.add(cursor.getString(2));
                    resString.add(cursor.getString(3));

                    if (!cursor.moveToNext()) {
                        break;
                    }

                }
                }catch (SQLiteException e){}


            //resId = res.stream().mapToInt(i -> i).toArray(); api za male na streamy i lambdy?

            nameAlco = name.toArray(new String[name.size()]);
            idIntent =  id.toArray(new Integer[id.size()]);
            brandAlco =  brand.toArray(new String[brand.size()]);
            //resId = res.toArray(new Integer[res.size()]);

            resId = new int[resString.size()];
            for(int i = 0; i < resString.size(); i++) {
                resId[i] =  getActivity().getResources().getIdentifier(resString.get(i), "drawable", getActivity().getPackageName());
            }

            db.close();
            cursor.close();


        }catch (SQLiteException e) {
            Toast toast = Toast.makeText(getActivity(),"Baza danych nie dziala",Toast.LENGTH_SHORT);
            toast.show();
        }


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        RecyclerView menuView = (RecyclerView) inflater.inflate(
                R.layout.fragment_recycler_view,container,false);




        //stworzenie i ustawienie adaptera
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(nameAlco,resId,brandAlco);
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

}
