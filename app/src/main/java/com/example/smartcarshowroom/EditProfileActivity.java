package com.example.smartcarshowroom;

import android.content.ContentValues;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.smartcarshowroom.database.DatabaseHelper;

public class EditProfileActivity extends AppCompatActivity {

    EditText etEditName;
    EditText etEditEmail;
    EditText etEditPhone;

    Button btnSaveProfile;

    DatabaseHelper databaseHelper;

    int userId = -1;

    String oldEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(
                R.layout.activity_edit_profile
        );

        etEditName =
                findViewById(
                        R.id.etEditName
                );

        etEditEmail =
                findViewById(
                        R.id.etEditEmail
                );

        etEditPhone =
                findViewById(
                        R.id.etEditPhone
                );

        btnSaveProfile =
                findViewById(
                        R.id.btnSaveProfile
                );

        databaseHelper =
                new DatabaseHelper(this);

        SharedPreferences preferences =
                getSharedPreferences(
                        "SmartCarPrefs",
                        MODE_PRIVATE
                );

        oldEmail =
                preferences.getString(
                        "USER_EMAIL",
                        ""
                );

        userId =
                databaseHelper.getUserId(
                        oldEmail
                );

        loadProfile();

        btnSaveProfile.setOnClickListener(v -> {

            saveProfile();
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

            etEditName.setText(
                    cursor.getString(
                            cursor.getColumnIndexOrThrow(
                                    "name"
                            )
                    )
            );

            etEditEmail.setText(
                    cursor.getString(
                            cursor.getColumnIndexOrThrow(
                                    "email"
                            )
                    )
            );

            etEditPhone.setText(
                    cursor.getString(
                            cursor.getColumnIndexOrThrow(
                                    "phone"
                            )
                    )
            );
        }

        cursor.close();
    }

    private void saveProfile() {

        String name =
                etEditName.getText()
                        .toString()
                        .trim();

        String email =
                etEditEmail.getText()
                        .toString()
                        .trim();

        String phone =
                etEditPhone.getText()
                        .toString()
                        .trim();

        if (name.isEmpty() ||
                email.isEmpty() ||
                phone.isEmpty()) {

            Toast.makeText(
                    this,
                    "Please fill all fields",
                    Toast.LENGTH_SHORT
            ).show();

            return;
        }

        ContentValues values =
                new ContentValues();

        values.put(
                "name",
                name
        );

        values.put(
                "email",
                email
        );

        values.put(
                "phone",
                phone
        );

        long result =
                databaseHelper
                        .getWritableDatabase()
                        .update(
                                "Users",
                                values,
                                "user_id=?",
                                new String[]{
                                        String.valueOf(
                                                userId
                                        )
                                }
                        );

        if (result != -1) {

            getSharedPreferences(
                    "SmartCarPrefs",
                    MODE_PRIVATE
            )
                    .edit()
                    .putString(
                            "USER_EMAIL",
                            email
                    )
                    .apply();

            Toast.makeText(
                    this,
                    "Profile updated successfully",
                    Toast.LENGTH_SHORT
            ).show();

            finish();

        } else {

            Toast.makeText(
                    this,
                    "Failed to update profile",
                    Toast.LENGTH_SHORT
            ).show();
        }
    }
}