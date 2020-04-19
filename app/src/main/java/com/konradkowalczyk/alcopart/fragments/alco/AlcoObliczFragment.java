package com.konradkowalczyk.alcopart.fragments.alco;


import android.graphics.Paint;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.konradkowalczyk.alcopart.R;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class AlcoObliczFragment extends Fragment implements View.OnClickListener ,AlcoDialogFragment.OnRespoundSend {

    //view
    private Button addAlco,showWynik;
    private ImageButton wiekUp,wiekDown,wagaUp,wagaDown;
    private EditText wiekEdit,wagaEdit;
    private TextView wypiszWynik;

    //zmienne
    private int age = 18;
    private int weigh = 0;
    private RecyclerViewAdapterAlco adapter;

    //zmienne z dialog
    private List<AlcomatItem> items= new ArrayList<>();




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    public AlcoObliczFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_alco_oblicz, container, false);

        wypiszWynik = view.findViewById(R.id.wynik);

        wiekEdit = view.findViewById(R.id.WiekZmien);
        wagaEdit = view.findViewById(R.id.WagaZmien);

        addAlco = view.findViewById(R.id.dodaj_alco);
        addAlco.setOnClickListener(this);

        showWynik = view.findViewById(R.id.oblicz);
        showWynik.setOnClickListener(this);

        wiekUp = view.findViewById(R.id.upWiek);
        wiekUp.setOnClickListener(this);

        wiekDown = view.findViewById(R.id.downWiek);
        wiekDown.setOnClickListener(this);

        wagaUp = view.findViewById(R.id.upWaga);
        wagaUp.setOnClickListener(this);

        wagaDown = view.findViewById(R.id.downWaga);
        wagaDown.setOnClickListener(this);


        wagaEdit.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                try{
                weigh = Integer.valueOf(wagaEdit.getText().toString().trim());
            }catch(NumberFormatException e){
            }
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.equals(null)) {
                    weigh = 0;
                }
            }

        });

        wiekEdit.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                try {
                    age = Integer.valueOf(wiekEdit.getText().toString().trim());
                }catch(NumberFormatException e){
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.equals(null)) {
                    age = 18;
                }
            }

        });

        RecyclerView menuView = (RecyclerView) view.findViewById(R.id.alco_recycler);

        //stworzenie i ustawienie adaptera
        adapter = new RecyclerViewAdapterAlco((AlcomatItem[])items.toArray(new AlcomatItem[items.size()]));
        menuView.setAdapter(adapter);

        //dodanie jak ma wygladac layout (cardView - rodzaj wyswietlania)
        //GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 2);
        LinearLayoutManager layoutManager
                = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        menuView.setLayoutManager(layoutManager);
        //menuView.setLayoutManager(layoutManager);


        //onClick - reakcja na przycisniecie cardView
        adapter.setListener(new RecyclerViewAdapterAlco.Listener() {
            @Override
            public void onClick(int position) {
                onClik(position);
            }

            @Override
            public void onDeleteClick(int position) {
                removeItem(position);
            }
        });


        return view;

    }

    @Override
    public void onClick(View v) {

        switch(v.getId())
        {
            case R.id.dodaj_alco:
                onClikDodaj();
                break;
            case R.id.upWaga:
                weigh++;
                wagaEdit.setText(Integer.toString(weigh));
                break;
            case R.id.downWaga:
                if(weigh>0) weigh--;
                wagaEdit.setText(Integer.toString(weigh));
                break;
            case R.id.upWiek:
                age++;
                wiekEdit.setText(Integer.toString(age));
                break;
            case R.id.downWiek:
                if(age>0) age--;
                wiekEdit.setText(Integer.toString(age));
                break;
            case R.id.oblicz:
                Obliczenia();
                break;
        }

    }


    public void removeItem(int position) {

        items.remove(position);
        adapter.updateData((AlcomatItem[])items.toArray(new AlcomatItem[items.size()]));
        adapter.notifyDataSetChanged();
    }

    public void onClikDodaj()
    {


        AlcoDialogFragment dialog = new AlcoDialogFragment();
        dialog.setTargetFragment(AlcoObliczFragment.this, 1);
        dialog.show(getFragmentManager(), "MyCustomDialog");

    }

    public void onClik(int position)
    {

        AlcoDialogFragment dialog = new AlcoDialogFragment(position);
        dialog.setTargetFragment(AlcoObliczFragment.this, 1);
        dialog.show(getFragmentManager(), "MyCustomDialog");

    }




    @Override
    public void respondData(String inputProcenty, String inputMl, GregorianCalendar inputData) {
        items.add(new AlcomatItem(inputProcenty,inputMl,inputData));
        Collections.sort(items, new Comparator<AlcomatItem>() {
            @Override
            public int compare(AlcomatItem object1, AlcomatItem object2) {
                return (int) (object1.getGregorian().compareTo(object2.getGregorian()));
            }
        });
        adapter.updateData((AlcomatItem[])items.toArray(new AlcomatItem[items.size()]));
        adapter.notifyDataSetChanged();

    }

    @Override
    public void changePosition(String inputProcenty, String inputMl, GregorianCalendar inputDate, int position) {

        items.get(position).setGregorian(inputDate);
        items.get(position).setMl(inputMl);
        items.get(position).setProcent(inputProcenty);
        Collections.sort(items, new Comparator<AlcomatItem>() {
            @Override
            public int compare(AlcomatItem object1, AlcomatItem object2) {
                return (int) (object1.getGregorian().compareTo(object2.getGregorian()));
            }
        });
        adapter.updateData((AlcomatItem[])items.toArray(new AlcomatItem[items.size()]));
        adapter.notifyDataSetChanged();

    }

    public void Obliczenia()
    {
        double etanol = 0;
        List<Float> prom = new ArrayList<>();

        for(int i = 0; i<items.size();i++)
        {
            etanol += Double.parseDouble(items.get(i).geProcent())*Double.parseDouble(items.get(i).getMl())/100* 0.79;
        }

        wypiszWynik.setText("Wypiles ogólnie "+etanol+"g alkocholu");

        //while()




    }

}


