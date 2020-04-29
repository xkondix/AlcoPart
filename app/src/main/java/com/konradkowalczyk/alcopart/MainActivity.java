package com.konradkowalczyk.alcopart;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.konradkowalczyk.alcopart.fragments.FavouriteFragment;
import com.konradkowalczyk.alcopart.fragments.LibFragment;
import com.konradkowalczyk.alcopart.fragments.MainFragment;
import com.konradkowalczyk.alcopart.fragments.SearchFragment;
import com.konradkowalczyk.alcopart.fragments.alco.AlkoPercentFragment;
import com.konradkowalczyk.alcopart.fragments.user.EditFragment;
import com.konradkowalczyk.alcopart.fragments.user.LoginFragment;
import com.konradkowalczyk.alcopart.fragments.user.RegisterFragment;
import com.konradkowalczyk.alcopart.fragments.user.User;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {


    private Toolbar toolbar;
    private TextView status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Dodanie paska aktywności
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.open,R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.navigator_view);
        navigationView.setNavigationItemSelectedListener(this);

        //textview w nagłówku
        View headerView = navigationView.getHeaderView(0);
        status = (TextView) headerView.findViewById(R.id.status);
        status.setText((User.iflog==true ? "Zalogowany" : "Wylogowany"));




        //stworzenie bazowego fragmentu
        Fragment fragment = new MainFragment();
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.add(R.id.content_frame, fragment);
        ft.commit();

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        int id = menuItem.getItemId();
        Fragment fragment = null;

        switch (id)
        {
            case R.id.mainFrag:
                fragment = new MainFragment();
                break;
            case R.id.wyszukajFrag:
                fragment = new SearchFragment();
                break;
            case R.id.klaserFrag:
                fragment = new LibFragment(toolbar);
                break;
            case R.id.ulubioneFrag:
                fragment = new FavouriteFragment();
                break;
            case R.id.trzeFrag:
                fragment = new AlkoPercentFragment();
                break;
            case R.id.rej:
                fragment = new RegisterFragment(status);
                break;
            case R.id.wyl:
                if(User.iflog)
                {
                    FirebaseAuth auch = FirebaseAuth.getInstance();
                    try {
                        auch.signOut();
                        User.iflog=false;
                        Toast.makeText(this, "Wylogowano", Toast.LENGTH_SHORT).show();
                        status.setText((User.iflog==true ? "Zalogowany" : "Wylogowany"));
                    }catch (Exception e) {
                    }
                }
                break;
            case R.id.zal:
                fragment = new LoginFragment(status);
                break;

            case R.id.profil:
                fragment = new EditFragment();
                break;
            default:
                fragment = new MainFragment();

        }

        if(fragment != null)
        {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.content_frame,fragment);
            ft.commit();
        }

        DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);
        drawerLayout.closeDrawer(GravityCompat.START);

        return true;
    }

    @Override
    public void onBackPressed()
    {
        DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);
        if(drawerLayout.isDrawerOpen(GravityCompat.START))
        {
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        else
        {
            super.onBackPressed();
        }
    }
}