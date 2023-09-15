package com.example.countdown;

import androidx.appcompat.app.AppCompatActivity;

import android.net.Network;
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

    private final String fileName = "data.json";
    public final String phpFile = "server.php";
    public final String url = "http://192.168.100.157/Countdown/" + phpFile;
    private File jsonFile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addcountdown);

        title = findViewById(R.id.editTextTitle);
        date = findViewById(R.id.editTextDate);
        time = findViewById(R.id.editTextTime);
        addCountdownButton = findViewById(R.id.addCountdownBtn);

        jsonFile = new File(getFilesDir(), fileName);

        addCountdownButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Countdown countdown = new Countdown();

                countdown.setTitle(title.getText().toString());
                countdown.setDate(date.getText().toString());
                countdown.setTime(time.getText().toString());

                saveCountdownToFile(countdown);
                sendCountdownDataToServer();

            }
        });
    }
    private void saveCountdownToFile(Countdown countdown) {
        Gson gson = new Gson();
        String json = gson.toJson(countdown);

        File file = new File(getFilesDir(), fileName);

        try {
            if (!file.exists()) {
                if (!file.createNewFile()) {
                    // Handle the case where file creation fails
                    Log.e("FileCreation", "Failed to create the file");
                    return;
                }
            }

            BufferedWriter writer = new BufferedWriter(new FileWriter(file));
            writer.write(json);
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    private void sendCountdownDataToServer() {
        new Thread(new Runnable() {
            NetworkManager networkManager = new NetworkManager();
            @Override
            public void run() {

                try {
                    networkManager.sendData(url, jsonFile);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}