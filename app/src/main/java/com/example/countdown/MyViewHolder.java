package com.example.countdown;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MyViewHolder extends RecyclerView.ViewHolder {

    TextView countdownTitleTextView, daysValueTextView, hoursValueTextView, minutesValueTextView, secondsValueTextView;

    public MyViewHolder(@NonNull View itemView) {
        super(itemView);

        countdownTitleTextView = itemView.findViewById(R.id.countdownTitleTextView);
        daysValueTextView = itemView.findViewById(R.id.daysValueTextView);
        hoursValueTextView = itemView.findViewById(R.id.hoursValueTextView);
        minutesValueTextView = itemView.findViewById(R.id.minutesValueTextView);
        secondsValueTextView = itemView.findViewById(R.id.secondsValueTextView);
    }
}
