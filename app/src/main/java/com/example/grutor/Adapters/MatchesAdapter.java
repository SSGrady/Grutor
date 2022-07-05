package com.example.grutor.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.grutor.Modals.Lessons;
import com.example.grutor.R;
import com.example.grutor.Utility.studentMatcher;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.List;

public class MatchesAdapter extends RecyclerView.Adapter<MatchesAdapter.ViewHolder> {
    List<ParseUser> users;
    LayoutInflater inflater;
    Context context;

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView tvMatchedName;
        public ImageView ivMatchedStudent;
        public Button btnAccept, btnDelete, btnSubjectTopic;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvMatchedName = itemView.findViewById(R.id.tvMatchedName);
            ivMatchedStudent = itemView.findViewById(R.id.ivMatchedStudent);
            btnAccept = itemView.findViewById(R.id.btnAccept);
            btnDelete = itemView.findViewById(R.id.btnDelete);
            btnSubjectTopic = itemView.findViewById(R.id.btnSubjectTopic);
        }
    }

    public MatchesAdapter(Context ctx, List<ParseUser> users) {
        this.users = users;
        this.inflater = LayoutInflater.from(ctx);
        this.context = ctx;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.activity_matching, parent, false);
        return new ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull MatchesAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.tvMatchedName.setText(users.get(position).getUsername());
        Glide.with(context)
                .load(users.get(position).getParseFile("profilePhoto").getUrl())
                .circleCrop() // create an effect of a round profile picture
                .into(holder.ivMatchedStudent);
//        holder.btnAccept.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return users.size();
    }
}
