package com.example.grutor.Modals;

import com.parse.ParseClassName;
import com.parse.ParseObject;
import com.parse.ParseUser;

@ParseClassName("Message")
public class Message extends ParseObject {
    public static final String KEY_USER_FROM = "userFrom";
    public static final String KEY_MESSAGE_BODY = "message";
    public static final String KEY_GROUPCHAT_POINTER = "groupchat";

    public ParseUser getUserFrom() {return getParseUser(KEY_USER_FROM);
    }

    public void setUserFrom(ParseUser user) { put(KEY_USER_FROM, user);}

    public String getMessage() {
        return getString(KEY_MESSAGE_BODY);
    }

    public void setMessageBody(String message) {
        put(KEY_MESSAGE_BODY, message);
    }

    public Groupchat getGroupChatPointer() {return (Groupchat) getParseObject(KEY_GROUPCHAT_POINTER);}

    public void setGroupChatPointer(Groupchat groupchat) {put(KEY_GROUPCHAT_POINTER, groupchat);}
}