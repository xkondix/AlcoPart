package com.konradkowalczyk.alcopart;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private Listener listener;
    private String[] titels;


    interface Listener
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

    public RecyclerViewAdapter(String[] titels)
    {
        this.titels=titels;
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

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder,final int position) {
        CardView cardView = holder.cardView;
        TextView textView = (TextView) cardView.findViewById(R.id.textMenu);
        textView.setText(titels[position]);
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
        return titels.length;
    }



}
