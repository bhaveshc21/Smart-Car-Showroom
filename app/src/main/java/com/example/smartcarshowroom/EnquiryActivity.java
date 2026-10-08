package com.example.smartcarshowroom;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Button;
import android.widget.Toast;
import android.database.Cursor;
import com.example.smartcarshowroom.database.DatabaseHelper;
import android.content.ContentValues;

public class EnquiryActivity extends AppCompatActivity {
    int carId;
    Spinner spinnerEnquiryType;
    EditText etEnquiryMessage;
    TextView tvEnquiryCar;
    Button btnSubmitEnquiry;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enquiry);
        spinnerEnquiryType =
                findViewById(R.id.spinnerEnquiryType);
        String[] enquiryTypes = {
                "Offers",
                "Finance",
                "Features",
                "Delivery",
                "Other"
        };
        ArrayAdapter<String> adapter =
                new ArrayAdapter<>(
                        this,
                        android.R.layout.simple_spinner_item,
                        enquiryTypes
                );
        adapter.setDropDownViewResource(
                android.R.layout.simple_spinner_dropdown_item
        );

        spinnerEnquiryType.setAdapter(adapter);

        etEnquiryMessage =
                findViewById(R.id.etEnquiryMessage);
        tvEnquiryCar =
                findViewById(R.id.tvEnquiryCar);

        btnSubmitEnquiry =
                findViewById(R.id.btnSubmitEnquiry);

        carId = getIntent().getIntExtra(
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
        DatabaseHelper databaseHelper =
                new DatabaseHelper(this);

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

            tvEnquiryCar.setText(
                    "Car: " + brand + " " + name
            );
        }

        cursor.close();
        btnSubmitEnquiry.setOnClickListener(v -> {

            String message =
                    etEnquiryMessage.getText()
                            .toString()
                            .trim();

            String enquiryType =
                    spinnerEnquiryType
                            .getSelectedItem()
                            .toString();

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

                Toast.makeText(
                        this,
                        "User not found",
                        Toast.LENGTH_SHORT
                ).show();

                return;
            }

            if (message.isEmpty()) {

                Toast.makeText(
                        this,
                        "Please enter your enquiry",
                        Toast.LENGTH_SHORT
                ).show();

                return;
            }

            DatabaseHelper db =
                    new DatabaseHelper(this);

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
                    "enquiry_type",
                    enquiryType
            );

            values.put(
                    "message",
                    message
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
                    db.getWritableDatabase().insert(
                            "Enquiries",
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
                        "Enquiry Submitted"
                );

                notificationValues.put(
                        "message",
                        "Your enquiry has been submitted successfully."
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

                db.getWritableDatabase().insert(
                        "Notifications",
                        null,
                        notificationValues
                );


                Toast.makeText(
                        this,
                        "Enquiry submitted successfully",
                        Toast.LENGTH_SHORT
                ).show();

                finish();

            } else {

                Toast.makeText(
                        this,
                        "Failed to submit enquiry",
                        Toast.LENGTH_SHORT
                ).show();
            }
        });
    }
}