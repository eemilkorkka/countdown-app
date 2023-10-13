package com.example.countdown;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.time.Duration;
import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyViewHolder> {

    private Context context;
    private ArrayList<Countdown> list;
    private RecyclerView recyclerView;
    private Handler handler = new Handler();

    public MyAdapter(Context context, ArrayList<Countdown> list, RecyclerView recyclerView) {
        this.context = context;
        this.list = list;
        this.recyclerView = recyclerView;

        startCountdownUpdates();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.item_view, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Countdown countdown = list.get(position);
        holder.countdownTitleTextView.setText(countdown.getTitle());
        updateCountdownTextViews(holder, countdown.getRemainingTime());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, EditCountdownActivity.class);
                intent.putExtra("title", countdown.getTitle());
                intent.putExtra("date", countdown.getDate());
                intent.putExtra("time", countdown.getTime());
                intent.putExtra("countdownID", countdown.getCountdownID());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    private void startCountdownUpdates() {
        // Creating a Runnable to update countdown values every second
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < list.size(); i++) {
                    Countdown countdown = list.get(i);
                    MyViewHolder holder = (MyViewHolder) recyclerView.findViewHolderForAdapterPosition(i);
                    if (holder != null) {
                        updateCountdownTextViews(holder, countdown.getRemainingTime());
                    }
                }
                // Schedule the next update in 1 second
                handler.postDelayed(this, 1000);
            }
        }, 1000); // Initial delay of 1 second
    }

    private void updateCountdownTextViews(MyViewHolder holder, Duration remainingTime) {
        long totalSeconds = remainingTime.getSeconds();
        long days = totalSeconds / (60 * 60 * 24);
        long hours = (totalSeconds % (60 * 60 * 24)) / (60 * 60);
        long minutes = (totalSeconds % (60 * 60)) / 60;
        long seconds = totalSeconds % 60;

        holder.daysValueTextView.setText(String.valueOf(days));
        holder.hoursValueTextView.setText(String.valueOf(hours));
        holder.minutesValueTextView.setText(String.valueOf(minutes));
        holder.secondsValueTextView.setText(String.valueOf(seconds));
    }
}