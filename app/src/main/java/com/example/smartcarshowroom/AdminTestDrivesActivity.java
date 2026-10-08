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

public class AdminTestDrivesActivity extends AppCompatActivity {

    ListView listAdminTestDrives;

    Button btnPendingTestDrives;
    Button btnConfirmedTestDrives;
    Button btnRejectedTestDrives;

    DatabaseHelper databaseHelper;

    ArrayList<String> testDriveList;
    ArrayList<Integer> testDriveIds;

    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(
            Bundle savedInstanceState
    ) {

        super.onCreate(
                savedInstanceState
        );

        setContentView(
                R.layout.activity_admin_test_drives
        );

        listAdminTestDrives =
                findViewById(
                        R.id.listAdminTestDrives
                );

        btnPendingTestDrives =
                findViewById(
                        R.id.btnPendingTestDrives
                );

        btnConfirmedTestDrives =
                findViewById(
                        R.id.btnConfirmedTestDrives
                );

        btnRejectedTestDrives =
                findViewById(
                        R.id.btnRejectedTestDrives
                );

        databaseHelper =
                new DatabaseHelper(this);

        testDriveList =
                new ArrayList<>();

        testDriveIds =
                new ArrayList<>();

        adapter =
                new ArrayAdapter<String>(
                        this,
                        0,
                        testDriveList
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
                                            R.layout.item_admin_test_drive,
                                            parent,
                                            false
                                    );

                        } else {

                            view =
                                    convertView;
                        }

                        TextView tvCustomer =
                                view.findViewById(
                                        R.id.tvTestDriveCustomer
                                );

                        TextView tvEmail =
                                view.findViewById(
                                        R.id.tvTestDriveEmail
                                );

                        TextView tvCar =
                                view.findViewById(
                                        R.id.tvTestDriveCar
                                );

                        TextView tvDate =
                                view.findViewById(
                                        R.id.tvTestDriveDate
                                );

                        TextView tvTime =
                                view.findViewById(
                                        R.id.tvTestDriveTime
                                );

                        TextView tvMessage =
                                view.findViewById(
                                        R.id.tvTestDriveMessage
                                );

                        TextView tvStatus =
                                view.findViewById(
                                        R.id.tvTestDriveStatus
                                );

                        /*
                         * Make all TextViews visible.
                         */

                        tvCustomer.setVisibility(
                                View.VISIBLE
                        );

                        tvEmail.setVisibility(
                                View.VISIBLE
                        );

                        tvCar.setVisibility(
                                View.VISIBLE
                        );

                        tvDate.setVisibility(
                                View.VISIBLE
                        );

                        tvTime.setVisibility(
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
                        tvEmail.setAlpha(1.0f);
                        tvCar.setAlpha(1.0f);
                        tvDate.setAlpha(1.0f);
                        tvTime.setAlpha(1.0f);
                        tvMessage.setAlpha(1.0f);
                        tvStatus.setAlpha(1.0f);

                        /*
                         * Premium text colors.
                         */

                        tvCustomer.setTextColor(
                                Color.WHITE
                        );

                        tvEmail.setTextColor(
                                Color.rgb(
                                        208,
                                        216,
                                        228
                                )
                        );

                        tvCar.setTextColor(
                                Color.rgb(
                                        208,
                                        216,
                                        228
                                )
                        );

                        tvDate.setTextColor(
                                Color.rgb(
                                        208,
                                        216,
                                        228
                                )
                        );

                        tvTime.setTextColor(
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

                        tvCustomer.setTextSize(
                                18
                        );

                        tvEmail.setTextSize(
                                14
                        );

                        tvCar.setTextSize(
                                14
                        );

                        tvDate.setTextSize(
                                14
                        );

                        tvTime.setTextSize(
                                14
                        );

                        tvMessage.setTextSize(
                                14
                        );

                        tvStatus.setTextSize(
                                12
                        );

                        /*
                         * Get booking data.
                         */

                        String booking =
                                testDriveList.get(
                                        position
                                );

                        String[] lines =
                                booking.split(
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
                         * Email
                         */

                        if (lines.length > 1 &&
                                !lines[1].trim().isEmpty()) {

                            tvEmail.setText(
                                    lines[1]
                            );

                        } else {

                            tvEmail.setText(
                                    "Email: Not Available"
                            );
                        }

                        /*
                         * Car
                         */

                        if (lines.length > 2 &&
                                !lines[2].trim().isEmpty()) {

                            tvCar.setText(
                                    lines[2]
                            );

                        } else {

                            tvCar.setText(
                                    "Car: Not Available"
                            );
                        }

                        /*
                         * Date
                         */

                        if (lines.length > 3 &&
                                !lines[3].trim().isEmpty()) {

                            tvDate.setText(
                                    lines[3]
                            );

                        } else {

                            tvDate.setText(
                                    "Date: Not Available"
                            );
                        }

                        /*
                         * Time
                         */

                        if (lines.length > 4 &&
                                !lines[4].trim().isEmpty()) {

                            tvTime.setText(
                                    lines[4]
                            );

                        } else {

                            tvTime.setText(
                                    "Time: Not Available"
                            );
                        }

                        /*
                         * Message
                         */

                        if (lines.length > 5 &&
                                !lines[5].trim().isEmpty()) {

                            tvMessage.setText(
                                    lines[5]
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

                        if (lines.length > 6 &&
                                !lines[6].trim().isEmpty()) {

                            statusText =
                                    lines[6];

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
                         * Confirmed -> Green
                         * Rejected  -> Red
                         */

                        String status =
                                statusText
                                        .replace(
                                                "Status:",
                                                ""
                                        )
                                        .trim();

                        if (status.equalsIgnoreCase(
                                "Pending"
                        )) {

                            tvStatus.setTextColor(
                                    Color.rgb(
                                            232,
                                            168,
                                            78
                                    )
                            );

                        } else if (status.equalsIgnoreCase(
                                "Confirmed"
                        )) {

                            tvStatus.setTextColor(
                                    Color.rgb(
                                            53,
                                            184,
                                            138
                                    )
                            );

                        } else if (status.equalsIgnoreCase(
                                "Rejected"
                        )) {

                            tvStatus.setTextColor(
                                    Color.rgb(
                                            229,
                                            107,
                                            111
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

        listAdminTestDrives.setAdapter(
                adapter
        );

        /*
         * Open test drive details.
         */

        listAdminTestDrives.setOnItemClickListener(
                (parent, view, position, id) -> {

                    int testDriveId =
                            testDriveIds.get(
                                    position
                            );

                    Intent intent =
                            new Intent(
                                    AdminTestDrivesActivity.this,
                                    AdminTestDriveResponseActivity.class
                            );

                    intent.putExtra(
                            "TEST_DRIVE_ID",
                            testDriveId
                    );

                    startActivity(
                            intent
                    );
                }
        );

        /*
         * Pending.
         */

        btnPendingTestDrives.setOnClickListener(
                v -> {

                    loadTestDrives(
                            "Pending"
                    );
                }
        );

        /*
         * Confirmed.
         */

        btnConfirmedTestDrives.setOnClickListener(
                v -> {

                    loadTestDrives(
                            "Confirmed"
                    );
                }
        );

        /*
         * Rejected.
         */

        btnRejectedTestDrives.setOnClickListener(
                v -> {

                    loadTestDrives(
                            "Rejected"
                    );
                }
        );

        /*
         * Load Pending test drives by default.
         */

        loadTestDrives(
                "Pending"
        );
    }

    private void loadTestDrives(
            String status
    ) {

        Cursor cursor =
                databaseHelper
                        .getReadableDatabase()
                        .rawQuery(
                                "SELECT TestDrives.*, " +
                                        "Users.name AS customer_name, " +
                                        "Users.email AS customer_email, " +
                                        "Cars.brand, " +
                                        "Cars.name AS car_name " +
                                        "FROM TestDrives " +
                                        "INNER JOIN Users " +
                                        "ON TestDrives.user_id = Users.user_id " +
                                        "INNER JOIN Cars " +
                                        "ON TestDrives.car_id = Cars.car_id " +
                                        "WHERE TestDrives.status=? " +
                                        "ORDER BY TestDrives.test_drive_id DESC",
                                new String[]{
                                        status
                                }
                        );

        testDriveList.clear();

        testDriveIds.clear();

        while (cursor.moveToNext()) {

            int testDriveId =
                    cursor.getInt(
                            cursor.getColumnIndexOrThrow(
                                    "test_drive_id"
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

            if (email == null ||
                    email.trim().isEmpty()) {

                email =
                        "Not Available";
            }

            if (brand == null) {

                brand =
                        "";
            }

            if (carName == null) {

                carName =
                        "";
            }

            String fullCarName =
                    (brand + " " + carName).trim();

            if (fullCarName.isEmpty()) {

                fullCarName =
                        "Not Available";
            }

            if (date == null ||
                    date.trim().isEmpty()) {

                date =
                        "Not Available";
            }

            if (time == null ||
                    time.trim().isEmpty()) {

                time =
                        "Not Available";
            }

            if (message == null ||
                    message.trim().isEmpty()) {

                message =
                        "No message";
            }

            String booking =
                    "Customer: " +
                            customer +
                            "\nEmail: " +
                            email +
                            "\nCar: " +
                            fullCarName +
                            "\nDate: " +
                            date +
                            "\nTime: " +
                            time +
                            "\nMessage: " +
                            message +
                            "\nStatus: " +
                            status;

            testDriveList.add(
                    booking
            );

            testDriveIds.add(
                    testDriveId
            );
        }

        cursor.close();

        adapter.notifyDataSetChanged();
    }
}