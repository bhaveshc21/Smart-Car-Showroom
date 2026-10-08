package com.example.smartcarshowroom;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class FeaturedCarAdapter
        extends RecyclerView.Adapter<FeaturedCarAdapter.CarViewHolder> {

    ArrayList<FeaturedCar> carList;

    public FeaturedCarAdapter(
            ArrayList<FeaturedCar> carList
    ) {

        this.carList = carList;
    }

    @NonNull
    @Override
    public CarViewHolder onCreateViewHolder(
            @NonNull ViewGroup parent,
            int viewType
    ) {

        View view =
                LayoutInflater.from(
                        parent.getContext()
                ).inflate(
                        R.layout.item_featured_car,
                        parent,
                        false
                );

        return new CarViewHolder(view);
    }

    @Override
    public void onBindViewHolder(
            @NonNull CarViewHolder holder,
            int position
    ) {

        FeaturedCar car =
                carList.get(position);

        int imageResource =
                holder.itemView.getContext()
                        .getResources()
                        .getIdentifier(
                                car.image,
                                "drawable",
                                holder.itemView.getContext()
                                        .getPackageName()
                        );

        if (imageResource != 0) {

            holder.ivImage.setImageResource(
                    imageResource
            );
        }

        holder.tvName.setText(
                car.name
        );

        holder.tvPrice.setText(
                car.price
        );
    }

    @Override
    public int getItemCount() {

        return carList.size();
    }

    static class CarViewHolder
            extends RecyclerView.ViewHolder {

        ImageView ivImage;
        TextView tvName;
        TextView tvPrice;

        public CarViewHolder(
                @NonNull View itemView
        ) {

            super(itemView);

            ivImage =
                    itemView.findViewById(
                            R.id.ivFeaturedCarImage
                    );

            tvName =
                    itemView.findViewById(
                            R.id.tvFeaturedCarName
                    );

            tvPrice =
                    itemView.findViewById(
                            R.id.tvFeaturedCarPrice
                    );
        }
    }
}