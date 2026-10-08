package com.example.smartcarshowroom;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;

import com.example.smartcarshowroom.database.DatabaseHelper;

import java.util.ArrayList;

public class MyEnquiriesActivity extends AppCompatActivity {

    ListView listEnquiries;

    DatabaseHelper databaseHelper;

    ArrayList<String> enquiryList;

    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(
                R.layout.activity_my_enquiries
        );

        listEnquiries =
                findViewById(
                        R.id.listEnquiries
                );

        databaseHelper =
                new DatabaseHelper(this);

        enquiryList =
                new ArrayList<>();

        adapter =
                new ArrayAdapter<>(
                        this,
                        R.layout.item_enquiry,
                        R.id.tvEnquiry,
                        enquiryList
                );

        listEnquiries.setAdapter(adapter);

        loadEnquiries();
    }
    private void loadEnquiries() {

        String userEmail =
                getSharedPreferences(
                        "SmartCarPrefs",
                        MODE_PRIVATE
                ).getString(
                        "USER_EMAIL",
                        ""
                );

        int userId =
                databaseHelper.getUserId(userEmail);

        if (userId == -1) {
            return;
        }

        Cursor cursor =
                databaseHelper.getReadableDatabase().rawQuery(
                        "SELECT Enquiries.*, " +
                                "Cars.brand, Cars.name " +
                                "FROM Enquiries " +
                                "INNER JOIN Cars " +
                                "ON Enquiries.car_id = Cars.car_id " +
                                "WHERE Enquiries.user_id=? " +
                                "ORDER BY Enquiries.enquiry_id DESC",
                        new String[]{
                                String.valueOf(userId)
                        }
                );

        enquiryList.clear();

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
                                    "name"
                            )
                    );

            String enquiryType =
                    cursor.getString(
                            cursor.getColumnIndexOrThrow(
                                    "enquiry_type"
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

            String adminResponse =
                    cursor.getString(
                            cursor.getColumnIndexOrThrow(
                                    "admin_response"
                            )
                    );

            String enquiry =
                    "Car: " + brand + " " + carName +
                            "\nType: " + enquiryType +
                            "\nMessage: " + message +
                            "\nStatus: " + status +
                            "\nAdmin Response: " +
                            (adminResponse == null ||
                                    adminResponse.isEmpty()
                                    ? "No response yet"
                                    : adminResponse);

            enquiryList.add(enquiry);
        }

        cursor.close();

        adapter.notifyDataSetChanged();
    }
}