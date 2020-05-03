package com.konradkowalczyk.alcopart.fragments.main;

import android.graphics.drawable.Drawable;
import android.text.format.DateFormat;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.RecyclerView;

import com.konradkowalczyk.alcopart.R;

import java.util.Calendar;
import java.util.Locale;

public class RecyclerViewMain extends RecyclerView.Adapter<RecyclerViewMain.ViewHolder> {

    private Listener listener;
    HelperObj helperObj[];

    //animation
    private boolean on_attach = true;
    long DURATION = 500;


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

    public RecyclerViewMain(HelperObj[] helperObj)
    {
        this.helperObj=helperObj;
    }



    public void setListener(RecyclerViewMain.Listener listener)
    {
        this.listener=listener;
    }

    @NonNull
    @Override
    public RecyclerViewMain.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CardView cardView = (CardView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.main_card_view,parent,false);
        return new RecyclerViewMain.ViewHolder(cardView);
    }




    //widok RecyclerView wywołuje te metode jeżeli chce ponownie użyć lub użyć obiektu viewHolder tylko z innymi danymi
    @Override
    public void onBindViewHolder(@NonNull RecyclerViewMain.ViewHolder holder, final int position) {
        CardView cardView = holder.cardView;
        TextView textView = (TextView) cardView.findViewById(R.id.nameBEER);
        textView.setText(helperObj[position].getNazwa());
        TextView textNick = (TextView) cardView.findViewById(R.id.recTEXT);
        textNick.setText(helperObj[position].getNick());
        TextView textData = (TextView) cardView.findViewById(R.id.dataRES);
        Calendar cal = Calendar.getInstance(Locale.ENGLISH);
        cal.setTimeInMillis(helperObj[position].getData().getSeconds() * 1000L);
        String date = DateFormat.format("dd-MM-yyyy hh:mm a", cal).toString();
        textData.setText(date);
        TextView textRec = (TextView) cardView.findViewById(R.id.resRES);
        textRec.setText(helperObj[position].getRecenzja());
        //dodaje scrolowanie tylko w momencie kiedy jest wieksze od view.heigh (zeby mozna bylo klikac w te miejsce)
        if(helperObj[position].getRecenzja().length()>125) {
            textRec.setOnTouchListener(new View.OnTouchListener() {

                public boolean onTouch(View v, MotionEvent event) {
                    // Disallow the touch request for parent scroll on touch of child view
                    v.getParent().requestDisallowInterceptTouchEvent(true);

                    return false;
                }
            });
            textRec.setMovementMethod(new ScrollingMovementMethod());
        }
        RatingBar ratingBar = cardView.findViewById(R.id.resSTARS);
        ratingBar.setRating(helperObj[position].getOcena());



        ImageView imageView = cardView.findViewById(R.id.recJPG);
        Drawable drawable =
                ContextCompat.getDrawable(cardView.getContext(), helperObj[position].getJpg());
        imageView.setImageDrawable(drawable);
        imageView.setContentDescription(helperObj[position].getNazwa());

        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onClick(position);
                }
            }
        });

    }
        public void updateData (HelperObj[] helperObj)
        {
            this.helperObj=helperObj;
        }

        @Override
        public int getItemCount () {
            return helperObj.length;
        }


    }







