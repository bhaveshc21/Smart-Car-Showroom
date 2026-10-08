package com.example.smartcarshowroom;

import android.content.Intent;
import android.widget.Button;
import android.widget.Toast;

import android.database.Cursor;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smartcarshowroom.adapters.CarAdapter;
import com.example.smartcarshowroom.database.DatabaseHelper;
import com.example.smartcarshowroom.models.Car;

import java.util.ArrayList;

public class CarListActivity extends AppCompatActivity {

    RecyclerView recyclerCars;

    EditText etSearchCars;

    Spinner spinnerBrand;
    Spinner spinnerFuel;
    Spinner spinnerTransmission;

    Button btnCompareSelected;

    DatabaseHelper databaseHelper;

    ArrayList<Car> carList;

    CarAdapter carAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(
                R.layout.activity_car_list
        );


        recyclerCars =
                findViewById(
                        R.id.recyclerCars
                );


        btnCompareSelected =
                findViewById(
                        R.id.btnCompareSelected
                );


        // Compare selected cars

        btnCompareSelected.setOnClickListener(v -> {

            ArrayList<Integer> selectedCars =
                    CarAdapter.getSelectedCars();


            if (selectedCars.size() < 2) {

                Toast.makeText(
                        this,
                        "Select at least 2 cars to compare",
                        Toast.LENGTH_SHORT
                ).show();

                return;
            }


            Intent intent =
                    new Intent(
                            CarListActivity.this,
                            CompareActivity.class
                    );


            intent.putIntegerArrayListExtra(
                    "SELECTED_CARS",
                    selectedCars
            );


            startActivity(intent);
        });


        etSearchCars =
                findViewById(
                        R.id.etSearchCars
                );


        spinnerBrand =
                findViewById(
                        R.id.spinnerBrand
                );


        spinnerFuel =
                findViewById(
                        R.id.spinnerFuel
                );


        spinnerTransmission =
                findViewById(
                        R.id.spinnerTransmission
                );


        databaseHelper =
                new DatabaseHelper(this);


        carList =
                new ArrayList<>();


        recyclerCars.setLayoutManager(
                new LinearLayoutManager(this)
        );


        setupFilters();

        loadCars();

        setupSearch();
    }


    // =========================================================
    // FILTER SETUP
    // =========================================================

    private void setupFilters() {

        ArrayList<String> brands =
                new ArrayList<>();


        ArrayList<String> fuelTypes =
                new ArrayList<>();


        ArrayList<String> transmissions =
                new ArrayList<>();


        // Default options

        brands.add(
                "All Brands"
        );


        fuelTypes.add(
                "All Fuel Types"
        );


        transmissions.add(
                "All Transmissions"
        );


        // -----------------------------------------------------
        // Get Brands
        // -----------------------------------------------------

        Cursor cursor =
                databaseHelper
                        .getReadableDatabase()
                        .rawQuery(
                                "SELECT DISTINCT brand " +
                                        "FROM Cars " +
                                        "WHERE brand IS NOT NULL " +
                                        "AND brand != '' " +
                                        "ORDER BY brand",
                                null
                        );


        while (cursor.moveToNext()) {

            String brand =
                    cursor.getString(0);


            if (!brands.contains(brand)) {

                brands.add(brand);
            }
        }


        cursor.close();


        // -----------------------------------------------------
        // Get Fuel Types
        // -----------------------------------------------------

        cursor =
                databaseHelper
                        .getReadableDatabase()
                        .rawQuery(
                                "SELECT DISTINCT fuel_type " +
                                        "FROM Cars " +
                                        "WHERE fuel_type IS NOT NULL " +
                                        "AND fuel_type != '' " +
                                        "ORDER BY fuel_type",
                                null
                        );


        while (cursor.moveToNext()) {

            String fuel =
                    cursor.getString(0);


            if (!fuelTypes.contains(fuel)) {

                fuelTypes.add(fuel);
            }
        }


        cursor.close();


        // -----------------------------------------------------
        // Get Transmissions
        // -----------------------------------------------------

        cursor =
                databaseHelper
                        .getReadableDatabase()
                        .rawQuery(
                                "SELECT DISTINCT transmission " +
                                        "FROM Cars " +
                                        "WHERE transmission IS NOT NULL " +
                                        "AND transmission != '' " +
                                        "ORDER BY transmission",
                                null
                        );


        while (cursor.moveToNext()) {

            String transmission =
                    cursor.getString(0);


            if (!transmissions.contains(transmission)) {

                transmissions.add(transmission);
            }
        }


        cursor.close();


        // =====================================================
        // BRAND ADAPTER
        // =====================================================

        ArrayAdapter<String> brandAdapter =
                new ArrayAdapter<>(
                        this,
                        R.layout.item_spinner,
                        brands
                );


        brandAdapter.setDropDownViewResource(
                R.layout.item_spinner_dropdown
        );


        spinnerBrand.setAdapter(
                brandAdapter
        );


        // =====================================================
        // FUEL ADAPTER
        // =====================================================

        ArrayAdapter<String> fuelAdapter =
                new ArrayAdapter<>(
                        this,
                        R.layout.item_spinner,
                        fuelTypes
                );


        fuelAdapter.setDropDownViewResource(
                R.layout.item_spinner_dropdown
        );


        spinnerFuel.setAdapter(
                fuelAdapter
        );


        // =====================================================
        // TRANSMISSION ADAPTER
        // =====================================================

        ArrayAdapter<String> transmissionAdapter =
                new ArrayAdapter<>(
                        this,
                        R.layout.item_spinner,
                        transmissions
                );


        transmissionAdapter.setDropDownViewResource(
                R.layout.item_spinner_dropdown
        );


        spinnerTransmission.setAdapter(
                transmissionAdapter
        );


        // =====================================================
        // FILTER LISTENERS
        // =====================================================

        spinnerBrand.setOnItemSelectedListener(
                new SimpleFilterListener() {

                    @Override
                    public void onFilterChanged() {

                        loadCars();
                    }
                }
        );


        spinnerFuel.setOnItemSelectedListener(
                new SimpleFilterListener() {

                    @Override
                    public void onFilterChanged() {

                        loadCars();
                    }
                }
        );


        spinnerTransmission.setOnItemSelectedListener(
                new SimpleFilterListener() {

                    @Override
                    public void onFilterChanged() {

                        loadCars();
                    }
                }
        );
    }


    // =========================================================
    // SEARCH
    // =========================================================

    private void setupSearch() {

        etSearchCars.addTextChangedListener(
                new TextWatcher() {

                    @Override
                    public void beforeTextChanged(
                            CharSequence s,
                            int start,
                            int count,
                            int after) {
                    }


                    @Override
                    public void onTextChanged(
                            CharSequence s,
                            int start,
                            int before,
                            int count) {

                        loadCars();
                    }


                    @Override
                    public void afterTextChanged(
                            Editable s) {
                    }
                }
        );
    }


    // =========================================================
    // LOAD CARS
    // =========================================================

    private void loadCars() {

        carList.clear();


        String search =
                etSearchCars
                        .getText()
                        .toString()
                        .trim();


        String brand =
                spinnerBrand.getSelectedItem() == null
                        ? "All Brands"
                        : spinnerBrand
                          .getSelectedItem()
                          .toString();


        String fuelType =
                spinnerFuel.getSelectedItem() == null
                        ? "All Fuel Types"
                        : spinnerFuel
                          .getSelectedItem()
                          .toString();


        String transmission =
                spinnerTransmission.getSelectedItem() == null
                        ? "All Transmissions"
                        : spinnerTransmission
                          .getSelectedItem()
                          .toString();


        Cursor cursor =
                databaseHelper.searchCars(
                        search,
                        brand,
                        fuelType,
                        transmission
                );


        if (cursor.moveToFirst()) {

            do {

                int carId =
                        cursor.getInt(
                                cursor.getColumnIndexOrThrow(
                                        "car_id"
                                )
                        );


                String carBrand =
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


                String model =
                        cursor.getString(
                                cursor.getColumnIndexOrThrow(
                                        "model"
                                )
                        );


                double price =
                        cursor.getDouble(
                                cursor.getColumnIndexOrThrow(
                                        "price"
                                )
                        );


                String carFuelType =
                        cursor.getString(
                                cursor.getColumnIndexOrThrow(
                                        "fuel_type"
                                )
                        );


                String carTransmission =
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


                String features =
                        cursor.getString(
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


                String image =
                        cursor.getString(
                                cursor.getColumnIndexOrThrow(
                                        "image"
                                )
                        );


                Car car =
                        new Car(
                                carId,
                                carBrand,
                                name,
                                model,
                                price,
                                carFuelType,
                                carTransmission,
                                mileage,
                                engine,
                                seats,
                                features,
                                availability,
                                image
                        );


                carList.add(car);


            } while (cursor.moveToNext());
        }


        cursor.close();


        carAdapter =
                new CarAdapter(carList);


        recyclerCars.setAdapter(
                carAdapter
        );
    }


    // =========================================================
    // FILTER LISTENER
    // =========================================================

    private abstract class SimpleFilterListener
            implements android.widget.AdapterView.OnItemSelectedListener {


        public abstract void onFilterChanged();


        @Override
        public void onNothingSelected(
                android.widget.AdapterView<?> parent) {
        }


        @Override
        public void onItemSelected(
                android.widget.AdapterView<?> parent,
                android.view.View view,
                int position,
                long id) {

            onFilterChanged();
        }
    }


    // =========================================================
    // RESUME
    // =========================================================

    @Override
    protected void onResume() {

        super.onResume();


        CarAdapter.clearSelectedCars();


        if (carAdapter != null) {

            setupFilters();

            loadCars();
        }
    }
}