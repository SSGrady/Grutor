package com.example.grutor.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.example.grutor.Activites.FeedActivity;
import com.example.grutor.Fragments.MessagesFragment;
import com.example.grutor.Modals.Lessons;
import com.example.grutor.R;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import dots.animation.textview.TextAndAnimationView;

public class LessonAdapter extends RecyclerView.Adapter<LessonAdapter.ViewHolder> {
    private List<Lessons> lessons;
    protected LayoutInflater inflater;
    protected Context context;
    private FeedActivity instance;
    public String requestedLessonString;
    public Lessons requestedLesson;
    private ParseUser snapshot_removed;
    private List<ParseUser> deleted;
    private ParseQuery<ParseUser> query;
    private final String KEY_QUERY_BY_USERNAME = "username";

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView tvSubjectTopic, tvTypeOfNumProblems, tvNeedByUrgency, tvMatchStatus;
        public TextAndAnimationView tvMatchStatusDots;
        public EditText etTutoringDescription;
        public ImageView ivLessonIcon;
        protected FloatingActionButton fabChat;
        public MaterialButton mbtnMatch;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivLessonIcon = itemView.findViewById(R.id.ivLessonIcon);
            tvSubjectTopic = itemView.findViewById(R.id.tvSubjectTopic);
            tvTypeOfNumProblems = itemView.findViewById(R.id.tvTypeOfNumProblems);
            tvNeedByUrgency = itemView.findViewById(R.id.tvNeedByUrgency);
            tvMatchStatus = itemView.findViewById(R.id.tvMatchStatus);
            tvMatchStatusDots = itemView.findViewById(R.id.tvMatchStatusDots);
            mbtnMatch = itemView.findViewById(R.id.mbtnMatch);
            etTutoringDescription = itemView.findViewById(R.id.etTutoringDescription);
            fabChat = itemView.findViewById(R.id.fabChat);
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
        instance = (FeedActivity) ctx;
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
        holder.etTutoringDescription.setText(lesson.getTutoringDescription());
        holder.tvSubjectTopic.setText(lesson.getTutoringSubject().toUpperCase(Locale.ROOT) + " · " + lesson.getTutoringTopic());

        setLessons(holder, position);
        setLessonIcons(holder, lesson);
        setMatchButton(holder, position);

        holder.tvNeedByUrgency.setText(lesson.getCalendarDate());

        holder.mbtnMatch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setLessons(holder, position);
                holder.mbtnMatch.setVisibility(View.GONE);
                instance.lessonListener.onLessonChanged(lesson);
                instance.holder = holder;
            }
        });
        if (lesson.getTypeOfLesson().equals("Essay") || lesson.getTypeOfLesson().equals("Other")) {
            holder.tvTypeOfNumProblems.setText(lesson.getTypeOfLesson());
        } else {
            holder.tvTypeOfNumProblems.setText(lesson.getTypeOfLesson() + " · " + lesson.getAssignmentLength());
        }

        setMatchStatus(holder, lesson);

        holder.fabChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                if (lesson != null) {
                    bundle.putParcelable("Lesson", lesson);
                }
                Fragment fragment = new MessagesFragment();
                fragment.setArguments(bundle);
                ((FeedActivity) context).getSupportFragmentManager().beginTransaction().replace(R.id.flContainer, fragment, "Messages").commit();
            }
        });
    }

    private void setMatchButton(ViewHolder holder, int position) {
        if (lessons.get(position).getStudentTutor() != null) {
            holder.mbtnMatch.setVisibility(View.GONE);
        }
    }

    public void setMatchStatus(ViewHolder holder, Lessons lesson) {
        setFabChatVisibility(holder);
        if (lesson.getIsGroupChat()) {
            if (lesson.getGroupChat().getParticipants().get(0).getUsername().equals(ParseUser.getCurrentUser().getUsername())) {
                holder.tvMatchStatus.setText(lesson.getGroupChat().getParticipants().get(1).getUsername());
            } else {
                holder.tvMatchStatus.setText(lesson.getGroupChat().getParticipants().get(0).getUsername());
            }
            holder.tvMatchStatus.setTextColor(Color.parseColor("#4CAF50"));
            holder.tvMatchStatusDots.noOfDots(0);
            holder.tvMatchStatusDots.stopAnimation();
            holder.tvMatchStatusDots.setVisibility(View.GONE);
        }
    }

    private void setFabChatVisibility(ViewHolder holder) {
        if (requestedLesson.getStudentTutor() != null) {
            holder.fabChat.setVisibility(View.VISIBLE);
            instance.requestedLesson = null;
        } else {
            instance.requestedLesson = requestedLesson;
        }
    }

    // for matching interface
    public void makeMatchButtonVisible(ViewHolder holder) {
        holder.mbtnMatch.setVisibility(View.VISIBLE);
    }


    @SuppressLint("ResourceAsColor")
    private void setLessons(ViewHolder holder, int position) {
        if (!holder.etTutoringDescription.getText().toString().isEmpty())
        {
            holder.etTutoringDescription.setVisibility(View.VISIBLE);
        }
        requestedLessonString = lessons.get(position).getTutoringSubject();
        requestedLesson = lessons.get(position);
        setFabChatVisibility(holder);
    }

    private void setLessonIcons(ViewHolder holder, Lessons lesson) {
        switch(lesson.getTutoringSubject()) {
            case "English":
                String ENGLISH_CARD_URL = "https://images5.alphacoders.com/394/thumb-1920-394862.jpg";
                Glide.with(context).load(ENGLISH_CARD_URL)
                        .into(new SimpleTarget<Drawable>() {
                            @Override
                            public void onResourceReady(@NonNull Drawable resource, @Nullable com.bumptech.glide.request.transition.Transition<? super Drawable> transition) {
                                holder.ivLessonIcon.setBackground(resource);
                            }});
                break;
            case "Science":
                String SCIENCE_CARD_URL = "https://cdn.shopify.com/s/files/1/0600/9130/2138/files/home-2-hero-1_2560x.jpg?v=1634081389";
                Glide.with(context).load(SCIENCE_CARD_URL)
                        .into(new SimpleTarget<Drawable>() {
                            @Override
                            public void onResourceReady(@NonNull Drawable resource, @Nullable com.bumptech.glide.request.transition.Transition<? super Drawable> transition) {
                                holder.ivLessonIcon.setBackground(resource);
                            }});
                break;
            case "History":
                String HISTORY_CARD_URL = "https://static.kanopy.com/cdn-cgi/image/fit=cover,height=540,width=960/https://static-assets.kanopy.com/video-images/5ed68f3d-6522-4a43-bcee-e24c4e84a8d6.jpg";
                Glide.with(context).load(HISTORY_CARD_URL)
                        .into(new SimpleTarget<Drawable>() {
                            @Override
                            public void onResourceReady(@NonNull Drawable resource, @Nullable com.bumptech.glide.request.transition.Transition<? super Drawable> transition) {
                                holder.ivLessonIcon.setBackground(resource);
                            }});
                break;
            case "Government":
                String GOVERNMENT_CARD_URL = "https://free4kwallpapers.com/uploads/originals/2021/03/10/united-states-capitol-building-wallpaper.jpg";
                Glide.with(context).load(GOVERNMENT_CARD_URL)
                        .into(new SimpleTarget<Drawable>() {
                            @Override
                            public void onResourceReady(@NonNull Drawable resource, @Nullable com.bumptech.glide.request.transition.Transition<? super Drawable> transition) {
                                holder.ivLessonIcon.setBackground(resource);
                            }});
                break;
            case "Economics":
                String ECONOMICS_CARD_URL = "https://cutewallpaper.org/21/economy-wallpaper/The-Circular-Economy-Core-Concepts-Explained.-Understand-.jpg";
                Glide.with(context).load(ECONOMICS_CARD_URL)
                        .into(new SimpleTarget<Drawable>() {
                            @Override
                            public void onResourceReady(@NonNull Drawable resource, @Nullable com.bumptech.glide.request.transition.Transition<? super Drawable> transition) {
                                holder.ivLessonIcon.setBackground(resource);
                            }});
                break;
            case "Math":
                String MATH_CARD_URL = "https://p2.piqsels.com/preview/939/797/86/calculator-the-calculation-of-casio-zero.jpg";
                Glide.with(context).load(MATH_CARD_URL)
                        .into(new SimpleTarget<Drawable>() {
                            @Override
                            public void onResourceReady(@NonNull Drawable resource, @Nullable com.bumptech.glide.request.transition.Transition<? super Drawable> transition) {
                                holder.ivLessonIcon.setBackground(resource);
                            }});
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
        if (lessons.get(position).getStudentTutor() != null) {
            if (lessons.get(position).getStudentTutor().hasSameId(deleted.get(0))){
                lessons.get(position).setStudentTutor(snapshot_removed);
            } else {
                lessons.get(position).setStudent(snapshot_removed);
            }
        } else {
            lessons.get(position).setStudent(snapshot_removed);
        }
        notifyItemInserted(position);
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
