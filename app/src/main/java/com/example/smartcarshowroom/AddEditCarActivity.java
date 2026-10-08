package com.example.smartcarshowroom;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.example.smartcarshowroom.database.DatabaseHelper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

public class AddEditCarActivity extends AppCompatActivity {

    TextView tvCarFormTitle;

    EditText etBrand;
    EditText etName;
    EditText etModel;
    EditText etPrice;
    EditText etFuelType;
    EditText etTransmission;
    EditText etMileage;
    EditText etEngine;
    EditText etSeats;
    EditText etFeatures;
    EditText etAvailability;

    ImageView ivCarImagePreview;

    Button btnChooseImage;
    Button btnSaveCar;

    DatabaseHelper databaseHelper;

    int carId = -1;

    String selectedImagePath = "";

    ActivityResultLauncher<String> imagePicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(
                R.layout.activity_add_edit_car
        );

        tvCarFormTitle =
                findViewById(
                        R.id.tvCarFormTitle
                );

        etBrand =
                findViewById(
                        R.id.etBrand
                );

        etName =
                findViewById(
                        R.id.etName
                );

        etModel =
                findViewById(
                        R.id.etModel
                );

        etPrice =
                findViewById(
                        R.id.etPrice
                );

        etFuelType =
                findViewById(
                        R.id.etFuelType
                );

        etTransmission =
                findViewById(
                        R.id.etTransmission
                );

        etMileage =
                findViewById(
                        R.id.etMileage
                );

        etEngine =
                findViewById(
                        R.id.etEngine
                );

        etSeats =
                findViewById(
                        R.id.etSeats
                );

        etFeatures =
                findViewById(
                        R.id.etFeatures
                );

        etAvailability =
                findViewById(
                        R.id.etAvailability
                );

        ivCarImagePreview =
                findViewById(
                        R.id.ivCarImagePreview
                );

        btnChooseImage =
                findViewById(
                        R.id.btnChooseImage
                );

        btnSaveCar =
                findViewById(
                        R.id.btnSaveCar
                );

        databaseHelper =
                new DatabaseHelper(this);

        /*
         * Open phone gallery / photo picker
         */
        imagePicker =
                registerForActivityResult(
                        new ActivityResultContracts.GetContent(),
                        uri -> {

                            if (uri != null) {

                                selectedImagePath =
                                        copyImageToInternalStorage(
                                                uri
                                        );

                                if (!selectedImagePath.isEmpty()) {

                                    ivCarImagePreview.setImageURI(
                                            Uri.fromFile(
                                                    new File(
                                                            selectedImagePath
                                                    )
                                            )
                                    );

                                    Toast.makeText(
                                            this,
                                            "Image selected",
                                            Toast.LENGTH_SHORT
                                    ).show();
                                }
                            }
                        }
                );

        /*
         * Choose image button
         */
        btnChooseImage.setOnClickListener(v -> {

            imagePicker.launch(
                    "image/*"
            );
        });

        carId =
                getIntent().getIntExtra(
                        "CAR_ID",
                        -1
                );

        if (carId != -1) {

            tvCarFormTitle.setText(
                    "Edit Car"
            );

            loadCar();

        } else {

            tvCarFormTitle.setText(
                    "Add Car"
            );
        }

        btnSaveCar.setOnClickListener(v -> {

            saveCar();
        });
    }

    private void loadCar() {

        Cursor cursor =
                databaseHelper.getCarById(
                        carId
                );

        if (cursor.moveToFirst()) {

            etBrand.setText(
                    cursor.getString(
                            cursor.getColumnIndexOrThrow(
                                    "brand"
                            )
                    )
            );

            etName.setText(
                    cursor.getString(
                            cursor.getColumnIndexOrThrow(
                                    "name"
                            )
                    )
            );

            etModel.setText(
                    cursor.getString(
                            cursor.getColumnIndexOrThrow(
                                    "model"
                            )
                    )
            );

            etPrice.setText(
                    String.valueOf(
                            cursor.getDouble(
                                    cursor.getColumnIndexOrThrow(
                                            "price"
                                    )
                            )
                    )
            );

            etFuelType.setText(
                    cursor.getString(
                            cursor.getColumnIndexOrThrow(
                                    "fuel_type"
                            )
                    )
            );

            etTransmission.setText(
                    cursor.getString(
                            cursor.getColumnIndexOrThrow(
                                    "transmission"
                            )
                    )
            );

            etMileage.setText(
                    cursor.getString(
                            cursor.getColumnIndexOrThrow(
                                    "mileage"
                            )
                    )
            );

            etEngine.setText(
                    cursor.getString(
                            cursor.getColumnIndexOrThrow(
                                    "engine"
                            )
                    )
            );

            etSeats.setText(
                    String.valueOf(
                            cursor.getInt(
                                    cursor.getColumnIndexOrThrow(
                                            "seating_capacity"
                                    )
                            )
                    )
            );

            etFeatures.setText(
                    cursor.getString(
                            cursor.getColumnIndexOrThrow(
                                    "features"
                            )
                    )
            );

            etAvailability.setText(
                    cursor.getString(
                            cursor.getColumnIndexOrThrow(
                                    "availability"
                            )
                    )
            );

            String existingImage =
                    cursor.getString(
                            cursor.getColumnIndexOrThrow(
                                    "image"
                            )
                    );

            if (existingImage != null &&
                    !existingImage.isEmpty()) {

                selectedImagePath =
                        existingImage;

                File imageFile =
                        new File(
                                existingImage
                        );

                if (imageFile.exists()) {

                    ivCarImagePreview.setImageURI(
                            Uri.fromFile(
                                    imageFile
                            )
                    );
                }
            }
        }

        cursor.close();
    }

    private void saveCar() {

        String brand =
                etBrand.getText()
                        .toString()
                        .trim();

        String name =
                etName.getText()
                        .toString()
                        .trim();

        String model =
                etModel.getText()
                        .toString()
                        .trim();

        String priceText =
                etPrice.getText()
                        .toString()
                        .trim();

        String fuelType =
                etFuelType.getText()
                        .toString()
                        .trim();

        String transmission =
                etTransmission.getText()
                        .toString()
                        .trim();

        String mileage =
                etMileage.getText()
                        .toString()
                        .trim();

        String engine =
                etEngine.getText()
                        .toString()
                        .trim();

        String seatsText =
                etSeats.getText()
                        .toString()
                        .trim();

        String features =
                etFeatures.getText()
                        .toString()
                        .trim();

        String availability =
                etAvailability.getText()
                        .toString()
                        .trim();

        if (brand.isEmpty() ||
                name.isEmpty() ||
                model.isEmpty() ||
                priceText.isEmpty() ||
                fuelType.isEmpty() ||
                transmission.isEmpty() ||
                mileage.isEmpty() ||
                engine.isEmpty() ||
                seatsText.isEmpty() ||
                features.isEmpty() ||
                availability.isEmpty()) {

            Toast.makeText(
                    this,
                    "Please fill all required fields",
                    Toast.LENGTH_SHORT
            ).show();

            return;
        }

        if (selectedImagePath.isEmpty()) {

            Toast.makeText(
                    this,
                    "Please choose a car image",
                    Toast.LENGTH_SHORT
            ).show();

            return;
        }

        double price =
                Double.parseDouble(
                        priceText
                );

        int seats =
                Integer.parseInt(
                        seatsText
                );

        ContentValues values =
                new ContentValues();

        values.put(
                "brand",
                brand
        );

        values.put(
                "name",
                name
        );

        values.put(
                "model",
                model
        );

        values.put(
                "price",
                price
        );

        values.put(
                "fuel_type",
                fuelType
        );

        values.put(
                "transmission",
                transmission
        );

        values.put(
                "mileage",
                mileage
        );

        values.put(
                "engine",
                engine
        );

        values.put(
                "seating_capacity",
                seats
        );

        values.put(
                "features",
                features
        );

        values.put(
                "availability",
                availability
        );

        values.put(
                "image",
                selectedImagePath
        );

        long result;

        if (carId == -1) {

            result =
                    databaseHelper
                            .getWritableDatabase()
                            .insert(
                                    "Cars",
                                    null,
                                    values
                            );

        } else {

            result =
                    databaseHelper
                            .getWritableDatabase()
                            .update(
                                    "Cars",
                                    values,
                                    "car_id=?",
                                    new String[]{
                                            String.valueOf(
                                                    carId
                                            )
                                    }
                            );
        }

        if (result != -1) {

            Toast.makeText(
                    this,
                    carId == -1
                            ? "Car added successfully"
                            : "Car updated successfully",
                    Toast.LENGTH_SHORT
            ).show();

            finish();

        } else {

            Toast.makeText(
                    this,
                    "Failed to save car",
                    Toast.LENGTH_SHORT
            ).show();
        }
    }

    private String copyImageToInternalStorage(
            Uri uri
    ) {

        try {

            String fileName =
                    "car_" +
                            System.currentTimeMillis() +
                            ".jpg";

            File directory =
                    new File(
                            getFilesDir(),
                            "car_images"
                    );

            if (!directory.exists()) {

                directory.mkdirs();
            }

            File destination =
                    new File(
                            directory,
                            fileName
                    );

            InputStream inputStream =
                    getContentResolver()
                            .openInputStream(uri);

            FileOutputStream outputStream =
                    new FileOutputStream(
                            destination
                    );

            byte[] buffer =
                    new byte[4096];

            int length;

            while (
                    (length =
                            inputStream.read(buffer))
                            > 0
            ) {

                outputStream.write(
                        buffer,
                        0,
                        length
                );
            }

            inputStream.close();

            outputStream.close();

            return destination.getAbsolutePath();

        } catch (Exception e) {

            e.printStackTrace();

            Toast.makeText(
                    this,
                    "Unable to select image",
                    Toast.LENGTH_SHORT
            ).show();

            return "";
        }
    }
}