package com.konradkowalczyk.alcopart.fragments.alco;

import android.graphics.drawable.Drawable;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import com.konradkowalczyk.alcopart.R;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class RecyclerViewAdapterAlco extends RecyclerView.Adapter<RecyclerViewAdapterAlco.ViewHolder> {

    private Listener listener;
    private String[] procenty;
    private String[] ml;
    private GregorianCalendar[] data;




    public interface Listener
    {
       // void onClick(int position);
        void onDeleteClick(int position);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder
    {

        private CardView cardView;

        public ViewHolder(CardView view) {
            super(view);
            this.cardView=view;
        }




    }

    public RecyclerViewAdapterAlco(GregorianCalendar[] data,String[] procenty, String[] ml)
    {
        this.procenty=procenty;
        this.data=data;
        this.ml=ml;
    }



    public void setListener(Listener listener)
    {
        this.listener=listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CardView cardView = (CardView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_view_alco,parent,false);
        return new ViewHolder(cardView);
    }




    //widok RecyclerView wywołuje te metode jeżeli chce ponownie użyć lub użyć obiektu viewHolder tylko z innymi danymi
    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapterAlco.ViewHolder holder, final int position) {
        final CardView cardView = holder.cardView;
        TextView procentyView = (TextView) cardView.findViewById(R.id.cardViewProcenty);
        procentyView.setText(procenty[position]+"%");
        TextView mlView = (TextView) cardView.findViewById(R.id.cardViewMl);
        mlView.setText(ml[position]+"ml");

        GregorianCalendar dataPosition = data[position];

        String dataString = dataPosition.get(Calendar.DAY_OF_MONTH) +"/" +(dataPosition.get(Calendar.MONTH)+1) +"/"+ dataPosition.get(Calendar.YEAR);
        String timeString = dataPosition.get(Calendar.HOUR)+":"+dataPosition.get(Calendar.MINUTE)+" "+(Integer.valueOf(dataPosition.get(Calendar.AM_PM)) == 0 ? "am" : "pm");

        TextView dateview = (TextView) cardView.findViewById(R.id.cardViewData);
        dateview.setText(dataString);
        TextView timeview = (TextView) cardView.findViewById(R.id.cardViewTime);
        timeview.setText(timeString);

        ImageButton img =  cardView.findViewById(R.id.delete_alco);


        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onDeleteClick(position);
                }
            }
        });

//        cardView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (listener != null) {
//                    listener.onClick(position);
//                }
//            }
//        });
    }



    public void updateData(GregorianCalendar[] data,String[] procenty, String[] ml)
    {
        this.procenty=procenty;
        this.data=data;
        this.ml=ml;
    }

    @Override
    public int getItemCount() {
        return data.length;
    }



}
