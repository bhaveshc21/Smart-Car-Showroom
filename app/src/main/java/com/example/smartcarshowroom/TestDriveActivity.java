package com.example.smartcarshowroom;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.content.ContentValues;

import androidx.appcompat.app.AppCompatActivity;

import com.example.smartcarshowroom.database.DatabaseHelper;

public class TestDriveActivity extends AppCompatActivity {

    TextView tvTestDriveCar;

    EditText etTestDriveDate;
    EditText etTestDriveTime;

    Button btnBookTestDrive;

    DatabaseHelper databaseHelper;

    int carId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(
                R.layout.activity_test_drive
        );

        tvTestDriveCar =
                findViewById(R.id.tvTestDriveCar);

        etTestDriveDate =
                findViewById(R.id.etTestDriveDate);

        etTestDriveTime =
                findViewById(R.id.etTestDriveTime);

        btnBookTestDrive =
                findViewById(R.id.btnBookTestDrive);

        databaseHelper =
                new DatabaseHelper(this);

        carId =
                getIntent().getIntExtra(
                        "CAR_ID",
                        -1
                );

        if (carId == -1) {

            Toast.makeText(
                    this,
                    "Car not found",
                    Toast.LENGTH_SHORT
            ).show();

            finish();

            return;
        }

        loadCarDetails();

        btnBookTestDrive.setOnClickListener(v -> {

            String date =
                    etTestDriveDate.getText()
                            .toString()
                            .trim();

            String time =
                    etTestDriveTime.getText()
                            .toString()
                            .trim();

            if (date.isEmpty() ||
                    time.isEmpty()) {

                Toast.makeText(
                        this,
                        "Please enter date and time",
                        Toast.LENGTH_SHORT
                ).show();

                return;
            }

            String userEmail =
                    getSharedPreferences(
                            "SmartCarPrefs",
                            MODE_PRIVATE
                    ).getString(
                            "USER_EMAIL",
                            ""
                    );

            int userId =
                    databaseHelper.getUserId(
                            userEmail
                    );

            if (userId == -1) {

                Toast.makeText(
                        this,
                        "User not found",
                        Toast.LENGTH_SHORT
                ).show();

                return;
            }

            ContentValues values =
                    new ContentValues();

            values.put(
                    "user_id",
                    userId
            );

            values.put(
                    "car_id",
                    carId
            );

            values.put(
                    "date",
                    date
            );

            values.put(
                    "time",
                    time
            );

            values.put(
                    "message",
                    ""
            );

            values.put(
                    "status",
                    "Pending"
            );

            values.put(
                    "created_at",
                    String.valueOf(
                            System.currentTimeMillis()
                    )
            );

            long result =
                    databaseHelper
                            .getWritableDatabase()
                            .insert(
                                    "TestDrives",
                                    null,
                                    values
                            );

            if (result != -1) {

                // Create notification for the customer
                ContentValues notificationValues =
                        new ContentValues();

                notificationValues.put(
                        "user_id",
                        userId
                );

                notificationValues.put(
                        "title",
                        "Test Drive Booked"
                );

                notificationValues.put(
                        "message",
                        "Your test drive request has been submitted successfully."
                );

                notificationValues.put(
                        "is_read",
                        0
                );

                notificationValues.put(
                        "created_at",
                        String.valueOf(
                                System.currentTimeMillis()
                        )
                );

                databaseHelper
                        .getWritableDatabase()
                        .insert(
                                "Notifications",
                                null,
                                notificationValues
                        );


                Toast.makeText(
                        this,
                        "Test drive booked successfully",
                        Toast.LENGTH_SHORT
                ).show();

                finish();

            } else {

                Toast.makeText(
                        this,
                        "Failed to book test drive",
                        Toast.LENGTH_SHORT
                ).show();
            }
        });
    }

    private void loadCarDetails() {

        Cursor cursor =
                databaseHelper.getCarById(carId);

        if (cursor.moveToFirst()) {

            String brand =
                    cursor.getString(
                            cursor.getColumnIndexOrThrow(
                                    "brand"
                            )
                    );

            String name =
                    cursor.getString(
                            cursor.getColumnIndexOrThrow(
                                    "name"
                            )
                    );

            tvTestDriveCar.setText(
                    "Car: " + brand + " " + name
            );
        }

        cursor.close();
    }

}