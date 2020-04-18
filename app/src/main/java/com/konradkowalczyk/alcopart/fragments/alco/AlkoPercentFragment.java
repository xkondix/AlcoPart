package com.konradkowalczyk.alcopart.fragments.alco;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.tabs.TabLayout;
import com.konradkowalczyk.alcopart.R;

public class AlkoPercentFragment extends Fragment {


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().setTitle("Alkomat wirtualny");

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_alko_percent, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);



        //Stworzenie viewPagera i tabLayoutu
        ViewPager pager = (ViewPager) view.findViewById(R.id.pager);
        TabLayout tabLayout = (TabLayout) view.findViewById(R.id.tabs);


        // Dołączamy ViewPager do TabLayout
        tabLayout.setupWithViewPager(pager);

        //stworzenie View.OnClickListener  adapetar
        FragmentStatePagerAdapter adapter = new SectionsPagerAdapter(getChildFragmentManager());

        //dodanie adaptera do pagera
        pager.setAdapter(adapter);
    }


    private class SectionsPagerAdapter extends FragmentStatePagerAdapter {
        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return new AlcoObliczFragment();
                case 1:
                    return new OpisFragment();

            }
            return null;
        }


        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "Obliczenia";
                case 1:
                    return "Opis";
            }
            return null;
        }
    }


}