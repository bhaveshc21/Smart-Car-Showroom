package com.example.smartcarshowroom;

import android.content.ContentValues;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.smartcarshowroom.database.DatabaseHelper;

public class AdminTestDriveResponseActivity
        extends AppCompatActivity {

    TextView tvTestDriveDetails;

    EditText etRescheduleDate;
    EditText etRescheduleTime;

    Button btnConfirmTestDrive;
    Button btnRejectTestDrive;
    Button btnRescheduleTestDrive;

    DatabaseHelper databaseHelper;

    int testDriveId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(
                R.layout.activity_admin_test_drive_response
        );

        tvTestDriveDetails =
                findViewById(
                        R.id.tvTestDriveDetails
                );

        etRescheduleDate =
                findViewById(
                        R.id.etRescheduleDate
                );

        etRescheduleTime =
                findViewById(
                        R.id.etRescheduleTime
                );

        btnConfirmTestDrive =
                findViewById(
                        R.id.btnConfirmTestDrive
                );

        btnRejectTestDrive =
                findViewById(
                        R.id.btnRejectTestDrive
                );

        btnRescheduleTestDrive =
                findViewById(
                        R.id.btnRescheduleTestDrive
                );

        databaseHelper =
                new DatabaseHelper(this);

        testDriveId =
                getIntent().getIntExtra(
                        "TEST_DRIVE_ID",
                        -1
                );

        if (testDriveId == -1) {

            Toast.makeText(
                    this,
                    "Test drive not found",
                    Toast.LENGTH_SHORT
            ).show();

            finish();

            return;
        }

        loadTestDrive();

        btnConfirmTestDrive.setOnClickListener(v -> {

            updateStatus("Confirmed");
        });

        btnRejectTestDrive.setOnClickListener(v -> {

            updateStatus("Rejected");
        });

        btnRescheduleTestDrive.setOnClickListener(v -> {

            String date =
                    etRescheduleDate.getText()
                            .toString()
                            .trim();

            String time =
                    etRescheduleTime.getText()
                            .toString()
                            .trim();

            if (date.isEmpty() ||
                    time.isEmpty()) {

                Toast.makeText(
                        this,
                        "Enter new date and time",
                        Toast.LENGTH_SHORT
                ).show();

                return;
            }

            ContentValues values =
                    new ContentValues();

            values.put(
                    "date",
                    date
            );

            values.put(
                    "time",
                    time
            );

            values.put(
                    "status",
                    "Confirmed"
            );

            long result =
                    databaseHelper
                            .getWritableDatabase()
                            .update(
                                    "TestDrives",
                                    values,
                                    "test_drive_id=?",
                                    new String[]{
                                            String.valueOf(
                                                    testDriveId
                                            )
                                    }
                            );

            if (result != -1) {

                createRescheduleNotification(
                        date,
                        time
                );

                Toast.makeText(
                        this,
                        "Test drive rescheduled",
                        Toast.LENGTH_SHORT
                ).show();

                finish();
            } else {

                Toast.makeText(
                        this,
                        "Failed to reschedule",
                        Toast.LENGTH_SHORT
                ).show();
            }
        });
    }

    private void loadTestDrive() {

        Cursor cursor =
                databaseHelper
                        .getReadableDatabase()
                        .rawQuery(
                                "SELECT TestDrives.*, " +
                                        "Users.name AS customer_name, " +
                                        "Users.email, " +
                                        "Cars.brand, " +
                                        "Cars.name AS car_name " +
                                        "FROM TestDrives " +
                                        "INNER JOIN Users " +
                                        "ON TestDrives.user_id = Users.user_id " +
                                        "INNER JOIN Cars " +
                                        "ON TestDrives.car_id = Cars.car_id " +
                                        "WHERE TestDrives.test_drive_id=?",
                                new String[]{
                                        String.valueOf(
                                                testDriveId
                                        )
                                }
                        );

        if (cursor.moveToFirst()) {

            String customer =
                    cursor.getString(
                            cursor.getColumnIndexOrThrow(
                                    "customer_name"
                            )
                    );

            String email =
                    cursor.getString(
                            cursor.getColumnIndexOrThrow(
                                    "email"
                            )
                    );

            String brand =
                    cursor.getString(
                            cursor.getColumnIndexOrThrow(
                                    "brand"
                            )
                    );

            String carName =
                    cursor.getString(
                            cursor.getColumnIndexOrThrow(
                                    "car_name"
                            )
                    );

            String date =
                    cursor.getString(
                            cursor.getColumnIndexOrThrow(
                                    "date"
                            )
                    );

            String time =
                    cursor.getString(
                            cursor.getColumnIndexOrThrow(
                                    "time"
                            )
                    );

            String message =
                    cursor.getString(
                            cursor.getColumnIndexOrThrow(
                                    "message"
                            )
                    );

            String status =
                    cursor.getString(
                            cursor.getColumnIndexOrThrow(
                                    "status"
                            )
                    );

            String details =
                    "Customer: " + customer +
                            "\nEmail: " + email +
                            "\nCar: " + brand + " " + carName +
                            "\nDate: " + date +
                            "\nTime: " + time +
                            "\nMessage: " + message +
                            "\nStatus: " + status;

            tvTestDriveDetails.setText(
                    details
            );

            etRescheduleDate.setText(date);

            etRescheduleTime.setText(time);
        }

        cursor.close();
    }

    private void updateStatus(String status) {

        ContentValues values =
                new ContentValues();

        values.put(
                "status",
                status
        );

        long result =
                databaseHelper
                        .getWritableDatabase()
                        .update(
                                "TestDrives",
                                values,
                                "test_drive_id=?",
                                new String[]{
                                        String.valueOf(
                                                testDriveId
                                        )
                                }
                        );

        if (result != -1) {

            createNotification(status);

            Toast.makeText(
                    this,
                    "Test drive " +
                            status.toLowerCase(),
                    Toast.LENGTH_SHORT
            ).show();

            finish();

        } else {

            Toast.makeText(
                    this,
                    "Failed to update test drive",
                    Toast.LENGTH_SHORT
            ).show();
        }
    }
    private void createNotification(String status) {

        Cursor cursor =
                databaseHelper
                        .getReadableDatabase()
                        .rawQuery(
                                "SELECT user_id " +
                                        "FROM TestDrives " +
                                        "WHERE test_drive_id=?",
                                new String[]{
                                        String.valueOf(
                                                testDriveId
                                        )
                                }
                        );

        if (cursor.moveToFirst()) {

            int userId =
                    cursor.getInt(
                            cursor.getColumnIndexOrThrow(
                                    "user_id"
                            )
                    );

            String title =
                    "Test Drive " + status;

            String message;

            if (status.equals("Confirmed")) {

                message =
                        "Your test drive has been confirmed.";

            } else {

                message =
                        "Your test drive has been rejected.";
            }

            ContentValues notification =
                    new ContentValues();

            notification.put(
                    "user_id",
                    userId
            );

            notification.put(
                    "title",
                    title
            );

            notification.put(
                    "message",
                    message
            );

            notification.put(
                    "is_read",
                    0
            );

            notification.put(
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
                            notification
                    );
        }

        cursor.close();
    }
    private void createRescheduleNotification(
            String date,
            String time) {

        Cursor cursor =
                databaseHelper
                        .getReadableDatabase()
                        .rawQuery(
                                "SELECT user_id " +
                                        "FROM TestDrives " +
                                        "WHERE test_drive_id=?",
                                new String[]{
                                        String.valueOf(
                                                testDriveId
                                        )
                                }
                        );

        if (cursor.moveToFirst()) {

            int userId =
                    cursor.getInt(
                            cursor.getColumnIndexOrThrow(
                                    "user_id"
                            )
                    );

            ContentValues notification =
                    new ContentValues();

            notification.put(
                    "user_id",
                    userId
            );

            notification.put(
                    "title",
                    "Test Drive Rescheduled"
            );

            notification.put(
                    "message",
                    "Your test drive has been rescheduled to "
                            + date
                            + " at "
                            + time
            );

            notification.put(
                    "is_read",
                    0
            );

            notification.put(
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
                            notification
                    );
        }

        cursor.close();
    }
}