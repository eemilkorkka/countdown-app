package com.example.countdown;


import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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

    public Duration getRemainingTime() {

        LocalDateTime currentTime = LocalDateTime.now();
        LocalDateTime targetTime = LocalDateTime.parse(date + " " + time, DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm"));

        Duration duration = Duration.between(currentTime, targetTime);

        return duration;
    }
}
