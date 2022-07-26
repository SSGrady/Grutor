package com.example.grutor.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.grutor.Activites.FeedActivity;
import com.example.grutor.Adapters.ChatAdapter;
import com.example.grutor.Modals.Lessons;
import com.example.grutor.Modals.Message;
import com.example.grutor.R;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.parse.livequery.ParseLiveQueryClient;
import com.parse.livequery.SubscriptionHandling;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class MessagesFragment extends Fragment {

    protected EditText etMessage;
    protected ImageButton ibSend;
    static final int MAX_CHAT_MESSAGES_TO_SHOW = 50;
    protected static RecyclerView rvChat;
    protected ArrayList<Message> mMessages;
    protected boolean mFirstLoad;
    public ChatAdapter mAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_messages, parent, false);
    }

    // This event is triggered soon after onCreateView().
    // Any view setup should occur here.  E.g., view lookups and attaching view listeners.
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        // Find the text field and button
        etMessage = view.findViewById(R.id.etMessage);
        ibSend = view.findViewById(R.id.ibSend);
        rvChat = view.findViewById(R.id.rvChat);
        mMessages = new ArrayList<>();
        mFirstLoad = true;
        final String userFrom = ParseUser.getCurrentUser().getObjectId();
        mAdapter = new ChatAdapter(getContext(), userFrom, mMessages);
        rvChat.setAdapter(mAdapter);

        // associate the LayoutManager with the RecyclerView
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, true);
        rvChat.setLayoutManager(linearLayoutManager);


        if (ParseUser.getCurrentUser() != null) { // start with existing user
            startWithCurrentUser();
        }
        // Make sure the Parse server is setup to configured for live queries
        // Enter the websocket URL of your Parse server
        String websocketUrl = "wss://grutor.b4a.io/";

        ParseLiveQueryClient parseLiveQueryClient = null;
        try {
            parseLiveQueryClient = ParseLiveQueryClient.Factory.getClient(new URI(websocketUrl));
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        ParseQuery<Message> parseQuery = ParseQuery.getQuery(Message.class);

        SubscriptionHandling<Message> subscriptionHandling = parseLiveQueryClient.subscribe(parseQuery);

        subscriptionHandling.handleEvent(SubscriptionHandling.Event.CREATE, (query, object) -> {
            mMessages.add(0, object);

//             RecyclerView updates need to be run on the UI thread
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mAdapter.notifyDataSetChanged();
                    rvChat.scrollToPosition(0);
                }
            });
        });
        refreshMessages();
    }


    // Get the userId from the cached currentUser object
    private void startWithCurrentUser() {
        setupMessagePosting();
    }

    // Set up button event handler which posts the entered message to Parse
    private void setupMessagePosting() {
        // When send button is clicked, create message object on Parse
        ibSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = getArguments();
                Lessons currentLesson = (Lessons) bundle.getParcelable("Lesson");
                String data = etMessage.getText().toString();
                Message message = new Message();
                message.setUserFrom(ParseUser.getCurrentUser());
                message.setMessageBody(data);
                message.setGroupChatPointer(currentLesson.getGroupChat());
                message.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(ParseException e) {
                        if (e == null) {
                            refreshMessages();
                        } else {
                            Log.e("ChatActivity", "Failed to save message", e);
                        }
                    }

                    final long POLL_INTERVAL = TimeUnit.SECONDS.toMillis(3);
                    Handler myHandler = new android.os.Handler();
                    Runnable mRefreshMessagesRunnable = new Runnable() {
                        @Override
                        public void run() {
                            myHandler.postDelayed(this, POLL_INTERVAL);
                        }
                    };
                });
                etMessage.setText(null);
            }
        });
    }

    public void refreshMessages() {
        // Construct query to execute
        ParseQuery<Message> query = ParseQuery.getQuery(Message.class);
        // Configure limit and sort order
        query.setLimit(MAX_CHAT_MESSAGES_TO_SHOW);

        // get the latest 50 messages, order will show up newest to oldest of this group
        query.orderByDescending("createdAt");

        // prevents index out of bounds error.
        Bundle bundle = getArguments();
        Lessons currentLesson = (Lessons) bundle.getParcelable("Lesson");
        query.whereEqualTo("groupchat", currentLesson.getGroupChat());
        query.include(Message.KEY_USER_FROM);

        // Execute query to fetch all messages from Parse asynchronously
        // This is equivalent to a SELECT query with SQL
        query.findInBackground(new FindCallback<Message>() {
            public void done(List<Message> messages, ParseException e) {
                if (e == null) {
                    mMessages.clear();
                    mMessages.addAll(messages);
                    mAdapter.notifyDataSetChanged(); // update adapter
                    // Scroll to the bottom of the list on initial load
                    if (mFirstLoad) {
                        rvChat.scrollToPosition(0);
                        mFirstLoad = false;
                    }
                }
            }
        });
    }


}