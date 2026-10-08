package com.example.smartcarshowroom.adapters;

import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smartcarshowroom.CarDetailsActivity;
import com.example.smartcarshowroom.R;
import com.example.smartcarshowroom.models.Car;

import java.io.File;
import java.util.ArrayList;

public class CarAdapter
        extends RecyclerView.Adapter<CarAdapter.CarViewHolder> {

    private ArrayList<Car> carList;

    private static final ArrayList<Integer> selectedCars =
            new ArrayList<>();

    public CarAdapter(ArrayList<Car> carList) {

        this.carList = carList;
    }

    @NonNull
    @Override
    public CarViewHolder onCreateViewHolder(
            @NonNull ViewGroup parent,
            int viewType) {

        View view =
                LayoutInflater.from(parent.getContext())
                        .inflate(
                                R.layout.item_car,
                                parent,
                                false
                        );

        return new CarViewHolder(view);
    }

    @Override
    public void onBindViewHolder(
            @NonNull CarViewHolder holder,
            int position) {

        Car car =
                carList.get(position);

        /*
         * ============================
         * LOAD CAR IMAGE
         * ============================
         */

        String imageValue =
                car.getImage();

        if (imageValue != null &&
                !imageValue.isEmpty()) {

            File imageFile =
                    new File(imageValue);

            /*
             * If the image is an uploaded
             * image stored in app storage
             */
            if (imageFile.exists()) {

                holder.ivCarImage.setImageURI(
                        Uri.fromFile(imageFile)
                );

            } else {

                /*
                 * Otherwise treat it as
                 * an existing drawable name
                 */

                int imageResource =
                        holder.itemView
                                .getContext()
                                .getResources()
                                .getIdentifier(
                                        imageValue,
                                        "drawable",
                                        holder.itemView
                                                .getContext()
                                                .getPackageName()
                                );

                if (imageResource != 0) {

                    holder.ivCarImage.setImageResource(
                            imageResource
                    );

                } else {

                    holder.ivCarImage.setImageResource(
                            android.R.drawable.ic_menu_gallery
                    );
                }
            }

        } else {

            holder.ivCarImage.setImageResource(
                    android.R.drawable.ic_menu_gallery
            );
        }


        /*
         * ============================
         * CAR INFORMATION
         * ============================
         */

        holder.tvCarName.setText(
                car.getBrand()
                        + " "
                        + car.getName()
        );

        holder.tvCarPrice.setText(
                "₹"
                        + String.format(
                        "%.0f",
                        car.getPrice()
                )
        );

        holder.tvCarInfo.setText(
                car.getFuelType()
                        + " • "
                        + car.getTransmission()
                        + " • "
                        + car.getMileage()
        );

        holder.tvAvailability.setText(
                car.getAvailability()
        );


        /*
         * ============================
         * VIEW DETAILS
         * ============================
         */

        holder.btnViewDetails.setOnClickListener(
                v -> {

                    Intent intent =
                            new Intent(
                                    v.getContext(),
                                    CarDetailsActivity.class
                            );

                    intent.putExtra(
                            "CAR_ID",
                            car.getCarId()
                    );

                    v.getContext().startActivity(
                            intent
                    );
                }
        );


        /*
         * ============================
         * COMPARE
         * ============================
         */

        int carId =
                car.getCarId();

        if (selectedCars.contains(carId)) {

            holder.btnCompare.setText(
                    "✓ SELECTED"
            );

        } else {

            holder.btnCompare.setText(
                    "⚖️ COMPARE"
            );
        }


        holder.btnCompare.setOnClickListener(
                v -> {

                    if (selectedCars.contains(carId)) {

                        selectedCars.remove(
                                Integer.valueOf(carId)
                        );

                        holder.btnCompare.setText(
                                "⚖️ COMPARE"
                        );

                    } else {

                        if (selectedCars.size() >= 3) {

                            Toast.makeText(
                                    v.getContext(),
                                    "You can compare maximum 3 cars",
                                    Toast.LENGTH_SHORT
                            ).show();

                            return;
                        }

                        selectedCars.add(
                                carId
                        );

                        holder.btnCompare.setText(
                                "✓ SELECTED"
                        );
                    }
                }
        );
    }


    @Override
    public int getItemCount() {

        return carList.size();
    }


    public static class CarViewHolder
            extends RecyclerView.ViewHolder {

        ImageView ivCarImage;

        TextView tvCarName;
        TextView tvCarPrice;
        TextView tvCarInfo;
        TextView tvAvailability;

        Button btnViewDetails;
        Button btnCompare;

        public CarViewHolder(
                @NonNull View itemView) {

            super(itemView);

            ivCarImage =
                    itemView.findViewById(
                            R.id.ivCarImage
                    );

            tvCarName =
                    itemView.findViewById(
                            R.id.tvCarName
                    );

            tvCarPrice =
                    itemView.findViewById(
                            R.id.tvCarPrice
                    );

            tvCarInfo =
                    itemView.findViewById(
                            R.id.tvCarInfo
                    );

            tvAvailability =
                    itemView.findViewById(
                            R.id.tvAvailability
                    );

            btnViewDetails =
                    itemView.findViewById(
                            R.id.btnViewDetails
                    );

            btnCompare =
                    itemView.findViewById(
                            R.id.btnCompare
                    );
        }
    }


    public static ArrayList<Integer>
    getSelectedCars() {

        return selectedCars;
    }


    public static void clearSelectedCars() {

        selectedCars.clear();
    }
}