package com.example.smartcarshowroom;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.smartcarshowroom.database.DatabaseHelper;

public class MainActivity extends AppCompatActivity {

    EditText etEmail, etPassword;
    Button btnLogin, btnAdminLogin;
    TextView tvRegister;

    DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);

        btnLogin = findViewById(R.id.btnLogin);
        btnAdminLogin = findViewById(R.id.btnAdminLogin);
        tvRegister = findViewById(R.id.tvRegister);

        databaseHelper = new DatabaseHelper(this);

        tvRegister.setOnClickListener(v -> {

            Intent intent = new Intent(
                    MainActivity.this,
                    RegisterActivity.class
            );

            startActivity(intent);
        });

        btnLogin.setOnClickListener(v -> loginCustomer());

        btnAdminLogin.setOnClickListener(v -> {

            Intent intent = new Intent(
                    MainActivity.this,
                    AdminLoginActivity.class
            );

            startActivity(intent);
        });
    }

    private void loginCustomer() {

        String email = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString();

        if (email.isEmpty() || password.isEmpty()) {

            Toast.makeText(this,
                    "Please enter email and password",
                    Toast.LENGTH_SHORT).show();

            return;
        }

        boolean valid = databaseHelper.checkCustomerLogin(
                email,
                password
        );

        if (valid) {

            Toast.makeText(
                    this,
                    "Login successful",
                    Toast.LENGTH_SHORT
            ).show();

            String name = databaseHelper.getUserName(email);

            // Save logged-in user's email
            getSharedPreferences(
                    "SmartCarPrefs",
                    MODE_PRIVATE
            )
                    .edit()
                    .putString("USER_EMAIL", email)
                    .apply();

            Intent intent = new Intent(
                    MainActivity.this,
                    HomeActivity.class
            );

            intent.putExtra("USER_NAME", name);

            startActivity(intent);

            finish();
        } else {

            Toast.makeText(this,
                    "Invalid email or password",
                    Toast.LENGTH_SHORT).show();
        }
    }
}