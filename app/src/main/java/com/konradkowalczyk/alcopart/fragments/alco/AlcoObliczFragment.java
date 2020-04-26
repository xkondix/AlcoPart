package com.konradkowalczyk.alcopart.fragments.alco;


import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.ContactsContract;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.TextView;

import com.konradkowalczyk.alcopart.R;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class AlcoObliczFragment extends Fragment implements View.OnClickListener ,AlcoDialogFragment.OnRespoundSend {

    //view
    private Button addAlco,showWynik;
    private ImageButton wzrostUp,wzrostDown,wagaUp,wagaDown,wiekUp,wiekDown;
    private EditText wzrostEdit,wagaEdit,wiekEdit;
    private TextView wypiszWynik,pilemNa;
    private RadioButton radioButtonMen,radioButtonWoman,radioButtonPelny,radioButtonPolPelny,radioButtonPusty;

    //zmienne
    private int heigh = 0;
    private int weigh = 0;
    private int age = 18;
    private String płeć="m";
    private int żołądek = 60;
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
        pilemNa = view.findViewById(R.id.pilemna);


        wzrostEdit = view.findViewById(R.id.WzrostZmien);
        wagaEdit = view.findViewById(R.id.WagaZmien);
        wiekEdit = view.findViewById(R.id.wiekZmien);

        addAlco = view.findViewById(R.id.dodaj_alco);
        addAlco.setOnClickListener(this);

        showWynik = view.findViewById(R.id.oblicz);
        showWynik.setOnClickListener(this);

        wiekUp = view.findViewById(R.id.upWiek);
        wiekUp.setOnClickListener(this);

        wiekDown = view.findViewById(R.id.downWiek);
        wiekDown.setOnClickListener(this);

        wzrostUp = view.findViewById(R.id.upWzrost);
        wzrostUp.setOnClickListener(this);

        wzrostDown = view.findViewById(R.id.downWzrost);
        wzrostDown.setOnClickListener(this);

        wagaUp = view.findViewById(R.id.upWaga);
        wagaUp.setOnClickListener(this);

        wagaDown = view.findViewById(R.id.downWaga);
        wagaDown.setOnClickListener(this);

        radioButtonMen = view.findViewById(R.id.men);
        radioButtonMen.setOnClickListener(this);

        radioButtonPelny = view.findViewById(R.id.pelny);
        radioButtonPelny.setOnClickListener(this);

        radioButtonPolPelny = view.findViewById(R.id.polpelny);
        radioButtonPolPelny.setOnClickListener(this);

        radioButtonPusty = view.findViewById(R.id.pusty);
        radioButtonPusty.setOnClickListener(this);

        radioButtonWoman = view.findViewById(R.id.girl);
        radioButtonWoman.setOnClickListener(this);


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

        wzrostEdit.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                try {
                    heigh = Integer.valueOf(wzrostEdit.getText().toString().trim());
                }catch(NumberFormatException e){
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.equals(null)) {
                    heigh = 0;
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
            case R.id.upWzrost:
                heigh++;
                wzrostEdit.setText(Integer.toString(heigh));
                break;
            case R.id.downWzrost:
                if(heigh>0) heigh--;
                wzrostEdit.setText(Integer.toString(heigh));
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
            case R.id.pusty:
                żołądek=60;
                break;
            case R.id.polpelny:
                żołądek=120;
                break;
            case R.id.pelny:
                żołądek=180;
                break;
            case R.id.men:
                płeć="m";
                pilemNa.setText("Piłem na: ");
                break;
            case R.id.girl:
                płeć="k";
                pilemNa.setText("Piłam na: ");
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
        dialog.show(getFragmentManager(), "Dialog1");

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



        //zmienne obliczeniowe
        double wchłanianie=items.get(0).getGram()/żołądek;
        double gram = items.get(0).getGram();
        double actualGram = 0;
        double promile;
        double maxPromil=0;
        double woda = liczPłyny();
        int itemSelect = 1;
        //czasowe zmienne
        GregorianCalendar start = items.get(0).getGregorian();
        Date prowadzenieAuta=null;
        Date maxPromileTime=null;


        do {
            if (itemSelect < items.size() && start.after(items.get(itemSelect).getGregorian())) {
                gram += items.get(itemSelect).getGram();
                wchłanianie = gram / żołądek;
                itemSelect++;

            }

            if (gram > 0) actualGram += wchłanianie; //ilość gramów we krwi
            if (gram > 0) gram -= wchłanianie; // ile gramów do wchłonienia
            if (actualGram > 0 )
                actualGram -= ((10 * actualGram) / (4.2 + actualGram)) / 60; // zmniejszane co minute stężenie
            promile = actualGram / woda;
            start.add(Calendar.MINUTE, 1);

            if(promile < 0.2 && prowadzenieAuta == null && start.after(items.get((items.size() - 1)).getGregorian()))
            {
                System.out.println("xd");
                prowadzenieAuta = start.getTime();
            }


            if(maxPromil<promile)
            {
                maxPromil = promile;
                maxPromileTime = start.getTime();
            }



        }while(start.before(items.get(items.size()-1).getGregorian()) || promile>0.0);




        wypiszWynik.setText("Wypiles ogólnie "+etanol+"g alkocholu\nBędziesz trzeźwy o godzinie "
        +start.getTime()+"\nTwoje maxymalne promile wynoszą "+maxPromil+" o godzinie "+
                (maxPromileTime!=null ? maxPromileTime : "brak")
        +"\nBędziesz mógł prowadzić o "+(prowadzenieAuta!=null ? prowadzenieAuta : "brak"));

        crateDialog(start);





    }

    public double liczPłyny()
    {
        double płyny=0;
        double tłuszcz = 0;
        boolean nadwaga = false;
        double BMI = weigh/((((double)heigh)/100)*(((double)heigh)/100));

        if(płeć.equals("k"))
        {
            if(age < 25)
            {
                if(BMI<=22.1)
                {
                    tłuszcz = (22.1-BMI)*((heigh/100)*(heigh/100));
                }
                else if(BMI>=25)
                {
                    tłuszcz = (BMI-25)*((heigh/100)*(heigh/100));
                    nadwaga = true;
                }
            }
            else if(age > 24 && age <30)
            {
                if(BMI<=22)
                {
                    tłuszcz = (22-BMI)*((heigh/100)*(heigh/100));
                }
                else if(BMI>=25.4)
                {
                    tłuszcz = (BMI-25.4)*((heigh/100)*(heigh/100));
                    nadwaga = true;
                }
            }

            else if(age > 29 && age <35)
            {
                if(BMI<=22.7)
                {
                    tłuszcz = (22.7-BMI)*((heigh/100)*(heigh/100));
                }
                else if(BMI>=26.4)
                {
                    tłuszcz = (BMI-26.4)*((heigh/100)*(heigh/100));
                    nadwaga = true;
                }
            }

            else if(age > 34 && age <40)
            {
                if(BMI<=24)
                {
                    tłuszcz = (24-BMI)*((heigh/100)*(heigh/100));
                }
                else if(BMI>=26.4)
                {
                    tłuszcz = (BMI-27.7)*((heigh/100)*(heigh/100));
                    nadwaga = true;
                }
            }

            else if(age > 39 && age <45)
            {
                if(BMI<=25.6)
                {
                    tłuszcz = (25.6-BMI)*((heigh/100)*(heigh/100));
                }
                else if(BMI>=29.3)
                {
                    tłuszcz = (BMI-29.3)*((heigh/100)*(heigh/100));
                    nadwaga = true;
                }
            }

            else if(age > 44 && age <50)
            {
                if(BMI<=27.3)
                {
                    tłuszcz = (27.3-BMI)*((heigh/100)*(heigh/100));
                }
                else if(BMI>=30.9)
                {
                    tłuszcz = (BMI-30.9)*((heigh/100)*(heigh/100));
                    nadwaga = true;
                }
            }

            else if(age > 49 && age <55)
            {
                if(BMI<=29.7 )
                {
                    tłuszcz = (29.7-BMI)*((heigh/100)*(heigh/100));
                }
                else if(BMI>=33.1)
                {
                    tłuszcz = (BMI-33.1)*((heigh/100)*(heigh/100));
                    nadwaga = true;
                }
            }

            else if(age > 54 && age <60)
            {
                if(BMI<=30.7 )
                {
                    tłuszcz = (30.7-BMI)*((heigh/100)*(heigh/100));
                }
                else if(BMI>=34)
                {
                    tłuszcz = (BMI-34)*((heigh/100)*(heigh/100));
                    nadwaga = true;
                }
            }

            else if(age > 59)
            {
                if(BMI<=31 )
                {
                    tłuszcz = (31-BMI)*((heigh/100)*(heigh/100));
                }
                else if(BMI>=34.4)
                {
                    tłuszcz = (BMI-34.4)*((heigh/100)*(heigh/100));
                    nadwaga = true;
                }
            }
        }
        else if(płeć.equals("m"))
        {
            if(age < 25)
            {
                if(BMI<=14.9)
                {
                    tłuszcz = (14.9-BMI)*((heigh/100)*(heigh/100));
                }
                else if(BMI>=19)
                {
                    tłuszcz = (BMI-19)*((heigh/100)*(heigh/100));
                    nadwaga = true;
                }
            }
            else if(age > 24 && age <30)
            {
                if(BMI<=16.5)
                {
                    tłuszcz = (16.5-BMI)*((heigh/100)*(heigh/100));
                }
                else if(BMI>=20.3)
                {
                    tłuszcz = (BMI-20.3)*((heigh/100)*(heigh/100));
                    nadwaga = true;
                }
            }

            else if(age > 29 && age <35)
            {
                if(BMI<=18)
                {
                    tłuszcz = (18-BMI)*((heigh/100)*(heigh/100));
                }
                else if(BMI>=21.5)
                {
                    tłuszcz = (BMI-21.5)*((heigh/100)*(heigh/100));
                    nadwaga = true;
                }
            }

            else if(age > 34 && age <40)
            {
                if(BMI<=19.4)
                {
                    tłuszcz = (19.4-BMI)*((heigh/100)*(heigh/100));
                }
                else if(BMI>=22.6)
                {
                    tłuszcz = (BMI-22.6)*((heigh/100)*(heigh/100));
                    nadwaga = true;
                }
            }

            else if(age > 39 && age <45)
            {
                if(BMI<=20.5)
                {
                    tłuszcz = (20.5-BMI)*((heigh/100)*(heigh/100));
                }
                else if(BMI>=23.6)
                {
                    tłuszcz = (BMI-23.6)*((heigh/100)*(heigh/100));
                    nadwaga = true;
                }
            }

            else if(age > 44 && age <50)
            {
                if(BMI<=21.5)
                {
                    tłuszcz = (21.5-BMI)*((heigh/100)*(heigh/100));
                }
                else if(BMI>=24.5)
                {
                    tłuszcz = (BMI-24.5)*((heigh/100)*(heigh/100));
                    nadwaga = true;
                }
            }

            else if(age > 49 && age <55)
            {
                if(BMI<=22.7 )
                {
                    tłuszcz = (22.7-BMI)*((heigh/100)*(heigh/100));
                }
                else if(BMI>=25.6)
                {
                    tłuszcz = (BMI-25.6)*((heigh/100)*(heigh/100));
                    nadwaga = true;
                }
            }

            else if(age > 54 && age <60)
            {
                if(BMI<=23.2 )
                {
                    tłuszcz = (23.2-BMI)*((heigh/100)*(heigh/100));
                }
                else if(BMI>=26.2)
                {
                    tłuszcz = (BMI-26.2)*((heigh/100)*(heigh/100));
                    nadwaga = true;
                }
            }

            else if(age > 59)
            {
                if(BMI<=23.5 )
                {
                    tłuszcz = (23.5-BMI)*((heigh/100)*(heigh/100));
                }
                else if(BMI>=26.7)
                {
                    tłuszcz = (BMI-26.7)*((heigh/100)*(heigh/100));
                    nadwaga = true;
                }
            }
        }


        if(nadwaga)
        {
            if(płeć.equals("k")) płyny = ((weigh-tłuszcz)*0.6)+(tłuszcz*0.15);
            else płyny = ((weigh-tłuszcz)*0.7)+(tłuszcz*0.15);
        }
        else
        {
            if(płeć.equals("k")) płyny = ((weigh)*0.6);
            else płyny = ((weigh)*0.7);
        }

        System.out.println(BMI);
        System.out.println(płyny);
        System.out.println(tłuszcz);
        return płyny;
    }

    public void crateDialog(GregorianCalendar calendar)
    {
       // PowiadomienieDialog dialog = new PowiadomienieDialog(calendar);
        //dialog.show(getFragmentManager(), "Dialog2");
    }

}


