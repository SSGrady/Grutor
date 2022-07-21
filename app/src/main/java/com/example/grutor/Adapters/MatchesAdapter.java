package com.example.grutor.Adapters;

import static com.parse.Parse.getApplicationContext;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.grutor.Modals.Groupchat;
import com.example.grutor.Modals.Lessons;
import com.example.grutor.R;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import es.dmoral.toasty.Toasty;
import nl.dionsegijn.konfetti.core.Party;
import nl.dionsegijn.konfetti.core.PartyFactory;
import nl.dionsegijn.konfetti.core.emitter.Emitter;
import nl.dionsegijn.konfetti.core.emitter.EmitterConfig;
import nl.dionsegijn.konfetti.core.models.Shape;
import nl.dionsegijn.konfetti.xml.KonfettiView;

public class MatchesAdapter extends RecyclerView.Adapter<MatchesAdapter.ViewHolder> {
    List<ParseUser> users;
    LayoutInflater inflater;
    Context context;
    Lessons requestedLesson;

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
        }
    }

    public MatchesAdapter(Context ctx, List<ParseUser> users, Lessons requestedLesson) {
        this.users = users;
        this.inflater = LayoutInflater.from(ctx);
        this.context = ctx;
        this.requestedLesson = requestedLesson;
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
        if (users.get(position) != null && users.get(position).get("profilePhoto") != null) {
            Glide.with(context)
                    .load(users.get(position).getParseFile("profilePhoto").getUrl())
                    .circleCrop() // create an effect of a round profile picture
                    .into(holder.ivMatchedStudent);
        }
        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onClick(View v) {
                users.remove(position);
                notifyDataSetChanged();
            }
        });
        holder.btnAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addStudentTutor(v, position);
                removeMatches();
                setupGroupchat(requestedLesson);
            }
        });
    }

    private void setupGroupchat(Lessons lesson) {
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

    @SuppressLint("NotifyDataSetChanged")
    private void removeMatches() {
        for (int i = 0; i < users.size(); i++) {
            users.clear();
        }
        notifyDataSetChanged();
    }


    @SuppressLint("ResourceType")
    private void addStudentTutor(View v, int position) {
        requestedLesson.setStudentTutor(users.get(position));
        Toasty.success(v.getContext(), "You Matched with " + users.get(position).getUsername() + "!", Toast.LENGTH_SHORT, true).show();
        requestedLesson.saveInBackground();
    }

    @Override
    public int getItemCount() {
        return users.size();
    }
}
