package com.example.smartcarshowroom;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.smartcarshowroom.database.DatabaseHelper;

import java.util.ArrayList;

public class AdminEnquiriesActivity extends AppCompatActivity {

    ListView listAdminEnquiries;

    Button btnPendingEnquiries;
    Button btnRespondedEnquiries;

    DatabaseHelper databaseHelper;

    ArrayList<String> enquiryList;
    ArrayList<Integer> enquiryIds;

    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(
                R.layout.activity_admin_enquiries
        );

        listAdminEnquiries =
                findViewById(
                        R.id.listAdminEnquiries
                );

        btnPendingEnquiries =
                findViewById(
                        R.id.btnPendingEnquiries
                );

        btnRespondedEnquiries =
                findViewById(
                        R.id.btnRespondedEnquiries
                );

        databaseHelper =
                new DatabaseHelper(this);

        enquiryList =
                new ArrayList<>();

        enquiryIds =
                new ArrayList<>();

        adapter =
                new ArrayAdapter<String>(
                        this,
                        0,
                        enquiryList
                ) {

                    @Override
                    public View getView(
                            int position,
                            View convertView,
                            ViewGroup parent
                    ) {

                        View view;

                        if (convertView == null) {

                            view =
                                    getLayoutInflater().inflate(
                                            R.layout.item_admin_enquiry,
                                            parent,
                                            false
                                    );

                        } else {

                            view = convertView;
                        }

                        TextView tvCustomer =
                                view.findViewById(
                                        R.id.tvEnquiryCustomer
                                );

                        TextView tvCar =
                                view.findViewById(
                                        R.id.tvEnquiryCar
                                );

                        TextView tvType =
                                view.findViewById(
                                        R.id.tvEnquiryType
                                );

                        TextView tvMessage =
                                view.findViewById(
                                        R.id.tvEnquiryMessage
                                );

                        TextView tvStatus =
                                view.findViewById(
                                        R.id.tvEnquiryStatus
                                );

                        /*
                         * Make all TextViews visible.
                         */

                        tvCustomer.setVisibility(
                                View.VISIBLE
                        );

                        tvCar.setVisibility(
                                View.VISIBLE
                        );

                        tvType.setVisibility(
                                View.VISIBLE
                        );

                        tvMessage.setVisibility(
                                View.VISIBLE
                        );

                        tvStatus.setVisibility(
                                View.VISIBLE
                        );

                        /*
                         * Make sure there is no transparency.
                         */

                        tvCustomer.setAlpha(1.0f);
                        tvCar.setAlpha(1.0f);
                        tvType.setAlpha(1.0f);
                        tvMessage.setAlpha(1.0f);
                        tvStatus.setAlpha(1.0f);

                        /*
                         * Premium text colors.
                         */

                        tvCustomer.setTextColor(
                                Color.WHITE
                        );

                        tvCar.setTextColor(
                                Color.rgb(
                                        208,
                                        216,
                                        228
                                )
                        );

                        tvType.setTextColor(
                                Color.rgb(
                                        208,
                                        216,
                                        228
                                )
                        );

                        tvMessage.setTextColor(
                                Color.WHITE
                        );

                        /*
                         * Text sizes.
                         */

                        tvCustomer.setTextSize(18);
                        tvCar.setTextSize(14);
                        tvType.setTextSize(14);
                        tvMessage.setTextSize(14);
                        tvStatus.setTextSize(12);

                        /*
                         * Get enquiry data.
                         */

                        String enquiry =
                                enquiryList.get(position);

                        String[] lines =
                                enquiry.split(
                                        "\n",
                                        -1
                                );

                        /*
                         * Customer
                         */

                        if (lines.length > 0 &&
                                !lines[0].trim().isEmpty()) {

                            tvCustomer.setText(
                                    lines[0]
                            );

                        } else {

                            tvCustomer.setText(
                                    "Customer: Not Available"
                            );
                        }

                        /*
                         * Car
                         */

                        if (lines.length > 1 &&
                                !lines[1].trim().isEmpty()) {

                            tvCar.setText(
                                    lines[1]
                            );

                        } else {

                            tvCar.setText(
                                    "Car: Not Available"
                            );
                        }

                        /*
                         * Type
                         */

                        if (lines.length > 2 &&
                                !lines[2].trim().isEmpty()) {

                            tvType.setText(
                                    lines[2]
                            );

                        } else {

                            tvType.setText(
                                    "Type: Not Available"
                            );
                        }

                        /*
                         * Message
                         */

                        if (lines.length > 3 &&
                                !lines[3].trim().isEmpty()) {

                            tvMessage.setText(
                                    lines[3]
                            );

                        } else {

                            tvMessage.setText(
                                    "Message: No message"
                            );
                        }

                        /*
                         * Status
                         */

                        String statusText;

                        if (lines.length > 4 &&
                                !lines[4].trim().isEmpty()) {

                            statusText =
                                    lines[4];

                        } else {

                            statusText =
                                    "Status: Not Available";
                        }

                        tvStatus.setText(
                                statusText
                        );

                        /*
                         * Dynamic status colors.
                         *
                         * Pending   -> Orange
                         * Responded -> Green
                         */

                        String status =
                                statusText
                                        .replace(
                                                "Status:",
                                                ""
                                        )
                                        .trim();

                        if (status.equalsIgnoreCase("Pending")) {

                            tvStatus.setTextColor(
                                    Color.rgb(
                                            232,
                                            168,
                                            78
                                    )
                            );

                        } else if (status.equalsIgnoreCase("Responded")) {

                            tvStatus.setTextColor(
                                    Color.rgb(
                                            53,
                                            184,
                                            138
                                    )
                            );

                        } else {

                            /*
                             * Default status color.
                             */

                            tvStatus.setTextColor(
                                    Color.WHITE
                            );
                        }

                        return view;
                    }
                };

        listAdminEnquiries.setAdapter(
                adapter
        );

        /*
         * Open enquiry details.
         */

        listAdminEnquiries.setOnItemClickListener(
                (parent, view, position, id) -> {

                    int enquiryId =
                            enquiryIds.get(position);

                    Intent intent =
                            new Intent(
                                    AdminEnquiriesActivity.this,
                                    AdminEnquiryResponseActivity.class
                            );

                    intent.putExtra(
                            "ENQUIRY_ID",
                            enquiryId
                    );

                    startActivity(intent);
                }
        );

        /*
         * Pending enquiries.
         */

        btnPendingEnquiries.setOnClickListener(v -> {

            loadEnquiries(
                    "Pending"
            );
        });

        /*
         * Responded enquiries.
         */

        btnRespondedEnquiries.setOnClickListener(v -> {

            loadEnquiries(
                    "Responded"
            );
        });

        /*
         * Load Pending enquiries by default.
         */

        loadEnquiries(
                "Pending"
        );
    }

    private void loadEnquiries(
            String status
    ) {

        Cursor cursor =
                databaseHelper
                        .getReadableDatabase()
                        .rawQuery(
                                "SELECT Enquiries.*, " +
                                        "Users.name AS customer_name, " +
                                        "Users.email AS customer_email, " +
                                        "Cars.brand, " +
                                        "Cars.name AS car_name " +
                                        "FROM Enquiries " +
                                        "INNER JOIN Users " +
                                        "ON Enquiries.user_id = Users.user_id " +
                                        "INNER JOIN Cars " +
                                        "ON Enquiries.car_id = Cars.car_id " +
                                        "WHERE Enquiries.status=? " +
                                        "ORDER BY Enquiries.enquiry_id DESC",
                                new String[]{
                                        status
                                }
                        );

        enquiryList.clear();
        enquiryIds.clear();

        while (cursor.moveToNext()) {

            int enquiryId =
                    cursor.getInt(
                            cursor.getColumnIndexOrThrow(
                                    "enquiry_id"
                            )
                    );

            String customer =
                    cursor.getString(
                            cursor.getColumnIndexOrThrow(
                                    "customer_name"
                            )
                    );

            String email =
                    cursor.getString(
                            cursor.getColumnIndexOrThrow(
                                    "customer_email"
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

            String type =
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

            /*
             * Customer fallback.
             */

            if (customer == null ||
                    customer.trim().isEmpty()) {

                if (email != null &&
                        !email.trim().isEmpty()) {

                    customer =
                            email;

                } else {

                    customer =
                            "Customer";
                }
            }

            if (brand == null) {

                brand = "";
            }

            if (carName == null) {

                carName = "";
            }

            String fullCarName =
                    (brand + " " + carName).trim();

            if (fullCarName.isEmpty()) {

                fullCarName =
                        "Not Available";
            }

            if (type == null ||
                    type.trim().isEmpty()) {

                type =
                        "Not Available";
            }

            if (message == null ||
                    message.trim().isEmpty()) {

                message =
                        "No message";
            }

            String enquiry =
                    "Customer: " +
                            customer +
                            "\nCar: " +
                            fullCarName +
                            "\nType: " +
                            type +
                            "\nMessage: " +
                            message +
                            "\nStatus: " +
                            status;

            enquiryList.add(
                    enquiry
            );

            enquiryIds.add(
                    enquiryId
            );
        }

        cursor.close();

        adapter.notifyDataSetChanged();
    }
}