package com.example.grutor.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.database.DataSetObserver;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.example.grutor.Activites.FeedActivity;
import com.example.grutor.Fragments.MessagesFragment;
import com.example.grutor.Modals.Groupchat;
import com.example.grutor.Modals.Lessons;
import com.example.grutor.R;
import com.example.grutor.Utility.studentMatcher;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.parse.ParseException;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

public class LessonAdapter extends RecyclerView.Adapter<LessonAdapter.ViewHolder> {
    public List<Lessons> lessons;
    protected LayoutInflater inflater;
    protected Context context;
    public String requestedLessonString;
    public Lessons requestedLesson;

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView tvNumType, tvDateTime;
        public EditText etBundledDescription;
        public ImageView ivSubjectLesson;
        protected FloatingActionButton fabChat;
        public Button btnSubjectTopic;
        public boolean clicky;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNumType = itemView.findViewById(R.id.tvNumType);
            tvDateTime = itemView.findViewById(R.id.tvDateTime);
            etBundledDescription = itemView.findViewById(R.id.etBundledDescription);
            ivSubjectLesson = itemView.findViewById(R.id.ivSubjectLesson);
            btnSubjectTopic = itemView.findViewById(R.id.btnSubjectTopic);
            fabChat = itemView.findViewById(R.id.fabChat);
            clicky = true;
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
        getAppropriateLessons(holder, position);

        holder.btnSubjectTopic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setLessons(holder, position);
            }
        });
        setLessonIcons(holder, lesson);
        holder.tvDateTime.setText(lesson.getCalendarDate());
        if (lesson.getTypeOfLesson().equals("Essay") || lesson.getTypeOfLesson().equals("Other")) {
            holder.tvNumType.setText(lesson.getTypeOfLesson());
        } else {
            holder.tvNumType.setText(lesson.getTypeOfLesson() + " · " + lesson.getAssignmentLength());
        }
        holder.fabChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!lesson.getIsGroupChat()) {
                    Groupchat groupchat = new Groupchat();
                    ArrayList<ParseUser> participants = new ArrayList<>();
                    participants.add(lesson.getStudent());
                    participants.add(lesson.getStudentTutor());
                    groupchat.setParticipants(participants);
                    groupchat.saveInBackground();
                    lesson.setIsGroupChat(true);
                    lesson.saveInBackground();
                }
                Fragment fragment = new MessagesFragment();
                ((FeedActivity) context).getSupportFragmentManager().beginTransaction().replace(R.id.flContainer, fragment).commit();
            }
        });
    }

    private void getAppropriateLessons(ViewHolder holder, int position) {
        ParseUser currentUser = ParseUser.getCurrentUser();
        // Make sure that lessons are allocated to Matched students only.
        if (lessons.get(position).getStudentTutor() != null) {
            if (!currentUser.getObjectId().equals(lessons.get(position).getStudentTutor().getObjectId())
                    && !currentUser.getObjectId().equals(lessons.get(position).getStudent().getObjectId())) {
                holder.btnSubjectTopic.setVisibility(View.GONE);
            }
        } // if there is no studentTutor then this user should not see anyone else's lesson
        else if (!currentUser.getObjectId().equals(lessons.get(position).getStudent().getObjectId())){
            holder.btnSubjectTopic.setVisibility(View.GONE);
        }
    }

    private void setLessons(ViewHolder holder, int position) {
        if (holder.clicky) {
            holder.ivSubjectLesson.setVisibility(View.VISIBLE);
            if (!holder.etBundledDescription.getText().toString().isEmpty())
            {
                holder.etBundledDescription.setVisibility(View.VISIBLE);
            }
            // Formatting conditional
            else {
                holder.etBundledDescription.setVisibility(View.INVISIBLE);
            }
            holder.tvNumType.setVisibility(View.VISIBLE);
            holder.tvDateTime.setVisibility(View.VISIBLE);
            holder.clicky = false;
            requestedLessonString = lessons.get(position).getTutoringSubject();
            requestedLesson = lessons.get(position);
            if (requestedLesson.getStudentTutor() != null) {
                holder.fabChat.setVisibility(View.VISIBLE);
            }
        }
        else {
            holder.clicky = true;
            holder.ivSubjectLesson.setVisibility(View.GONE);
            holder.etBundledDescription.setVisibility(View.INVISIBLE);
            holder.tvNumType.setVisibility(View.GONE);
            holder.tvDateTime.setVisibility(View.GONE);
            requestedLessonString = "";
            requestedLesson = null;
            holder.fabChat.setVisibility(View.GONE);
        }
    }

    private void setLessonIcons(ViewHolder holder, Lessons lesson) {
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
