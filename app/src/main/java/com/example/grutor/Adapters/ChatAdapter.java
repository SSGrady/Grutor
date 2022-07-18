package com.example.grutor.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.grutor.Modals.Message;
import com.example.grutor.R;
import com.parse.ParseObject;
import com.parse.ParseUser;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.List;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.MessageViewHolder> {

    private List<Message> mMessages;
    private Context mContext;
    private String mUserFrom;
    protected ParseUser currentUser;
    private final String KEY_PROFILE_PHOTO = "profilePhoto";
    private static final int MESSAGE_OUTGOING = 18;
    private static final int MESSAGE_INCOMING = 81;

    public ChatAdapter(Context context, String userId, List<Message> messages) {
        mMessages = messages;
        this.mUserFrom = userId;
        mContext = context;
        currentUser = ParseUser.getCurrentUser();
    }

    @Override
    public void onBindViewHolder(MessageViewHolder holder, int position) {
        Message message = mMessages.get(position);
        holder.bindMessage(message);
    }

    @Override
    public int getItemCount() {
        return mMessages.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (isMe(position)) {
            return MESSAGE_OUTGOING;
        } else {
            return MESSAGE_INCOMING;
        }
    }

    private boolean isMe(int position) {
        Message message = mMessages.get(position);
        return message.getUserFrom() != null && message.getUserFrom().getObjectId().equals(mUserFrom);
    }

    public abstract class MessageViewHolder extends RecyclerView.ViewHolder {

        public MessageViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        abstract void bindMessage(Message message);
    }

    // create new views from xml layout and assign them to corresponding view holders
    // In case if the view type is different from the ones defined we will throw an exception
    @Override
    public MessageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        if (viewType == MESSAGE_INCOMING) {
            View contactView = inflater.inflate(R.layout.message_incoming, parent, false);
            return new IncomingMessageViewHolder(contactView);
        } else if (viewType == MESSAGE_OUTGOING) {
            View contactView = inflater.inflate(R.layout.message_outgoing, parent, false);
            return new OutgoingMessageViewHolder(contactView);
        } else {
            throw new IllegalArgumentException("Unknown view type");
        }
    }

    // Other participant in Groupchat.
    public class IncomingMessageViewHolder extends MessageViewHolder {
        ImageView imageOther;
        TextView body;
        TextView name;

        public IncomingMessageViewHolder(View itemView) {
            super(itemView);
            imageOther = (ImageView) itemView.findViewById(R.id.ivProfileOther);
            body = (TextView) itemView.findViewById(R.id.tvBody);
            name = (TextView) itemView.findViewById(R.id.tvName);
        }

        @Override
        public void bindMessage(Message message) {
            Glide.with(mContext)
                    .load(message.getUserFrom().getParseFile(KEY_PROFILE_PHOTO).getUrl())
                    .circleCrop().into(imageOther);
            body.setText(message.getMessage());
            if (message.getGroupChatPointer().getParticipants().get(0).getUsername().equals(currentUser.getUsername()))
            {
                name.setText(message.getGroupChatPointer().getParticipants().get(1).getUsername());
            } else {
                name.setText(message.getGroupChatPointer().getParticipants().get(0).getUsername()); // in addition to message show user ID
            }

        }
    }

    // current User
    public class OutgoingMessageViewHolder extends MessageViewHolder {
        ImageView imageMe;
        TextView body;

        public OutgoingMessageViewHolder(View itemView) {
            super(itemView);
            imageMe = itemView.findViewById(R.id.ivProfileMe);
            body = (TextView) itemView.findViewById(R.id.tvBody);
        }

        @Override
        public void bindMessage(Message message) {
            Glide.with(mContext)
                    .load(message.getUserFrom().getParseFile(KEY_PROFILE_PHOTO).getUrl())
                    .circleCrop().into(imageMe);
            body.setText(message.getMessage());

        }
    }
}