package com.example.smartcarshowroom.models;

public class Car {

    private int carId;
    private String brand;
    private String name;
    private String model;
    private double price;
    private String fuelType;
    private String transmission;
    private String mileage;
    private String engine;
    private int seatingCapacity;
    private String features;
    private String availability;
    private String image;

    public Car(int carId, String brand, String name, String model,
               double price, String fuelType, String transmission,
               String mileage, String engine, int seatingCapacity,
               String features, String availability, String image) {

        this.carId = carId;
        this.brand = brand;
        this.name = name;
        this.model = model;
        this.price = price;
        this.fuelType = fuelType;
        this.transmission = transmission;
        this.mileage = mileage;
        this.engine = engine;
        this.seatingCapacity = seatingCapacity;
        this.features = features;
        this.availability = availability;
        this.image = image;
    }

    public int getCarId() {
        return carId;
    }

    public String getBrand() {
        return brand;
    }

    public String getName() {
        return name;
    }

    public String getModel() {
        return model;
    }

    public double getPrice() {
        return price;
    }

    public String getFuelType() {
        return fuelType;
    }

    public String getTransmission() {
        return transmission;
    }

    public String getMileage() {
        return mileage;
    }

    public String getEngine() {
        return engine;
    }

    public int getSeatingCapacity() {
        return seatingCapacity;
    }

    public String getFeatures() {
        return features;
    }

    public String getAvailability() {
        return availability;
    }

    public String getImage() {
        return image;
    }
}