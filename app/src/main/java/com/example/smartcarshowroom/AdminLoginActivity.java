package com.example.smartcarshowroom;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;

public class AdminLoginActivity extends AppCompatActivity {

    EditText etAdminEmail, etAdminPassword;
    Button btnAdminLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login);

        etAdminEmail = findViewById(R.id.etAdminEmail);
        etAdminPassword = findViewById(R.id.etAdminPassword);

        btnAdminLogin = findViewById(R.id.btnAdminLogin);

        btnAdminLogin.setOnClickListener(v -> {

            String email = etAdminEmail.getText().toString().trim();
            String password = etAdminPassword.getText().toString();

            if (email.equals("admin@smartmotors.com")
                    && password.equals("admin123")) {

                Toast.makeText(
                        this,
                        "Admin Login Successful",
                        Toast.LENGTH_SHORT
                ).show();

                Intent intent =
                        new Intent(
                                AdminLoginActivity.this,
                                AdminDashboardActivity.class
                        );

                startActivity(intent);

                finish();

            } else {

                Toast.makeText(
                        this,
                        "Invalid Admin Credentials",
                        Toast.LENGTH_SHORT
                ).show();
            }
        });
    }
}