package com.example.smartcarshowroom;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smartcarshowroom.adapters.CarAdapter;
import com.example.smartcarshowroom.database.DatabaseHelper;
import com.example.smartcarshowroom.models.Car;

import java.util.ArrayList;

public class FavoritesActivity extends AppCompatActivity {

    RecyclerView recyclerFavorites;

    DatabaseHelper databaseHelper;

    ArrayList<Car> favoriteCars;

    CarAdapter carAdapter;

    String userEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(
                R.layout.activity_favorites
        );

        recyclerFavorites =
                findViewById(
                        R.id.recyclerFavorites
                );

        databaseHelper =
                new DatabaseHelper(this);

        userEmail = getSharedPreferences(
                "SmartCarPrefs",
                MODE_PRIVATE
        ).getString(
                "USER_EMAIL",
                ""
        );

        favoriteCars = new ArrayList<>();

        recyclerFavorites.setLayoutManager(
                new LinearLayoutManager(this)
        );

        loadFavorites();
    }

    private void loadFavorites() {

        favoriteCars.clear();

        Cursor cursor =
                databaseHelper.getFavoriteCars(
                        userEmail
                );

        if (cursor.moveToFirst()) {

            do {

                int carId = cursor.getInt(
                        cursor.getColumnIndexOrThrow(
                                "car_id"
                        )
                );

                String brand = cursor.getString(
                        cursor.getColumnIndexOrThrow(
                                "brand"
                        )
                );

                String name = cursor.getString(
                        cursor.getColumnIndexOrThrow(
                                "name"
                        )
                );

                String model = cursor.getString(
                        cursor.getColumnIndexOrThrow(
                                "model"
                        )
                );

                double price = cursor.getDouble(
                        cursor.getColumnIndexOrThrow(
                                "price"
                        )
                );

                String fuelType = cursor.getString(
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

                String mileage = cursor.getString(
                        cursor.getColumnIndexOrThrow(
                                "mileage"
                        )
                );

                String engine = cursor.getString(
                        cursor.getColumnIndexOrThrow(
                                "engine"
                        )
                );

                int seats = cursor.getInt(
                        cursor.getColumnIndexOrThrow(
                                "seating_capacity"
                        )
                );

                String features = cursor.getString(
                        cursor.getColumnIndexOrThrow(
                                "features"
                        )
                );

                String availability =
                        cursor.getString(
                                cursor.getColumnIndexOrThrow(
                                        "availability"
                                )
                        );

                String image = cursor.getString(
                        cursor.getColumnIndexOrThrow(
                                "image"
                        )
                );

                Car car = new Car(
                        carId,
                        brand,
                        name,
                        model,
                        price,
                        fuelType,
                        transmission,
                        mileage,
                        engine,
                        seats,
                        features,
                        availability,
                        image
                );

                favoriteCars.add(car);

            } while (cursor.moveToNext());
        }

        cursor.close();

        carAdapter =
                new CarAdapter(favoriteCars);

        recyclerFavorites.setAdapter(
                carAdapter
        );


        if (favoriteCars.isEmpty()) {

            Toast.makeText(
                    this,
                    "No favorite cars yet",
                    Toast.LENGTH_SHORT
            ).show();
        }
    }
}