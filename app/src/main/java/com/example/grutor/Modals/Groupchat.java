package com.example.grutor.Modals;

import com.parse.ParseClassName;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

@ParseClassName("Groupchat")
public class Groupchat extends ParseObject {
    public static final String KEY_GROUPCHAT_ID = "objectId";
    public static final String KEY_LIST_OF_PARTICIPANTS = "participants";

    public String getGroupchatId() {return getString(KEY_GROUPCHAT_ID);}
    public void setGroupChatId(String groupChatId) {put(KEY_GROUPCHAT_ID, groupChatId);}
    public List<ParseUser> getParticipants() {
        try {
            return fetchIfNeeded().getList(KEY_LIST_OF_PARTICIPANTS);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return getList(KEY_LIST_OF_PARTICIPANTS);
    }
    public void setParticipants(ArrayList<ParseUser> participants) {put(KEY_LIST_OF_PARTICIPANTS, participants);}
}
