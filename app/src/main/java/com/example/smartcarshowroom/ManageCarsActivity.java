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

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.smartcarshowroom.database.DatabaseHelper;

import java.util.ArrayList;

public class ManageCarsActivity extends AppCompatActivity {

    ListView listAdminCars;
    Button btnAddCar;

    DatabaseHelper databaseHelper;

    ArrayList<String> carList;
    ArrayList<Integer> carIds;

    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(
                R.layout.activity_manage_cars
        );

        listAdminCars =
                findViewById(
                        R.id.listAdminCars
                );

        btnAddCar =
                findViewById(
                        R.id.btnAddCar
                );

        databaseHelper =
                new DatabaseHelper(this);

        carList =
                new ArrayList<>();

        carIds =
                new ArrayList<>();

        adapter =
                new ArrayAdapter<String>(
                        this,
                        0,
                        carList
                ) {

                    @Override
                    public View getView(
                            int position,
                            View convertView,
                            ViewGroup parent
                    ) {

                        View view;

                        if (convertView == null) {

                            view = getLayoutInflater().inflate(
                                    R.layout.item_admin_car,
                                    parent,
                                    false
                            );

                        } else {

                            view = convertView;
                        }

                        TextView tvCarName =
                                view.findViewById(
                                        R.id.tvAdminCarName
                                );

                        TextView tvCarPrice =
                                view.findViewById(
                                        R.id.tvAdminCarPrice
                                );

                        TextView tvCarAvailability =
                                view.findViewById(
                                        R.id.tvAdminCarAvailability
                                );

                        /*
                         * Force the text to be visible.
                         * This overrides any unwanted
                         * theme/drawable text appearance.
                         */

                        tvCarName.setVisibility(View.VISIBLE);
                        tvCarPrice.setVisibility(View.VISIBLE);
                        tvCarAvailability.setVisibility(View.VISIBLE);

                        tvCarName.setAlpha(1.0f);
                        tvCarPrice.setAlpha(1.0f);
                        tvCarAvailability.setAlpha(1.0f);

                        tvCarName.setTextColor(
                                Color.rgb(52, 49, 63)
                        );

                        tvCarPrice.setTextColor(
                                Color.rgb(85, 81, 93)
                        );

                        tvCarAvailability.setTextColor(
                                Color.rgb(20, 105, 80)
                        );

                        tvCarName.setTextSize(18);
                        tvCarPrice.setTextSize(16);
                        tvCarAvailability.setTextSize(15);

                        String car =
                                carList.get(position);

                        String[] lines =
                                car.split(
                                        "\n",
                                        -1
                                );

                        /*
                         * Car Name
                         */

                        if (lines.length > 0 &&
                                !lines[0].trim().isEmpty()) {

                            tvCarName.setText(
                                    lines[0]
                            );

                        } else {

                            tvCarName.setText(
                                    "Car Name"
                            );
                        }

                        /*
                         * Price
                         */

                        if (lines.length > 1 &&
                                !lines[1].trim().isEmpty()) {

                            tvCarPrice.setText(
                                    lines[1]
                            );

                        } else {

                            tvCarPrice.setText(
                                    "Price: Not Available"
                            );
                        }

                        /*
                         * Availability
                         */

                        if (lines.length > 2 &&
                                !lines[2].trim().isEmpty()) {

                            tvCarAvailability.setText(
                                    lines[2]
                            );

                        } else {

                            tvCarAvailability.setText(
                                    "Availability: Not Available"
                            );
                        }

                        return view;
                    }
                };

        listAdminCars.setAdapter(adapter);

        /*
         * Open car for editing
         */

        listAdminCars.setOnItemClickListener(
                (parent, view, position, id) -> {

                    int selectedCarId =
                            carIds.get(position);

                    Intent intent =
                            new Intent(
                                    ManageCarsActivity.this,
                                    AddEditCarActivity.class
                            );

                    intent.putExtra(
                            "CAR_ID",
                            selectedCarId
                    );

                    startActivity(intent);
                }
        );

        /*
         * Long press to delete
         */

        listAdminCars.setOnItemLongClickListener(
                (parent, view, position, id) -> {

                    int selectedCarId =
                            carIds.get(position);

                    new AlertDialog.Builder(
                            ManageCarsActivity.this
                    )
                            .setTitle(
                                    "Delete Car"
                            )
                            .setMessage(
                                    "Are you sure you want to delete this car?"
                            )
                            .setPositiveButton(
                                    "Delete",
                                    (dialog, which) -> {

                                        databaseHelper
                                                .getWritableDatabase()
                                                .delete(
                                                        "Cars",
                                                        "car_id=?",
                                                        new String[]{
                                                                String.valueOf(
                                                                        selectedCarId
                                                                )
                                                        }
                                                );

                                        loadCars();
                                    }
                            )
                            .setNegativeButton(
                                    "Cancel",
                                    null
                            )
                            .show();

                    return true;
                }
        );

        /*
         * Add new car
         */

        btnAddCar.setOnClickListener(v -> {

            Intent intent =
                    new Intent(
                            ManageCarsActivity.this,
                            AddEditCarActivity.class
                    );

            startActivity(intent);
        });

        loadCars();
    }

    @Override
    protected void onResume() {

        super.onResume();

        if (adapter != null) {

            loadCars();
        }
    }

    private void loadCars() {

        Cursor cursor =
                databaseHelper
                        .getReadableDatabase()
                        .rawQuery(
                                "SELECT * FROM Cars " +
                                        "ORDER BY car_id DESC",
                                null
                        );

        carList.clear();
        carIds.clear();

        while (cursor.moveToNext()) {

            int carId =
                    cursor.getInt(
                            cursor.getColumnIndexOrThrow(
                                    "car_id"
                            )
                    );

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

            double price =
                    cursor.getDouble(
                            cursor.getColumnIndexOrThrow(
                                    "price"
                            )
                    );

            String availability =
                    cursor.getString(
                            cursor.getColumnIndexOrThrow(
                                    "availability"
                            )
                    );

            /*
             * Protect against null values.
             */

            if (brand == null) {

                brand = "";
            }

            if (name == null) {

                name = "";
            }

            if (availability == null ||
                    availability.trim().isEmpty()) {

                availability =
                        "Not Available";
            }

            String fullCarName =
                    (brand + " " + name).trim();

            if (fullCarName.isEmpty()) {

                fullCarName =
                        "Car Name";
            }

            String car =
                    fullCarName +
                            "\nPrice: ₹" +
                            String.format(
                                    "%.0f",
                                    price
                            ) +
                            "\nAvailability: " +
                            availability;

            carList.add(car);

            carIds.add(carId);
        }

        cursor.close();

        adapter.notifyDataSetChanged();
    }
}