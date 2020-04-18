package com.konradkowalczyk.alcopart.fragments.alco;


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
import com.konradkowalczyk.alcopart.R;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class AlcoObliczFragment extends Fragment implements View.OnClickListener ,AlcoDialogFragment.OnRespoundSend {

    //view
    private Button addAlco;
    private ImageButton wiekUp,wiekDown,wagaUp,wagaDown;
    private EditText wiekEdit,wagaEdit;

    //zmienne
    private int age = 18;
    private int weigh = 0;
    private RecyclerViewAdapterAlco adapter;

    //zmienne z dialog
    private List<String> percent= new ArrayList<String>();
    private List<String> ml= new ArrayList<String>();
    private List<GregorianCalendar> data= new ArrayList<>();







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

        wiekEdit = view.findViewById(R.id.WiekZmien);
        wagaEdit = view.findViewById(R.id.WagaZmien);

        addAlco = view.findViewById(R.id.dodaj_alco);
        addAlco.setOnClickListener(this);

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
        adapter = new RecyclerViewAdapterAlco((GregorianCalendar[])data.toArray(new GregorianCalendar[data.size()]),
                (String[])percent.toArray(new String[percent.size()]),(String[])ml.toArray(new String[ml.size()]));
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
        }

    }


    public void removeItem(int position) {


        data.remove(position);
        percent.remove(position);
        adapter.updateData((GregorianCalendar[])data.toArray(new GregorianCalendar[0]),
                (String[])percent.toArray(new String[percent.size()]),(String[])ml.toArray(new String[ml.size()]));
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
        data.add(inputData);
        percent.add(inputProcenty);
        ml.add(inputMl);
        adapter.updateData((GregorianCalendar[])data.toArray(new GregorianCalendar[0]),
                (String[])percent.toArray(new String[percent.size()]),(String[])ml.toArray(new String[ml.size()]));
        adapter.notifyDataSetChanged();

    }

    @Override
    public void changePosition(String inputProcenty, String inputMl, GregorianCalendar inputDate, int position) {

        data.set(position,inputDate);
        percent.set(position,inputProcenty);
        ml.set(position,inputMl);
        adapter.updateData((GregorianCalendar[])data.toArray(new GregorianCalendar[0]),
                (String[])percent.toArray(new String[percent.size()]),(String[])ml.toArray(new String[ml.size()]));
        adapter.notifyDataSetChanged();

    }
}


