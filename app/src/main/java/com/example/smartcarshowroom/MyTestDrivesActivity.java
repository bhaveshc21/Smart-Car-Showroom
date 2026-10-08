package com.example.smartcarshowroom;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.smartcarshowroom.database.DatabaseHelper;

import java.util.ArrayList;

public class MyTestDrivesActivity extends AppCompatActivity {

    ListView listTestDrives;

    DatabaseHelper databaseHelper;

    ArrayList<String> testDriveList;

    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(
                R.layout.activity_my_test_drives
        );

        listTestDrives =
                findViewById(
                        R.id.listTestDrives
                );

        databaseHelper =
                new DatabaseHelper(this);

        testDriveList =
                new ArrayList<>();

        adapter =
                new ArrayAdapter<>(
                        this,
                        R.layout.item_test_drive,
                        R.id.tvTestDrive,
                        testDriveList
                );

        listTestDrives.setAdapter(adapter);

        loadTestDrives();
    }

    private void loadTestDrives() {

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
            return;
        }

        Cursor cursor =
                databaseHelper
                        .getReadableDatabase()
                        .rawQuery(
                                "SELECT TestDrives.*, " +
                                        "Cars.brand, " +
                                        "Cars.name AS car_name " +
                                        "FROM TestDrives " +
                                        "INNER JOIN Cars " +
                                        "ON TestDrives.car_id = Cars.car_id " +
                                        "WHERE TestDrives.user_id=? " +
                                        "ORDER BY TestDrives.test_drive_id DESC",
                                new String[]{
                                        String.valueOf(userId)
                                }
                        );

        testDriveList.clear();

        while (cursor.moveToNext()) {

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

            String status =
                    cursor.getString(
                            cursor.getColumnIndexOrThrow(
                                    "status"
                            )
                    );

            String testDrive =
                    "Car: " + brand + " " + carName +
                            "\nDate: " + date +
                            "\nTime: " + time +
                            "\nStatus: " + status;

            testDriveList.add(
                    testDrive
            );
        }

        cursor.close();

        adapter.notifyDataSetChanged();
    }
}