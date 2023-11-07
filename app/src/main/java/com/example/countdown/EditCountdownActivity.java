package com.example.countdown;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class EditCountdownActivity extends AppCompatActivity {

    private Button saveCountdownButton;
    private FloatingActionButton returnToHomeButton, deleteCountdownButton;
    private String title, date, time, countdownID;
    private TextView countdownTitle;
    private EditText countdownDate, countdownTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editcountdown);

        saveCountdownButton = findViewById(R.id.updateCountdownBtn);
        returnToHomeButton = findViewById(R.id.backToHomeBtn);
        deleteCountdownButton = findViewById(R.id.deleteCountdownBtn);
        countdownTitle = findViewById(R.id.editTextTitle);
        countdownDate = findViewById(R.id.editTextDate);
        countdownTime = findViewById(R.id.editTextTime);

        title = getIntent().getStringExtra("title");
        date = getIntent().getStringExtra("date");
        time = getIntent().getStringExtra("time");
        countdownID = getIntent().getStringExtra("countdownID");

        countdownTitle.setText(title);
        countdownDate.setText(date);
        countdownTime.setText(time);

        saveCountdownButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String updatedTitle = countdownTitle.getText().toString();
                String updatedDate = countdownDate.getText().toString();
                String updatedTime = countdownTime.getText().toString();

                updateCountdown(updatedTitle, updatedDate, updatedTime);
            }
        });

        returnToHomeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(EditCountdownActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        deleteCountdownButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteCountdown();
            }
        });

        countdownDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerFragment().show(getSupportFragmentManager(), "datePicker");
            }
        });

        countdownTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new TimePickerFragment().show(getSupportFragmentManager(), "timePicker");
            }
        });
    }

    private void updateCountdown(String title, String date, String time) {

        HashMap<String, Object> updatedData = new HashMap<>();
        updatedData.put("title", title);
        updatedData.put("date", date);
        updatedData.put("time", time);

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Countdowns");
        databaseReference.child(countdownID).updateChildren(updatedData).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), "Countdown updated successfully!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Failed to update countdown.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void deleteCountdown() {

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Countdowns");
        databaseReference.child(countdownID).removeValue();

        Intent intent = new Intent(EditCountdownActivity.this, MainActivity.class);
        startActivity(intent);
    }
}