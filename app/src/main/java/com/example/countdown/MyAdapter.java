package com.example.countdown;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyViewHolder> {

    Context context;
    ArrayList<Countdown> list;

    public MyAdapter(Context context, ArrayList<Countdown> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.item_view, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.countdownTitleTextView.setText(list.get(position).getTitle());
        holder.daysValueTextView.setText(String.valueOf(list.get(position).getRemainingTime().toDays()));
        holder.hoursValueTextView.setText(String.valueOf(list.get(position).getRemainingTime().toHours() % 24));
        holder.minutesValueTextView.setText(String.valueOf(list.get(position).getRemainingTime().toMinutes() % 60));
        holder.secondsValueTextView.setText(String.valueOf(list.get(position).getRemainingTime().toMillis() % 60));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
