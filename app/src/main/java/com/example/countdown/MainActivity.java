package com.example.countdown;

import androidx.appcompat.app.AppCompatActivity;

import android.app.admin.NetworkEvent;
import android.content.Intent;
import android.net.Network;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private FloatingActionButton newCountdownButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        newCountdownButton = findViewById(R.id.newCountdownBtn);

        newCountdownButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, CountdownActivity.class);
                startActivity(intent);
            }
        });
    }
}