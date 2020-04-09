package com.konradkowalczyk.alcopart;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private Listener listener;
    private String[] nameAlco;
    private int[] resId;
    private String[] brandAlco;

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

        ImageView imageView = cardView.findViewById(R.id.photo_alco);
        Drawable drawable =
               ContextCompat.getDrawable(cardView.getContext(), resId[position]);
        imageView.setImageDrawable(drawable);
        imageView.setContentDescription(nameAlco[position]);

        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(listener != null)
                {
                    listener.onClick(position);
                }
            }
        });

//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            setAnimation(holder.itemView, position);
//        }
    }

//    @Override
//    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
//
//        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
//            @Override
//            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
//                Log.d(TAG, "onScrollStateChanged: Called " + newState);
//                on_attach = false;
//                super.onScrollStateChanged(recyclerView, newState);
//            }
//        });
//
//        super.onAttachedToRecyclerView(recyclerView);
//    }
//
//    private void setAnimation(View view, int position) {
//        if(!on_attach){
//            position = -1;
//        }
//
//        boolean isNotFirstItem = (position == -1);
//        position++;
//        view.setAlpha(0.f);
//        AnimatorSet animatorSet = new AnimatorSet();
//        ObjectAnimator animator = ObjectAnimator.ofFloat(view, "alpha", 0.f, 0.5f, 1.0f, 2.0f);
//        ObjectAnimator.ofFloat(view, "alpha", 0.f).start();
//        animator.setStartDelay(isNotFirstItem ? DURATION / 2 : (position * DURATION / 3));
//        animator.setDuration(500);
//        animatorSet.play(animator);
//        animator.start();
//    }


//    @Override
//    public void onViewDetachedFromWindow(RecyclerViewAdapter.ViewHolder viewHolder)
//    {
//        super.onViewDetachedFromWindow(viewHolder);
//        viewHolder.itemView.clearAnimation();
//    }



    public void updateData(String[] nameAlco,int[] resId,String[] brandAlco)
    {
        this.nameAlco=nameAlco;
        this.resId=resId;
        this.brandAlco=brandAlco;
    }

    @Override
    public int getItemCount() {
        return nameAlco.length;
    }








}
