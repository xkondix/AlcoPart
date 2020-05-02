package com.konradkowalczyk.alcopart.fragments.main;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.rpc.Help;
import com.google.rpc.HelpOrBuilder;
import com.konradkowalczyk.alcopart.AlcoDatabaseHelper;
import com.konradkowalczyk.alcopart.AlcoViewActivity;
import com.konradkowalczyk.alcopart.R;
import com.konradkowalczyk.alcopart.fragments.alco.AlcomatItem;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;


/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends Fragment {

//    //tablice
      private List<HelperObj> helper;
      private Set<String> set = new HashSet<>();

    private RecyclerViewMain adapter;





    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().setTitle("Recenzje");

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        RecyclerView menuView = (RecyclerView) inflater.inflate(
                R.layout.fragment_recycler_view,container,false);




        //stworzenie i ustawienie adaptera
        adapter = new RecyclerViewMain(new HelperObj[]{});
        menuView.setAdapter(adapter);

        //dodanie jak ma wygladac layout (cardView - rodzaj wyswietlania)
        LinearLayoutManager layoutManager
                = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        menuView.setLayoutManager(layoutManager);


        //onClick - reakcja na przycisniecie cardView
        adapter.setListener(new RecyclerViewMain.Listener(){
            @Override
            public void onClick(int position) {

                Intent intent = new Intent(getActivity(), AlcoViewActivity.class);
                intent.putExtra(AlcoViewActivity.EXTRA_DRINKID, (int) helper.get(position).getId());
                getActivity().startActivity(intent);
            }
        });

        return menuView;



    }


    @Override
    public void onResume()
    {
        super.onResume();
        new FireRec().execute();

    }

    public void updateView()
    {
        Collections.sort(helper, new Comparator<HelperObj>() {
            @Override
            public int compare(HelperObj object1, HelperObj object2) {
                return (int) (object2.getData().compareTo(object1.getData()));
            }
        });
        adapter.updateData(helper.toArray(new HelperObj[0]));
        adapter.notifyDataSetChanged();

    }



    private class FireRec extends AsyncTask<Void,Void,Boolean>
    {
        private FirebaseAuth auth;
        private FirebaseUser user;
        private FirebaseFirestore db;

        @Override
        protected void onPreExecute()
        {
            helper = new ArrayList<>();
            auth = FirebaseAuth.getInstance();
            user = auth.getCurrentUser();
            db = FirebaseFirestore.getInstance();


        }

        @Override
        protected Boolean doInBackground(Void... voids) {


            try {
                FirebaseFirestore firestore = FirebaseFirestore.getInstance();
                firestore.collection("User").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Map<String, Object> doc = document.getData();

                                String nick = (String) doc.get("Nick");

                                for(String key : doc.keySet()) {
                                    if (key.startsWith("Recenzja")) {
                                        System.out.println(key);
                                        Map<String, Object> rec = (Map<String, Object>) doc.get(key);
                                        HelperObj help = new HelperObj();
                                        help.setData((Timestamp) rec.get("Data"));
                                        help.setNick(nick);
                                        help.setId(Integer.parseInt(String.valueOf((Long) rec.get("Id"))));
                                        help.setOcena(Float.parseFloat((String) rec.get("Ocena")));
                                        help.setRecenzja((String) rec.get("Recenzja"));
                                        set.add(help.getId()+"");
                                        helper.add(help);
                                    }


                                }
                            }
                            new UpdateAlco().execute();

                        } else {

                        }
                    }
                });

                return true;


            }catch (Exception e) {
                return false;
            }
        }

        public void onPostExecute(Boolean success)
        {


        }


    }



    private class UpdateAlco extends AsyncTask<Void,Void,Boolean>
    {
        private SQLiteOpenHelper alcoDatabaseHelper;
        private SQLiteDatabase db;
        private Cursor cursor;


        @Override
        protected void onPreExecute()
        {
            System.out.println(helper.size());

            alcoDatabaseHelper = new AlcoDatabaseHelper(getActivity());
             db = alcoDatabaseHelper.getReadableDatabase();
             String[] arr = set.toArray(new String[set.size()]);




        }

        @Override
        protected Boolean doInBackground(Void... voids) {

            cursor = db.query("ALCOHOL",
                    new String[]{"_id","NAME","IMAGE_ID"},
                    null,null,
                    null, null, null);

            Map<Integer,String[]> val = new HashMap<>();


            try {

                if (cursor.moveToFirst()){
                    do{
                        val.put(cursor.getInt(0), new String[]{cursor.getString(1), cursor.getString(2)});
                    }while(cursor.moveToNext());
                }
            } catch (SQLiteException e) {
                return false;
            }

            for(HelperObj obj : helper)
            {
                String[] tab = val.get(obj.getId());
                obj.setJpg(getActivity().getResources().getIdentifier(tab[1], "drawable", getActivity().getPackageName()));
                obj.setNazwa(tab[0]);
            }

            return true;
        }

        public void onPostExecute(Boolean success)
        {
            cursor.close();
            db.close();
            if(success)
            updateView();


        }
    }

}
