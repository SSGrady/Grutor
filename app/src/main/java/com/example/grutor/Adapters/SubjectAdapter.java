package com.example.grutor.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.transition.Transition;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.example.grutor.Activites.DetailActivity;
import com.example.grutor.Activites.FeedActivity;
import com.example.grutor.R;

import java.util.List;

public class SubjectAdapter extends RecyclerView.Adapter<SubjectAdapter.ViewHolder> {
    List<String> titles;
    LayoutInflater inflater;
    Context context;
    private final String HISTORY_CARD_URL =  "https://static.kanopy.com/cdn-cgi/image/fit=cover,height=540,width=960/https://static-assets.kanopy.com/video-images/5ed68f3d-6522-4a43-bcee-e24c4e84a8d6.jpg";
    private final String MATH_CARD_URL = "https://p2.piqsels.com/preview/939/797/86/calculator-the-calculation-of-casio-zero.jpg";
    private final String ENGLISH_CARD_URL = "https://images5.alphacoders.com/394/thumb-1920-394862.jpg";
    private final String SCIENCE_CARD_URL = "https://images5.alphacoders.com/110/thumb-1920-1107096.jpg";
    private final String GOVERNMENT_CARD_URL = "https://free4kwallpapers.com/uploads/originals/2021/03/10/united-states-capitol-building-wallpaper.jpg";
    private final String ECONOMICS_CARD_URL = "https://cutewallpaper.org/21/economy-wallpaper/The-Circular-Economy-Core-Concepts-Explained.-Understand-.jpg";

    public SubjectAdapter(Context ctx, List<String> titles) {
        this.titles = titles;
        this.inflater = LayoutInflater.from(ctx);
        this.context = ctx;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.subject_card_grid_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SubjectAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.title.setText(titles.get(position));

        set4KCards(holder);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getTutoringHelp(position);
            }

            private void getTutoringHelp(int index) {
                Bundle bundle = new Bundle();
                bundle.putString("subject", titles.get(position));
                Intent i = new Intent(context, DetailActivity.class);
                i.putExtras(bundle);
                context.startActivity(i);
            }
        });
    }

    private void set4KCards(ViewHolder holder) {
        if (holder.title.getText().toString().equals("Math")) {
            Glide.with(context).load(MATH_CARD_URL)
                    .into(new SimpleTarget<Drawable>() {
                        @Override
                        public void onResourceReady(@NonNull Drawable resource, @Nullable com.bumptech.glide.request.transition.Transition<? super Drawable> transition) {
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                                // holder.itemView.setBackground(resource);
                                holder.rlCardImageContainer.setBackground(resource);
                            }
                        }});
        } else if (holder.title.getText().toString().equals("English")) {
            Glide.with(context).load(ENGLISH_CARD_URL)
                    .into(new SimpleTarget<Drawable>() {
                        @Override
                        public void onResourceReady(@NonNull Drawable resource, @Nullable com.bumptech.glide.request.transition.Transition<? super Drawable> transition) {
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                                // holder.itemView.setBackground(resource);
                                holder.rlCardImageContainer.setBackground(resource);
                            }
                        }});
        }
        else if (holder.title.getText().toString().equals("Science")) {
            Glide.with(context).load(SCIENCE_CARD_URL)
                    .into(new SimpleTarget<Drawable>() {
                        @Override
                        public void onResourceReady(@NonNull Drawable resource, @Nullable com.bumptech.glide.request.transition.Transition<? super Drawable> transition) {
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                                // holder.itemView.setBackground(resource);
                                holder.rlCardImageContainer.setBackground(resource);
                            }
                        }});
        }
        else if (holder.title.getText().toString().equals("History")) {
            Glide.with(context).load(HISTORY_CARD_URL)
                    .into(new SimpleTarget<Drawable>() {
                        @Override
                        public void onResourceReady(@NonNull Drawable resource, @Nullable com.bumptech.glide.request.transition.Transition<? super Drawable> transition) {
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                                // holder.itemView.setBackground(resource);
                                holder.rlCardImageContainer.setBackground(resource);
                            }
                        }});
        }
        else if (holder.title.getText().toString().equals("Government")) {
            Glide.with(context).load(GOVERNMENT_CARD_URL)
                    .into(new SimpleTarget<Drawable>() {
                        @Override
                        public void onResourceReady(@NonNull Drawable resource, @Nullable com.bumptech.glide.request.transition.Transition<? super Drawable> transition) {
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                                // holder.itemView.setBackground(resource);
                                holder.rlCardImageContainer.setBackground(resource);
                            }
                        }});
        }
        else if (holder.title.getText().toString().equals("Economics")) {
            Glide.with(context).load(ECONOMICS_CARD_URL)
                    .into(new SimpleTarget<Drawable>() {
                        @Override
                        public void onResourceReady(@NonNull Drawable resource, @Nullable com.bumptech.glide.request.transition.Transition<? super Drawable> transition) {
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                                // holder.itemView.setBackground(resource);
                                holder.rlCardImageContainer.setBackground(resource);
                            }
                        }});
        }
    }

    @Override
    public int getItemCount() {
        return titles.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        RelativeLayout rlCardImageContainer;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.tvTitle);
            rlCardImageContainer = itemView.findViewById(R.id.rlCardImageContainer);
        }
    }
}
