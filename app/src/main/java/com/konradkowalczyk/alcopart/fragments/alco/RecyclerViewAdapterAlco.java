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
    private AlcomatItem[] items;

    public RecyclerViewAdapterAlco(AlcomatItem[] items) {
        this.items=items;
    }


    public interface Listener
    {
        void onClick(int position);
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
        procentyView.setText(items[position].geProcent()+"%");
        TextView mlView = (TextView) cardView.findViewById(R.id.cardViewMl);
        mlView.setText(items[position].getMl()+"ml");

        TextView dateview = (TextView) cardView.findViewById(R.id.cardViewData);
        dateview.setText(items[position].getData());
        TextView timeview = (TextView) cardView.findViewById(R.id.cardViewTime);
        timeview.setText(items[position].getTime());

        ImageButton img =  cardView.findViewById(R.id.delete_alco);


        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onDeleteClick(position);
                }
            }
        });

        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onClick(position);
                }
            }
        });
    }



    public void updateData(AlcomatItem[] items)
    {
        this.items=items;
    }

    @Override
    public int getItemCount() {
        return items.length;
    }



}
