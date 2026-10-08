package com.example.smartcarshowroom;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.smartcarshowroom.database.DatabaseHelper;

public class EMIActivity extends AppCompatActivity {

    TextView tvSelectedCar;
    TextView tvCarPrice;

    EditText etDownPayment;
    EditText etInterestRate;
    EditText etLoanTenure;

    Button btnCalculateEMI;

    TextView tvLoanAmount;
    TextView tvMonthlyEMI;
    TextView tvTotalInterest;
    TextView tvTotalAmount;

    DatabaseHelper databaseHelper;

    int carId;
    double carPrice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_emi);

        tvSelectedCar = findViewById(R.id.tvSelectedCar);
        tvCarPrice = findViewById(R.id.tvCarPrice);

        etDownPayment = findViewById(R.id.etDownPayment);
        etInterestRate = findViewById(R.id.etInterestRate);
        etLoanTenure = findViewById(R.id.etLoanTenure);

        btnCalculateEMI = findViewById(R.id.btnCalculateEMI);

        tvLoanAmount = findViewById(R.id.tvLoanAmount);
        tvMonthlyEMI = findViewById(R.id.tvMonthlyEMI);
        tvTotalInterest = findViewById(R.id.tvTotalInterest);
        tvTotalAmount = findViewById(R.id.tvTotalAmount);

        databaseHelper = new DatabaseHelper(this);

        carId = getIntent().getIntExtra(
                "CAR_ID",
                -1
        );

        if (carId != -1) {

            loadCarPrice();

        } else {

            Toast.makeText(
                    this,
                    "Car not found",
                    Toast.LENGTH_SHORT
            ).show();

            finish();
        }
        btnCalculateEMI.setOnClickListener(v -> {

            String downPaymentText =
                    etDownPayment.getText()
                            .toString()
                            .trim();

            String interestText =
                    etInterestRate.getText()
                            .toString()
                            .trim();

            String tenureText =
                    etLoanTenure.getText()
                            .toString()
                            .trim();

            if (downPaymentText.isEmpty() ||
                    interestText.isEmpty() ||
                    tenureText.isEmpty()) {

                Toast.makeText(
                        this,
                        "Please enter all values",
                        Toast.LENGTH_SHORT
                ).show();

                return;
            }

            double downPayment =
                    Double.parseDouble(
                            downPaymentText
                    );

            double annualInterest =
                    Double.parseDouble(
                            interestText
                    );

            int years =
                    Integer.parseInt(
                            tenureText
                    );

            if (downPayment >= carPrice) {

                Toast.makeText(
                        this,
                        "Down payment must be less than car price",
                        Toast.LENGTH_SHORT
                ).show();

                return;
            }

            double loanAmount =
                    carPrice - downPayment;

            double monthlyInterest =
                    annualInterest / 12 / 100;

            int months =
                    years * 12;

            double emi;

            if (monthlyInterest == 0) {

                emi =
                        loanAmount / months;

            } else {

                emi =
                        loanAmount *
                                monthlyInterest *
                                Math.pow(
                                        1 + monthlyInterest,
                                        months
                                )
                                /
                                (
                                        Math.pow(
                                                1 + monthlyInterest,
                                                months
                                        ) - 1
                                );
            }

            double totalAmount =
                    emi * months;

            double totalInterest =
                    totalAmount - loanAmount;


            tvLoanAmount.setText(
                    "Loan Amount: ₹" +
                            String.format(
                                    "%.2f",
                                    loanAmount
                            )
            );

            tvMonthlyEMI.setText(
                    "Monthly EMI: ₹" +
                            String.format(
                                    "%.2f",
                                    emi
                            )
            );

            tvTotalInterest.setText(
                    "Total Interest: ₹" +
                            String.format(
                                    "%.2f",
                                    totalInterest
                            )
            );

            tvTotalAmount.setText(
                    "Total Amount Payable: ₹" +
                            String.format(
                                    "%.2f",
                                    totalAmount
                            )
            );
        });
    }

    private void loadCarPrice() {

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

            carPrice =
                    cursor.getDouble(
                            cursor.getColumnIndexOrThrow(
                                    "price"
                            )
                    );

            tvSelectedCar.setText(
                    "Car: " + brand + " " + name
            );

            tvCarPrice.setText(
                    "Car Price: ₹" +
                            String.format(
                                    "%.0f",
                                    carPrice
                            )
            );
        }

        cursor.close();
    }
}