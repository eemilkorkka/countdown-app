package com.example.countdown;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
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
import java.util.UUID;

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

        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerFragment().show(getSupportFragmentManager(), "datePicker");
            }
        });

        time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new TimePickerFragment().show(getSupportFragmentManager(), "timePicker");
            }
        });
    }

    private void addCountdown() {
        String inputTitle = title.getText().toString();
        String inputDate = date.getText().toString();
        String inputTime = time.getText().toString();

        if (!inputTitle.isEmpty() && !inputDate.isEmpty() && !inputTime.isEmpty()) {

            if (currentUser != null) {

                String userID = currentUser.getUid();
                String countdownID = UUID.randomUUID().toString();

                Countdown countdown = new Countdown();

                countdown.setTitle(inputTitle);
                countdown.setDate(inputDate);
                countdown.setTime(inputTime);
                countdown.setUserID(userID);
                countdown.setCountdownID(countdownID);

                saveCountdownToDatabase(countdown);
            }
        }
    }

    private void saveCountdownToDatabase(Countdown countdown) {

        String key = databaseReference.push().getKey();

        databaseReference.child(countdown.getCountdownID()).setValue(countdown)
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