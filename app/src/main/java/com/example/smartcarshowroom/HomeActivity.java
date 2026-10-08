package com.example.smartcarshowroom;

import android.content.Intent;
import android.widget.Button;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.ImageButton;
import android.graphics.Color;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.viewpager2.widget.ViewPager2;

import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;

public class HomeActivity extends AppCompatActivity {

    TextView tvWelcome;
    Button btnCars;
    Button btnFavorites;
    Button btnMyEnquiries;
    Button btnMyTestDrives;
    Button btnNotifications;
    Button btnShowroom;
    ImageButton btnProfile;
    ViewPager2 viewPagerFeaturedCars;
    LinearLayout layoutDots;
    ArrayList<FeaturedCar> featuredCars;
    FeaturedCarAdapter featuredCarAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_home);

        viewPagerFeaturedCars =
                findViewById(
                        R.id.viewPagerFeaturedCars
                );

        layoutDots =
                findViewById(
                        R.id.layoutDots
                );

        featuredCars =
                new ArrayList<>();

        featuredCars.add(
                new FeaturedCar(
                        "Hyundai Creta",
                        "₹12.5 Lakh",
                        "creta"
                )
        );

        featuredCars.add(
                new FeaturedCar(
                        "Tata Nexon",
                        "₹10 Lakh",
                        "nexon"
                )
        );

        featuredCars.add(
                new FeaturedCar(
                        "Mahindra XUV700",
                        "₹14 Lakh",
                        "xuv700"
                )
        );

        featuredCarAdapter =
                new FeaturedCarAdapter(
                        featuredCars
                );

        viewPagerFeaturedCars.setAdapter(
                featuredCarAdapter
        );

        setupDots();

        viewPagerFeaturedCars.registerOnPageChangeCallback(
                new ViewPager2.OnPageChangeCallback() {

                    @Override
                    public void onPageSelected(
                            int position
                    ) {

                        super.onPageSelected(
                                position
                        );

                        updateDots(position);
                    }
                }
        );

        tvWelcome = findViewById(R.id.tvWelcome);
        btnCars = findViewById(R.id.btnCars);
        btnFavorites = findViewById(
                R.id.btnFavorites
        );
        btnMyEnquiries =
                findViewById(R.id.btnMyEnquiries);
        btnMyTestDrives =
                findViewById(
                        R.id.btnMyTestDrives
                );
        btnNotifications =
                findViewById(
                        R.id.btnNotifications
                );

        btnShowroom =
                findViewById(
                        R.id.btnShowroom
                );
        btnProfile =
                findViewById(
                        R.id.btnProfile
                );

        btnProfile.setOnClickListener(v -> {

            Intent intent =
                    new Intent(
                            HomeActivity.this,
                            ProfileActivity.class
                    );

            startActivity(intent);
        });

        btnShowroom.setOnClickListener(v -> {

            Intent intent =
                    new Intent(
                            HomeActivity.this,
                            ShowroomActivity.class
                    );

            startActivity(intent);
        });

        btnNotifications.setOnClickListener(v -> {

            Intent intent =
                    new Intent(
                            HomeActivity.this,
                            NotificationsActivity.class
                    );

            startActivity(intent);
        });

        btnFavorites.setOnClickListener(v -> {

            Intent intent = new Intent(
                    HomeActivity.this,
                    FavoritesActivity.class
            );

            startActivity(intent);
        });

        btnCars.setOnClickListener(v -> {

            Intent intent = new Intent(
                    HomeActivity.this,
                    CarListActivity.class
            );

            startActivity(intent);
        });
        btnMyEnquiries.setOnClickListener(v -> {

            Intent intent =
                    new Intent(
                            HomeActivity.this,
                            MyEnquiriesActivity.class
                    );

            startActivity(intent);

        });

        btnMyTestDrives.setOnClickListener(v -> {

            Intent intent =
                    new Intent(
                            HomeActivity.this,
                            MyTestDrivesActivity.class
                    );

            startActivity(intent);
        });

        String userName = getIntent().getStringExtra("USER_NAME");

        if (userName != null) {
            tvWelcome.setText("Welcome, " + userName + " 👋");
        }
    }
    private void setupDots() {

        layoutDots.removeAllViews();

        for (
                int i = 0;
                i < featuredCars.size();
                i++
        ) {

            ImageView dot =
                    new ImageView(this);

            dot.setImageResource(
                    android.R.drawable.presence_invisible
            );

            LinearLayout.LayoutParams params =
                    new LinearLayout.LayoutParams(
                            20,
                            20
                    );

            params.setMargins(
                    8,
                    0,
                    8,
                    0
            );

            layoutDots.addView(
                    dot,
                    params
            );
        }

        updateDots(0);
    }
    private void updateDots(int position) {

        for (
                int i = 0;
                i < layoutDots.getChildCount();
                i++
        ) {

            ImageView dot =
                    (ImageView)
                            layoutDots.getChildAt(i);

            if (i == position) {

                dot.setImageResource(
                        android.R.drawable.presence_online
                );

            } else {

                dot.setImageResource(
                        android.R.drawable.presence_invisible
                );
            }
        }
    }
}