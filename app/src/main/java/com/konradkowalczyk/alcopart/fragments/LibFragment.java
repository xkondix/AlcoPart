package com.konradkowalczyk.alcopart.fragments;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.konradkowalczyk.alcopart.AlcoDatabaseHelper;
import com.konradkowalczyk.alcopart.AlcoViewActivity;
import com.konradkowalczyk.alcopart.R;
import com.konradkowalczyk.alcopart.RecyclerViewAdapter;
import com.konradkowalczyk.alcopart.ScanActivity;

import java.util.ArrayList;
import java.util.List;


public class LibFragment extends Fragment {

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
    private List<Integer> res = new ArrayList<>();
    private List<String> name = new ArrayList<>();
    private List<Integer> id = new ArrayList<>();
    private List<String> brand = new ArrayList<>();

    private RecyclerViewAdapter adapter;
    private Toolbar toolbar;

    private String message="brak piw w klaserze";


    //sendIntent need this
    public LibFragment()
    {

    }

    public LibFragment(androidx.appcompat.widget.Toolbar toolbar) {
        this.toolbar=toolbar;

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().setTitle("Klaser");
        data();
        setHasOptionsMenu(true);//Make sure you have this line of code.
        toolbar.inflateMenu(R.menu.menu_kolekcja);


    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.menu_kolekcja, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.scan_kolekcja:
                Intent i = new Intent(getActivity(), ScanActivity.class);
                getActivity().startActivity(i);
                return true;

            case R.id.send_kolekcja:
                new SendBuilder().execute();

                String text = message;
                System.out.println(message);
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_TEXT,text);
                String title = getString(R.string.tittle);
                Intent chosenIntent = Intent.createChooser(intent,title);
                startActivity(chosenIntent);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        RecyclerView menuView = (RecyclerView) inflater.inflate(
                R.layout.fragment_recycler_view, container, false);

        //stworzenie i ustawienie adaptera
        adapter = new RecyclerViewAdapter(nameAlco, resId, brandAlco);
        menuView.setAdapter(adapter);

        //dodanie jak ma wygladac layout (cardView - rodzaj wyswietlania)
        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 2);
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
    public void onDestroy() {
        super.onDestroy();
        if (db.isOpen()) {
            db.close();
        }

        if (!cursor.isClosed()) {
            cursor.close();
        }
    }

    public void processFinish() {
        System.out.println(message);
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT, message);
        String title = getString(R.string.tittle);
        Intent chosenIntent = Intent.createChooser(intent, title);
        startActivity(chosenIntent);

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
                    "COLLECT = ?", new String[]{"1"}, //wszystkie, ktre mamy w kolekcji COLLECT == 1
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


    private class SendBuilder extends AsyncTask<Void,Void,Boolean>
    {
        StringBuilder stringBuilder;


        @Override
        protected void onPreExecute()
        {
            db = alcoDatabaseHelper.getReadableDatabase();
            stringBuilder = new StringBuilder();

        }

        @Override
        protected Boolean doInBackground(Void... voids) {


            cursor = db.query("ALCOHOL",
                    new String[]{"NAME","TYPE","BRAND","NUMBER","CAPACITY","PERCENT"},
                    "COLLECT = ?", new String[]{"1"}, //wszystkie, ktre mamy w kolekcji COLLECT == 1
                    null, null, null);


            try {

                if (cursor.moveToFirst()){
                    do{

                        stringBuilder.append("nazwa - ");
                        stringBuilder.append(cursor.getString(0));
                        stringBuilder.append("\n");
                        stringBuilder.append("typ - ");
                        stringBuilder.append(cursor.getString(1));
                        stringBuilder.append("\n");
                        stringBuilder.append("marka - ");
                        stringBuilder.append(cursor.getString(2));
                        stringBuilder.append("\n");
                        stringBuilder.append("kod kreskowy - ");
                        stringBuilder.append(cursor.getString(3));
                        stringBuilder.append("\n");
                        stringBuilder.append("pojemność - ");
                        stringBuilder.append(cursor.getString(4));
                        stringBuilder.append("ml");
                        stringBuilder.append("\n");
                        stringBuilder.append("procenty - ");
                        stringBuilder.append(cursor.getString(5));
                        stringBuilder.append("%");
                        stringBuilder.append("\n");
                        stringBuilder.append("-------------------------------");
                        stringBuilder.append("\n");

                    }while(cursor.moveToNext());
                }
                message = stringBuilder.toString();
                db.close();
                cursor.close();

                return true;

            }catch (SQLiteException e) {
                return false;
            }

        }

        public void onPostExecute(Boolean success)
        {
            if(!success)
            {

                Toast toast = Toast.makeText(getContext(),"Baza danych nie dziala",Toast.LENGTH_SHORT);
                toast.show();
            }
            processFinish();
        }
    }


}






