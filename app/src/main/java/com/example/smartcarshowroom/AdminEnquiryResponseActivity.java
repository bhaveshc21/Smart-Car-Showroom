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

public class AdminEnquiryResponseActivity extends AppCompatActivity {

    TextView tvCustomerName;
    TextView tvCustomerEmail;
    TextView tvEnquiryCar;
    TextView tvEnquiryType;
    TextView tvEnquiryMessage;
    TextView tvEnquiryStatus;

    EditText etAdminResponse;

    Button btnSendResponse;

    DatabaseHelper databaseHelper;

    int enquiryId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(
                R.layout.activity_admin_enquiry_response
        );

        tvCustomerName =
                findViewById(R.id.tvCustomerName);

        tvCustomerEmail =
                findViewById(R.id.tvCustomerEmail);

        tvEnquiryCar =
                findViewById(R.id.tvEnquiryCar);

        tvEnquiryType =
                findViewById(R.id.tvEnquiryType);

        tvEnquiryMessage =
                findViewById(R.id.tvEnquiryMessage);

        tvEnquiryStatus =
                findViewById(R.id.tvEnquiryStatus);

        etAdminResponse =
                findViewById(R.id.etAdminResponse);

        btnSendResponse =
                findViewById(R.id.btnSendResponse);

        databaseHelper =
                new DatabaseHelper(this);

        enquiryId =
                getIntent().getIntExtra(
                        "ENQUIRY_ID",
                        -1
                );

        if (enquiryId == -1) {

            Toast.makeText(
                    this,
                    "Enquiry not found",
                    Toast.LENGTH_SHORT
            ).show();

            finish();

            return;
        }

        loadEnquiry();
        btnSendResponse.setOnClickListener(v -> {

            String response =
                    etAdminResponse.getText()
                            .toString()
                            .trim();

            if (response.isEmpty()) {

                Toast.makeText(
                        this,
                        "Please enter a response",
                        Toast.LENGTH_SHORT
                ).show();

                return;
            }

            ContentValues values =
                    new ContentValues();

            values.put(
                    "admin_response",
                    response
            );

            values.put(
                    "status",
                    "Responded"
            );

            int result =
                    databaseHelper
                            .getWritableDatabase()
                            .update(
                                    "Enquiries",
                                    values,
                                    "enquiry_id=?",
                                    new String[]{
                                            String.valueOf(
                                                    enquiryId
                                            )
                                    }
                            );

            if (result > 0) {

                // Get the customer user_id for this enquiry
                Cursor notificationCursor =
                        databaseHelper
                                .getReadableDatabase()
                                .rawQuery(
                                        "SELECT user_id " +
                                                "FROM Enquiries " +
                                                "WHERE enquiry_id=?",
                                        new String[]{
                                                String.valueOf(
                                                        enquiryId
                                                )
                                        }
                                );

                if (notificationCursor.moveToFirst()) {

                    int customerUserId =
                            notificationCursor.getInt(
                                    notificationCursor
                                            .getColumnIndexOrThrow(
                                                    "user_id"
                                            )
                            );

                    // Create notification
                    ContentValues notificationValues =
                            new ContentValues();

                    notificationValues.put(
                            "user_id",
                            customerUserId
                    );

                    notificationValues.put(
                            "title",
                            "Enquiry Responded"
                    );

                    notificationValues.put(
                            "message",
                            "The showroom has responded to your enquiry."
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
                }

                notificationCursor.close();


                Toast.makeText(
                        this,
                        "Response sent successfully",
                        Toast.LENGTH_SHORT
                ).show();

                finish();

            } else {

                Toast.makeText(
                        this,
                        "Failed to send response",
                        Toast.LENGTH_SHORT
                ).show();
            }
        });
    }

    private void loadEnquiry() {

        Cursor cursor =
                databaseHelper
                        .getReadableDatabase()
                        .rawQuery(
                                "SELECT Enquiries.*, " +
                                        "Users.name, Users.email, " +
                                        "Cars.brand, " +
                                        "Cars.name AS car_name " +
                                        "FROM Enquiries " +
                                        "INNER JOIN Users " +
                                        "ON Enquiries.user_id = Users.user_id " +
                                        "INNER JOIN Cars " +
                                        "ON Enquiries.car_id = Cars.car_id " +
                                        "WHERE Enquiries.enquiry_id=?",
                                new String[]{
                                        String.valueOf(
                                                enquiryId
                                        )
                                }
                        );

        if (cursor.moveToFirst()) {

            String customerName =
                    cursor.getString(
                            cursor.getColumnIndexOrThrow(
                                    "name"
                            )
                    );

            String customerEmail =
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

            tvCustomerName.setText(
                    "Customer: " + customerName
            );

            tvCustomerEmail.setText(
                    "Email: " + customerEmail
            );

            tvEnquiryCar.setText(
                    "Car: " + brand + " " + carName
            );

            tvEnquiryType.setText(
                    "Type: " + enquiryType
            );

            tvEnquiryMessage.setText(
                    "Message: " + message
            );

            tvEnquiryStatus.setText(
                    "Status: " + status
            );

            if (adminResponse != null &&
                    !adminResponse.isEmpty()) {

                etAdminResponse.setText(
                        adminResponse
                );
            }
        }

        cursor.close();
    }
}