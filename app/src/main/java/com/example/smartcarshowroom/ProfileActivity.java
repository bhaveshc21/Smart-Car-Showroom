package com.example.smartcarshowroom;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.smartcarshowroom.database.DatabaseHelper;

public class ProfileActivity extends AppCompatActivity {

    TextView tvProfileName;
    TextView tvProfileEmail;
    TextView tvProfilePhone;

    Button btnEditProfile;
    Button btnProfileEnquiries;
    Button btnProfileTestDrives;
    Button btnProfileNotifications;
    Button btnLogout;
    Button btnDeleteAccount;

    DatabaseHelper databaseHelper;

    int userId = -1;

    String userEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(
                R.layout.activity_profile
        );

        tvProfileName =
                findViewById(
                        R.id.tvProfileName
                );

        tvProfileEmail =
                findViewById(
                        R.id.tvProfileEmail
                );

        tvProfilePhone =
                findViewById(
                        R.id.tvProfilePhone
                );

        btnEditProfile =
                findViewById(
                        R.id.btnEditProfile
                );

        btnProfileEnquiries =
                findViewById(
                        R.id.btnProfileEnquiries
                );

        btnProfileTestDrives =
                findViewById(
                        R.id.btnProfileTestDrives
                );

        btnProfileNotifications =
                findViewById(
                        R.id.btnProfileNotifications
                );

        btnLogout =
                findViewById(
                        R.id.btnLogout
                );

        btnDeleteAccount =
                findViewById(
                        R.id.btnDeleteAccount
                );

        databaseHelper =
                new DatabaseHelper(this);

        SharedPreferences preferences =
                getSharedPreferences(
                        "SmartCarPrefs",
                        MODE_PRIVATE
                );

        userEmail =
                preferences.getString(
                        "USER_EMAIL",
                        ""
                );

        userId =
                databaseHelper.getUserId(
                        userEmail
                );

        loadProfile();

        btnEditProfile.setOnClickListener(v -> {

            Intent intent =
                    new Intent(
                            ProfileActivity.this,
                            EditProfileActivity.class
                    );

            startActivity(intent);
        });

        btnProfileEnquiries.setOnClickListener(v -> {

            Intent intent =
                    new Intent(
                            ProfileActivity.this,
                            MyEnquiriesActivity.class
                    );

            startActivity(intent);
        });

        btnProfileTestDrives.setOnClickListener(v -> {

            Intent intent =
                    new Intent(
                            ProfileActivity.this,
                            MyTestDrivesActivity.class
                    );

            startActivity(intent);
        });

        btnProfileNotifications.setOnClickListener(v -> {

            Intent intent =
                    new Intent(
                            ProfileActivity.this,
                            NotificationsActivity.class
                    );

            startActivity(intent);
        });

        btnLogout.setOnClickListener(v -> {

            preferences
                    .edit()
                    .clear()
                    .apply();

            Intent intent =
                    new Intent(
                            ProfileActivity.this,
                            MainActivity.class
                    );

            intent.setFlags(
                    Intent.FLAG_ACTIVITY_NEW_TASK |
                            Intent.FLAG_ACTIVITY_CLEAR_TASK
            );

            startActivity(intent);

            finish();
        });

        btnDeleteAccount.setOnClickListener(v -> {

            new androidx.appcompat.app.AlertDialog.Builder(
                    ProfileActivity.this
            )
                    .setTitle("Delete Account")
                    .setMessage(
                            "Are you sure you want to permanently delete your account?\n\n" +
                                    "Your enquiries, test drives, notifications and favorites will also be deleted."
                    )
                    .setNegativeButton(
                            "CANCEL",
                            null
                    )
                    .setPositiveButton(
                            "DELETE",
                            (dialog, which) -> {

                                boolean deleted =
                                        databaseHelper.deleteUserAccount(
                                                userEmail
                                        );

                                if (deleted) {

                                    preferences
                                            .edit()
                                            .clear()
                                            .apply();

                                    Toast.makeText(
                                            ProfileActivity.this,
                                            "Account deleted successfully",
                                            Toast.LENGTH_SHORT
                                    ).show();

                                    Intent intent =
                                            new Intent(
                                                    ProfileActivity.this,
                                                    MainActivity.class
                                            );

                                    intent.setFlags(
                                            Intent.FLAG_ACTIVITY_NEW_TASK |
                                                    Intent.FLAG_ACTIVITY_CLEAR_TASK
                                    );

                                    startActivity(intent);

                                    finish();

                                } else {

                                    Toast.makeText(
                                            ProfileActivity.this,
                                            "Unable to delete account",
                                            Toast.LENGTH_SHORT
                                    ).show();
                                }
                            }
                    )
                    .show();
        });
    }

    private void loadProfile() {

        Cursor cursor =
                databaseHelper
                        .getReadableDatabase()
                        .rawQuery(
                                "SELECT name, email, phone " +
                                        "FROM Users " +
                                        "WHERE user_id=?",
                                new String[]{
                                        String.valueOf(
                                                userId
                                        )
                                }
                        );

        if (cursor.moveToFirst()) {

            String name =
                    cursor.getString(
                            cursor.getColumnIndexOrThrow(
                                    "name"
                            )
                    );

            String email =
                    cursor.getString(
                            cursor.getColumnIndexOrThrow(
                                    "email"
                            )
                    );

            String phone =
                    cursor.getString(
                            cursor.getColumnIndexOrThrow(
                                    "phone"
                            )
                    );

            tvProfileName.setText(
                    "Name: " + name
            );

            tvProfileEmail.setText(
                    "Email: " + email
            );

            tvProfilePhone.setText(
                    "Phone: " + phone
            );
        }

        cursor.close();
    }

    @Override
    protected void onResume() {

        super.onResume();

        if (databaseHelper != null) {

            loadProfile();
        }
    }
}