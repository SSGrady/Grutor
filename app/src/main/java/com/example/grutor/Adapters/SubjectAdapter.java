package com.example.grutor.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.grutor.Activites.DetailActivity;
import com.example.grutor.Activites.FeedActivity;
import com.example.grutor.R;

import java.util.List;

public class SubjectAdapter extends RecyclerView.Adapter<SubjectAdapter.ViewHolder> {
    List<String> titles;
    List<Integer> images;
    LayoutInflater inflater;
    Context context;

    public SubjectAdapter(Context ctx, List<String> titles, List<Integer> images) {
        this.titles = titles;
        this.images = images;
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
        // TODO: View collapse? look into it for the Detail Activity. tvitem.setVisibility(view.GONE);
        // TODO Create a list of topics for each subject. Ex: Math - [PreAlgebra, Algebra I, Geometry Trig, Algebra II, Calculus]
        holder.title.setText(titles.get(position));
        holder.gridIcon.setImageResource(images.get(position));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Display a toast message to the user
                // Display the title of the card
                Toast.makeText(inflater.getContext(), "You clicked: " + titles.get(position), Toast.LENGTH_SHORT).show();
                getTutoringHelp(position);
            }

            private void getTutoringHelp(int index) {
                Bundle bundle = new Bundle();
                bundle.putString("subject", titles.get(position));
                Intent i = new Intent(context, DetailActivity.class);
                // only way to access my bundled String
                i.putExtras(bundle);
                context.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return titles.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        ImageView gridIcon;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            gridIcon = itemView.findViewById(R.id.ivGridIcon);
            title = itemView.findViewById(R.id.tvTitle);

        }
    }
}
