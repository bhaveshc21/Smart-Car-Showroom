package com.example.smartcarshowroom;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.smartcarshowroom.database.DatabaseHelper;

public class AdminDashboardActivity extends AppCompatActivity {

    TextView tvTotalCars;
    TextView tvAvailableCars;
    TextView tvTotalEnquiries;
    TextView tvTotalTestDrives;

    Button btnAdminCars;
    Button btnAdminEnquiries;
    Button btnAdminTestDrives;
    Button btnAdminLogout;

    DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(
                R.layout.activity_admin_dashboard
        );

        tvTotalCars =
                findViewById(R.id.tvTotalCars);

        tvAvailableCars =
                findViewById(R.id.tvAvailableCars);

        tvTotalEnquiries =
                findViewById(R.id.tvTotalEnquiries);

        tvTotalTestDrives =
                findViewById(R.id.tvTotalTestDrives);

        btnAdminCars =
                findViewById(R.id.btnAdminCars);

        btnAdminEnquiries =
                findViewById(R.id.btnAdminEnquiries);

        btnAdminTestDrives =
                findViewById(R.id.btnAdminTestDrives);

        btnAdminLogout =
                findViewById(R.id.btnAdminLogout);

        databaseHelper =
                new DatabaseHelper(this);

        loadDashboardStats();

        btnAdminEnquiries.setOnClickListener(v -> {

            Intent intent =
                    new Intent(
                            AdminDashboardActivity.this,
                            AdminEnquiriesActivity.class
                    );

            startActivity(intent);
        });

        btnAdminTestDrives.setOnClickListener(v -> {

            Intent intent =
                    new Intent(
                            AdminDashboardActivity.this,
                            AdminTestDrivesActivity.class
                    );

            startActivity(intent);
        });



        btnAdminLogout.setOnClickListener(v -> {

            Intent intent =
                    new Intent(
                            AdminDashboardActivity.this,
                            AdminLoginActivity.class
                    );

            startActivity(intent);

            finish();
        });

        btnAdminCars.setOnClickListener(v -> {

            Intent intent =
                    new Intent(
                            AdminDashboardActivity.this,
                            ManageCarsActivity.class
                    );

            startActivity(intent);
        });
    }

    private void loadDashboardStats() {

        Cursor cursor;

        cursor =
                databaseHelper
                        .getReadableDatabase()
                        .rawQuery(
                                "SELECT COUNT(*) FROM Cars",
                                null
                        );

        if (cursor.moveToFirst()) {

            tvTotalCars.setText(
                    String.valueOf(cursor.getInt(0))
            );
        }

        cursor.close();

        cursor =
                databaseHelper
                        .getReadableDatabase()
                        .rawQuery(
                                "SELECT COUNT(*) FROM Cars " +
                                        "WHERE availability='Available'",
                                null
                        );

        if (cursor.moveToFirst()) {

            tvAvailableCars.setText(
                    String.valueOf(cursor.getInt(0))
            );
        }

        cursor.close();

        cursor =
                databaseHelper
                        .getReadableDatabase()
                        .rawQuery(
                                "SELECT COUNT(*) FROM Enquiries",
                                null
                        );

        if (cursor.moveToFirst()) {

            tvTotalEnquiries.setText(
                    String.valueOf(cursor.getInt(0))
            );
        }

        cursor.close();

        cursor =
                databaseHelper
                        .getReadableDatabase()
                        .rawQuery(
                                "SELECT COUNT(*) FROM TestDrives",
                                null
                        );

        if (cursor.moveToFirst()) {

            tvTotalTestDrives.setText(
                    String.valueOf(cursor.getInt(0))
            );
        }

        cursor.close();
    }
}