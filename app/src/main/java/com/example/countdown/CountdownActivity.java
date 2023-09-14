package com.example.countdown;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.gson.Gson;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class CountdownActivity extends AppCompatActivity {

    private EditText title;
    private EditText date;
    private EditText time;
    private Button addCountdownButton;

    private String fileName = "data.json";
    private String url = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addcountdown);

        title = findViewById(R.id.editTextTitle);
        date = findViewById(R.id.editTextDate);
        time = findViewById(R.id.editTextTime);
        addCountdownButton = findViewById(R.id.addCountdownBtn);

        addCountdownButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Countdown countdown = new Countdown();

                countdown.setTitle(title.getText().toString());
                countdown.setDate(date.getText().toString());
                countdown.setTime(time.getText().toString());

                Gson gson = new Gson();

                // Converting our countdown object to JSON format so that we can send this data to our PHP script
                String json = gson.toJson(countdown);

                try {
                    BufferedWriter writer = new BufferedWriter(new FileWriter(getFilesDir() + fileName));
                    writer.write(json);

                    writer.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

                NetworkManager networkManager = new NetworkManager();

                //networkManager.sendData();
            }
        });
    }
}