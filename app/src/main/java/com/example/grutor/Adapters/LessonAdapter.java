package com.example.grutor.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.grutor.Modals.Lessons;
import com.example.grutor.R;
import com.parse.ParseUser;

import java.util.List;

public class LessonAdapter extends RecyclerView.Adapter<LessonAdapter.ViewHolder> {
    List<Lessons> lessons;
    LayoutInflater inflater;
    Context context;

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView tvNumType, tvDateTime;
        public EditText etBundledDescription;
        public ImageView ivSubjectLesson;
        public Button btnSubjectTopic;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNumType = itemView.findViewById(R.id.tvNumType);
            tvDateTime = itemView.findViewById(R.id.tvDateTime);
            etBundledDescription = itemView.findViewById(R.id.etBundledDescription);
            ivSubjectLesson = itemView.findViewById(R.id.ivSubjectLesson);
            btnSubjectTopic = itemView.findViewById(R.id.btnSubjectTopic);
        }
    }

    public LessonAdapter(Context ctx, List<Lessons> lessons) {
        this.lessons = lessons;
        this.inflater = LayoutInflater.from(ctx);
        this.context = ctx;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.activity_lesson, parent, false);
        return new ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull LessonAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Lessons lesson = lessons.get(position);
        holder.etBundledDescription.setText(lesson.getTutoringDescription());
        holder.btnSubjectTopic.setText(lesson.getTutoringSubject() + " · " + lesson.getTutoringTopic());
        holder.tvDateTime.setText(lesson.getCalendarDate());
        if (lesson.getTypeOfLesson().equals("Essay") || lesson.getTypeOfLesson().equals("Other")) {
            holder.tvNumType.setText(lesson.getTypeOfLesson());
        } else {
            holder.tvNumType.setText(lesson.getTypeOfLesson() + " · " + lesson.getAssignmentLength());
        }
        switch(lesson.getTutoringSubject()) {
            case "English":
                holder.ivSubjectLesson.setImageResource(R.drawable.icons8_english_64);
                break;
            case "Science":
                holder.ivSubjectLesson.setImageResource(R.drawable.icons8_physics_64);
                break;
            case "History":
                holder.ivSubjectLesson.setImageResource(R.drawable.icons8_history_64);
                break;
            case "Government":
                holder.ivSubjectLesson.setImageResource(R.drawable.icons8_government_64);
                break;
            case "Economics":
                holder.ivSubjectLesson.setImageResource(R.drawable.icons8_stock_share_64);
                break;
            case "Math":
                holder.ivSubjectLesson.setImageResource(R.drawable.icons8_math_64);
                break;
        }
    }

    @Override
    public int getItemCount() {
        return lessons.size();
    }
}
