package com.konradkowalczyk.alcopart;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import com.konradkowalczyk.alcopart.fragments.MainFragment;
import com.konradkowalczyk.alcopart.fragments.Search2Fragment;

public class SearchActivity extends AppCompatActivity {

    public static final String EXTRA_DRINKID = "drinkID";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        String query = (String) getIntent().getExtras().get(EXTRA_DRINKID);

        //Dodanie paska aktywności
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Fragment fragment = new Search2Fragment(query);
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.add(R.id.content_frame, fragment);
        ft.commit();

    }
}
