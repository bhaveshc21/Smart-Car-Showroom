package com.example.smartcarshowroom;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Intent;
import android.widget.ImageView;
import android.net.Uri;
import java.io.File;

import androidx.appcompat.app.AppCompatActivity;

import com.example.smartcarshowroom.database.DatabaseHelper;

public class CarDetailsActivity extends AppCompatActivity {

    ImageView ivCarImage;
    TextView tvCarName;
    TextView tvCarPrice;
    TextView tvCarSpecifications;
    TextView tvCarEngine;
    TextView tvCarSeats;
    TextView tvCarFeatures;
    TextView tvCarAvailability;

    Button btnFavorite;
    Button btnEMI;
    Button btnEnquiry;
    Button btnTestDrive;

    DatabaseHelper databaseHelper;
    String userEmail;
    int carId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_car_details);

        ivCarImage =
                findViewById(
                        R.id.ivCarImage
                );
        tvCarName = findViewById(R.id.tvCarName);
        tvCarPrice = findViewById(R.id.tvCarPrice);
        tvCarSpecifications = findViewById(R.id.tvCarSpecifications);
        tvCarEngine = findViewById(R.id.tvCarEngine);
        tvCarSeats = findViewById(R.id.tvCarSeats);
        tvCarFeatures = findViewById(R.id.tvCarFeatures);
        tvCarAvailability = findViewById(R.id.tvCarAvailability);

        btnFavorite = findViewById(R.id.btnFavorite);
        btnEMI = findViewById(R.id.btnEMI);
        btnEnquiry = findViewById(R.id.btnEnquiry);
        btnTestDrive = findViewById(R.id.btnTestDrive);

        databaseHelper = new DatabaseHelper(this);
        databaseHelper.createFavoritesTable();

        carId = getIntent().getIntExtra(
                "CAR_ID",
                -1
        );

        userEmail = getSharedPreferences(
                "SmartCarPrefs",
                MODE_PRIVATE
        ).getString(
                "USER_EMAIL",
                ""
        );


        if (carId != -1) {

            loadCarDetails(carId);

            updateFavoriteButton();

            btnFavorite.setOnClickListener(v -> {

                if (databaseHelper.isFavorite(
                        userEmail,
                        carId)) {

                    databaseHelper.removeFavorite(
                            userEmail,
                            carId
                    );

                    Toast.makeText(
                            this,
                            "Removed from Favorites",
                            Toast.LENGTH_SHORT
                    ).show();

                } else {

                    databaseHelper.addFavorite(
                            userEmail,
                            carId
                    );

                    Toast.makeText(
                            this,
                            "Added to Favorites",
                            Toast.LENGTH_SHORT
                    ).show();
                }

                updateFavoriteButton();
            });
            btnEMI.setOnClickListener(v -> {

                Intent intent = new Intent(
                        CarDetailsActivity.this,
                        EMIActivity.class
                );

                intent.putExtra(
                        "CAR_ID",
                        carId
                );

                startActivity(intent);
            });
            btnEnquiry.setOnClickListener(v -> {

                Intent intent =
                        new Intent(
                                CarDetailsActivity.this,
                                EnquiryActivity.class
                        );

                intent.putExtra(
                        "CAR_ID",
                        carId
                );

                startActivity(intent);
            });
            btnTestDrive.setOnClickListener(v -> {

                Intent intent =
                        new Intent(
                                CarDetailsActivity.this,
                                TestDriveActivity.class
                        );

                intent.putExtra(
                        "CAR_ID",
                        carId
                );

                startActivity(intent);
            });

        } else {

            Toast.makeText(
                    this,
                    "Car not found",
                    Toast.LENGTH_SHORT
            ).show();

            finish();
        }
    }

    private void loadCarDetails(int carId) {

        Cursor cursor = databaseHelper.getCarById(carId);

        if (cursor.moveToFirst()) {

            String brand = cursor.getString(
                    cursor.getColumnIndexOrThrow("brand")
            );

            String name = cursor.getString(
                    cursor.getColumnIndexOrThrow("name")
            );

            double price = cursor.getDouble(
                    cursor.getColumnIndexOrThrow("price")
            );

            String fuelType = cursor.getString(
                    cursor.getColumnIndexOrThrow("fuel_type")
            );

            String transmission = cursor.getString(
                    cursor.getColumnIndexOrThrow("transmission")
            );

            String mileage = cursor.getString(
                    cursor.getColumnIndexOrThrow("mileage")
            );

            String engine = cursor.getString(
                    cursor.getColumnIndexOrThrow("engine")
            );

            int seats = cursor.getInt(
                    cursor.getColumnIndexOrThrow("seating_capacity")
            );

            String features = cursor.getString(
                    cursor.getColumnIndexOrThrow("features")
            );

            String availability = cursor.getString(
                    cursor.getColumnIndexOrThrow("availability")
            );

            String image = cursor.getString(
                    cursor.getColumnIndexOrThrow("image")
            );

            File imageFile = new File(image);

            if (imageFile.exists()) {

                ivCarImage.setImageURI(
                        Uri.fromFile(imageFile)
                );

            } else {

                int imageResource =
                        getResources().getIdentifier(
                                image,
                                "drawable",
                                getPackageName()
                        );

                if (imageResource != 0) {

                    ivCarImage.setImageResource(
                            imageResource
                    );
                }
            }


            tvCarName.setText(
                    brand + " " + name
            );

            tvCarPrice.setText(
                    "₹" + String.format("%.0f", price)
            );

            tvCarSpecifications.setText(
                    fuelType
                            + " • "
                            + transmission
                            + " • "
                            + mileage
            );

            tvCarEngine.setText(
                    "Engine: " + engine
            );

            tvCarSeats.setText(
                    "Seating Capacity: " + seats
            );

            tvCarFeatures.setText(
                    features
            );

            tvCarAvailability.setText(
                    "Availability: " + availability
            );
        }

        cursor.close();
    }
    private void updateFavoriteButton() {

        if (databaseHelper.isFavorite(
                userEmail,
                carId)) {

            btnFavorite.setText(
                    "❤️ Remove from Favorites"
            );

        } else {

            btnFavorite.setText(
                    "❤️ Add to Favorites"
            );
        }
    }
}