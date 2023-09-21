package com.example.countdown;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.UUID;

public class Countdown {

    private String title, date, time, userID;

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getTitle() {
        return title;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public String getUserID() {
        return userID;
    }
}
