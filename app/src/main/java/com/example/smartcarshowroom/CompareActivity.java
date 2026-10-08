package com.example.smartcarshowroom;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.smartcarshowroom.database.DatabaseHelper;

import java.util.ArrayList;
import com.example.smartcarshowroom.adapters.CarAdapter;

public class CompareActivity extends AppCompatActivity {

    DatabaseHelper databaseHelper;

    ArrayList<Integer> selectedCars;

    TextView tvCar1Name;
    TextView tvCar2Name;
    TextView tvCar3Name;

    TextView tvPrice1;
    TextView tvPrice2;
    TextView tvPrice3;

    TextView tvFuel1;
    TextView tvFuel2;
    TextView tvFuel3;

    TextView tvTransmission1;
    TextView tvTransmission2;
    TextView tvTransmission3;

    TextView tvMileage1;
    TextView tvMileage2;
    TextView tvMileage3;

    TextView tvEngine1;
    TextView tvEngine2;
    TextView tvEngine3;

    TextView tvSeats1;
    TextView tvSeats2;
    TextView tvSeats3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(
                R.layout.activity_compare
        );

        databaseHelper =
                new DatabaseHelper(this);

        selectedCars =
                getIntent().getIntegerArrayListExtra(
                        "SELECTED_CARS"
                );

        if (selectedCars == null ||
                selectedCars.size() < 2) {

            Toast.makeText(
                    this,
                    "Please select at least 2 cars",
                    Toast.LENGTH_SHORT
            ).show();

            finish();

            return;
        }

        initializeViews();

        loadComparison();
    }

    private void initializeViews() {

        tvCar1Name = findViewById(R.id.tvCar1Name);
        tvCar2Name = findViewById(R.id.tvCar2Name);
        tvCar3Name = findViewById(R.id.tvCar3Name);

        tvPrice1 = findViewById(R.id.tvPrice1);
        tvPrice2 = findViewById(R.id.tvPrice2);
        tvPrice3 = findViewById(R.id.tvPrice3);

        tvFuel1 = findViewById(R.id.tvFuel1);
        tvFuel2 = findViewById(R.id.tvFuel2);
        tvFuel3 = findViewById(R.id.tvFuel3);

        tvTransmission1 =
                findViewById(R.id.tvTransmission1);

        tvTransmission2 =
                findViewById(R.id.tvTransmission2);

        tvTransmission3 =
                findViewById(R.id.tvTransmission3);

        tvMileage1 =
                findViewById(R.id.tvMileage1);

        tvMileage2 =
                findViewById(R.id.tvMileage2);

        tvMileage3 =
                findViewById(R.id.tvMileage3);

        tvEngine1 =
                findViewById(R.id.tvEngine1);

        tvEngine2 =
                findViewById(R.id.tvEngine2);

        tvEngine3 =
                findViewById(R.id.tvEngine3);

        tvSeats1 =
                findViewById(R.id.tvSeats1);

        tvSeats2 =
                findViewById(R.id.tvSeats2);

        tvSeats3 =
                findViewById(R.id.tvSeats3);
    }

    private void loadComparison() {

        for (int i = 0;
             i < selectedCars.size();
             i++) {

            int carId =
                    selectedCars.get(i);

            loadCar(
                    carId,
                    i
            );
        }

        if (selectedCars.size() == 2) {

            hideThirdCar();
        }
    }
    private void loadCar(
            int carId,
            int position) {

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

            double price =
                    cursor.getDouble(
                            cursor.getColumnIndexOrThrow(
                                    "price"
                            )
                    );

            String fuel =
                    cursor.getString(
                            cursor.getColumnIndexOrThrow(
                                    "fuel_type"
                            )
                    );

            String transmission =
                    cursor.getString(
                            cursor.getColumnIndexOrThrow(
                                    "transmission"
                            )
                    );

            String mileage =
                    cursor.getString(
                            cursor.getColumnIndexOrThrow(
                                    "mileage"
                            )
                    );

            String engine =
                    cursor.getString(
                            cursor.getColumnIndexOrThrow(
                                    "engine"
                            )
                    );

            int seats =
                    cursor.getInt(
                            cursor.getColumnIndexOrThrow(
                                    "seating_capacity"
                            )
                    );


            TextView nameView;
            TextView priceView;
            TextView fuelView;
            TextView transmissionView;
            TextView mileageView;
            TextView engineView;
            TextView seatsView;


            if (position == 0) {

                nameView = tvCar1Name;
                priceView = tvPrice1;
                fuelView = tvFuel1;
                transmissionView = tvTransmission1;
                mileageView = tvMileage1;
                engineView = tvEngine1;
                seatsView = tvSeats1;

            } else if (position == 1) {

                nameView = tvCar2Name;
                priceView = tvPrice2;
                fuelView = tvFuel2;
                transmissionView = tvTransmission2;
                mileageView = tvMileage2;
                engineView = tvEngine2;
                seatsView = tvSeats2;

            } else {

                nameView = tvCar3Name;
                priceView = tvPrice3;
                fuelView = tvFuel3;
                transmissionView = tvTransmission3;
                mileageView = tvMileage3;
                engineView = tvEngine3;
                seatsView = tvSeats3;
            }


            nameView.setText(
                    brand + " " + name
            );

            priceView.setText(
                    "₹" + String.format(
                            "%.0f",
                            price
                    )
            );

            fuelView.setText(fuel);

            transmissionView.setText(
                    transmission
            );

            mileageView.setText(
                    mileage
            );

            engineView.setText(
                    engine
            );

            seatsView.setText(
                    String.valueOf(seats)
            );
        }

        cursor.close();
    }

    private void hideThirdCar() {

        tvCar3Name.setVisibility(
                TextView.GONE
        );

        tvPrice3.setVisibility(
                TextView.GONE
        );

        tvFuel3.setVisibility(
                TextView.GONE
        );

        tvTransmission3.setVisibility(
                TextView.GONE
        );

        tvMileage3.setVisibility(
                TextView.GONE
        );

        tvEngine3.setVisibility(
                TextView.GONE
        );

        tvSeats3.setVisibility(
                TextView.GONE
        );
    }
}