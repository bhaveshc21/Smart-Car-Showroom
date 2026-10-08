package com.example.smartcarshowroom;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ShowroomActivity extends AppCompatActivity {

    Button btnViewLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(
                R.layout.activity_showroom
        );

        btnViewLocation =
                findViewById(
                        R.id.btnViewLocation
                );

        btnViewLocation.setOnClickListener(v -> {

            Uri location =
                    Uri.parse(
                            "https://www.google.com/maps/search/?api=1&query=18.5204,73.8567"
                    );

            Intent intent =
                    new Intent(
                            Intent.ACTION_VIEW,
                            location
                    );

            startActivity(intent);
        });
    }
}