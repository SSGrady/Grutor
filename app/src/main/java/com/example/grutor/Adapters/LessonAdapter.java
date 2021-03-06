package com.example.grutor.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.grutor.Activites.FeedActivity;
import com.example.grutor.Fragments.MessagesFragment;
import com.example.grutor.Modals.Groupchat;
import com.example.grutor.Modals.Lessons;
import com.example.grutor.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

public class LessonAdapter extends RecyclerView.Adapter<LessonAdapter.ViewHolder> {
    private List<Lessons> lessons;
    protected LayoutInflater inflater;
    protected Context context;
    public String requestedLessonString;
    public Lessons requestedLesson;
    private ParseUser snapshot_removed;
    private List<ParseUser> deleted;
    private ParseQuery<ParseUser> query;
    private final String KEY_QUERY_BY_USERNAME = "username";

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
        snapshot_removed = ParseUser.getCurrentUser();
        deleted = new ArrayList<>();
        query = ParseUser.getQuery();
        query.whereEqualTo(KEY_QUERY_BY_USERNAME, "Deleted");
        try {
            deleted.addAll(query.find());
        } catch (ParseException e) {
            e.printStackTrace();
        }
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
        holder.btnSubjectTopic.setText(lesson.getTutoringSubject() + " ?? " + lesson.getTutoringTopic());

        holder.btnSubjectTopic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { setLessons(holder, position); }
        });

        setLessonIcons(holder, lesson, position);
        holder.tvDateTime.setText(lesson.getCalendarDate());
        if (lesson.getTypeOfLesson().equals("Essay") || lesson.getTypeOfLesson().equals("Other")) {
            holder.tvNumType.setText(lesson.getTypeOfLesson());
        } else {
            holder.tvNumType.setText(lesson.getTypeOfLesson() + " ?? " + lesson.getAssignmentLength());
        }
        holder.fabChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                setupGroupChat();
                if (lesson != null) {
                    bundle.putParcelable("Lesson", lesson);
                }
                Fragment fragment = new MessagesFragment();
                fragment.setArguments(bundle);
                ((FeedActivity) context).getSupportFragmentManager().beginTransaction().replace(R.id.flContainer, fragment).commit();
            }

            private void setupGroupChat() {
                if (!lesson.getIsGroupChat()) {
                    Groupchat groupchat = new Groupchat();
                    ArrayList<ParseUser> participants = new ArrayList<>();
                    participants.add(lesson.getStudent());
                    participants.add(lesson.getStudentTutor());
                    groupchat.setParticipants(participants);
                    groupchat.saveInBackground();
                    lesson.setGroupChatPointer(groupchat);
                    lesson.setIsGroupChat(true);
                    lesson.saveInBackground();
                }
            }
        });
    }

    @SuppressLint("ResourceAsColor")
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

    private void setLessonIcons(ViewHolder holder, Lessons lesson, int position) {
        if (lessons.get(position).getStudentTutor() != null) {
            if (lessons.get(position).getStudentTutor().getUsername().equals(ParseUser.getCurrentUser().getUsername())) {
            holder.btnSubjectTopic.setBackgroundResource(R.drawable.round_shape_btn_tutor);
            }
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

    public void removeItem(int position) {
        if (lessons.get(position).getStudentTutor() != null) {

            if (ParseUser.getCurrentUser().hasSameId(lessons.get(position).getStudentTutor())) {
                lessons.get(position).setStudentTutor(deleted.get(0));
            } else {
                lessons.get(position).setStudent(deleted.get(0));
            }
        } else {
            lessons.get(position).setStudent(deleted.get(0));
        }
        lessons.get(position).saveInBackground();
        lessons.remove(position);
        notifyItemRemoved(position);
    }

    public void restoreItem(Lessons lesson, int position) {
        lessons.add(position, lesson);
        notifyItemInserted(position);
        if (lessons.get(position).getStudentTutor() != null) {
            if (lessons.get(position).getStudentTutor().equals(deleted.get(0))){
                lessons.get(position).setStudentTutor(snapshot_removed);
            }
        } else {
            lessons.get(position).setStudent(snapshot_removed);
        }
        lessons.get(position).saveInBackground();
    }

    public List<Lessons> getData() {
        return lessons;
    }

    @Override
    public int getItemCount() {
        return lessons.size();
    }
}
