package com.konradkowalczyk.alcopart;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private Listener listener;
    private String[] nameAlco;
    private int[] resId;
    private String[] brandAlco;


    public interface Listener
    {
        void onClick(int position);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder
    {

        private CardView cardView;

        public ViewHolder(CardView view) {
            super(view);
            this.cardView=view;
        }
    }

    public RecyclerViewAdapter(String[] nameAlco,int[] resId,String[] brandAlco)
    {
        this.nameAlco=nameAlco;
        this.resId=resId;
        this.brandAlco=brandAlco;
    }

    public void setListener(Listener listener)
    {
        this.listener=listener;
    }

    @NonNull
    @Override
    public RecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CardView cardView = (CardView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_view_item,parent,false);
        return new ViewHolder(cardView);
    }




    //widok RecyclerView wywołuje te metode jeżeli chce ponownie użyć lub użyć obiektu viewHolder tylko z innymi danymi
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder,final int position) {
        CardView cardView = holder.cardView;
        TextView textView = (TextView) cardView.findViewById(R.id.nazwa);
        textView.setText(nameAlco[position]);
        TextView textView2 = (TextView) cardView.findViewById(R.id.marka);
        textView2.setText(brandAlco[position]);

        ImageView imageView = cardView.findViewById(R.id.photo_alco);
      //  Drawable drawable =
        //        ContextCompat.getDrawable(cardView.getContext(), resId[position]);
        //imageView.setImageDrawable(drawable);
        //imageView.setContentDescription(nameAlco[position]);

        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(listener != null)
                {
                    listener.onClick(position);
                }
            }
        });


    }

    @Override
    public int getItemCount() {
        return nameAlco.length;
    }




}
