package com.example.countdown;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Network;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class CountdownActivity extends AppCompatActivity {

    private EditText title, date, time;
    private Button addCountdownButton;
    private FloatingActionButton returnToHomeButton;
    private DatabaseReference databaseReference;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addcountdown);

        title = findViewById(R.id.editTextTitle);
        date = findViewById(R.id.editTextDate);
        time = findViewById(R.id.editTextTime);
        addCountdownButton = findViewById(R.id.addCountdownBtn);
        returnToHomeButton = findViewById(R.id.backToHomeBtn);

        databaseReference = FirebaseDatabase.getInstance() .getReference("Countdowns");

        firebaseAuth = FirebaseAuth.getInstance();
        currentUser = firebaseAuth.getCurrentUser();

        addCountdownButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addCountdown();
            }
        });

        returnToHomeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CountdownActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

    }

    private void addCountdown() {
        String inputTitle = title.getText().toString();
        String inputDate = date.getText().toString();
        String inputTime = time.getText().toString();

        // Using regex to make sure that the date is in DD.MM.YYYY format
        String dateFormat = "^(0[1-9]|[12][0-9]|3[01])\\.(0[1-9]|1[0-2])\\.\\d{4}$";

        // Using regex to make sure that the time is in HH:MM format
        String timeFormat = "^(?:[01]\\d|2[0-3]):[0-5]\\d$";

        if (inputDate.matches(dateFormat) && inputTime.matches(timeFormat) && !inputTitle.isEmpty()) {

            if (currentUser != null) {

                String userID = currentUser.getUid();

                Countdown countdown = new Countdown();

                countdown.setTitle(inputTitle);
                countdown.setDate(inputDate);
                countdown.setTime(inputTime);
                countdown.setUserID(userID);

                saveCountdownToDatabase(countdown);
            }

        } else if (!inputDate.matches(dateFormat)) {
            date.setError("Incorrect date format. Please use DD.MM.YYYY.");
        } else if (!inputTime.matches(timeFormat)) {
            time.setError("Incorrect time format. Please use HH:MM");
        } else if (inputTitle.isEmpty()) {
            title.setError("Cannot have an empty title.");
        }
    }

    private void saveCountdownToDatabase(Countdown countdown) {

        String key = databaseReference.push().getKey();

        databaseReference.child(key).setValue(countdown)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(getApplicationContext(), "Countdown saved", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(CountdownActivity.this, "Failed to save countdown", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}