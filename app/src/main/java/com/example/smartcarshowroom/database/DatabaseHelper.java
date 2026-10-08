package com.example.smartcarshowroom.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "SmartCarShowroom.db";
    private static final int DATABASE_VERSION = 3;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        // Users table
        db.execSQL("CREATE TABLE Users (" +
                "user_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "name TEXT," +
                "email TEXT UNIQUE," +
                "phone TEXT," +
                "password TEXT," +
                "role TEXT)");

        // Cars table
        db.execSQL("CREATE TABLE Cars (" +
                "car_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "brand TEXT," +
                "name TEXT," +
                "model TEXT," +
                "price REAL," +
                "fuel_type TEXT," +
                "transmission TEXT," +
                "mileage TEXT," +
                "engine TEXT," +
                "seating_capacity INTEGER," +
                "features TEXT," +
                "availability TEXT," +
                "image TEXT)");

        // Favorites table
        db.execSQL("CREATE TABLE Favorites (" +
                "favorite_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "user_email TEXT," +
                "car_id INTEGER)");

        // Enquiries table
        db.execSQL("CREATE TABLE Enquiries (" +
                "enquiry_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "user_id INTEGER," +
                "car_id INTEGER," +
                "enquiry_type TEXT," +
                "message TEXT," +
                "admin_response TEXT," +
                "status TEXT," +
                "created_at TEXT)");

        // Test Drives table
        db.execSQL("CREATE TABLE TestDrives (" +
                "test_drive_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "user_id INTEGER," +
                "car_id INTEGER," +
                "date TEXT," +
                "time TEXT," +
                "message TEXT," +
                "status TEXT," +
                "created_at TEXT)");

        // Notifications table
        db.execSQL("CREATE TABLE Notifications (" +
                "notification_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "user_id INTEGER," +
                "title TEXT," +
                "message TEXT," +
                "is_read INTEGER DEFAULT 0," +
                "created_at TEXT)");


        // Insert sample cars
        insertSampleCars(db);

    }

    @Override
    public void onUpgrade(
            SQLiteDatabase db,
            int oldVersion,
            int newVersion) {

        if (oldVersion < 2) {

            db.execSQL("CREATE TABLE IF NOT EXISTS Notifications (" +
                    "notification_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "user_id INTEGER," +
                    "title TEXT," +
                    "message TEXT," +
                    "is_read INTEGER DEFAULT 0," +
                    "created_at TEXT)");
        }

        if (oldVersion < 3) {

            insertNewCars(db);
        }
    }
    public boolean registerUser(String name, String email, String phone, String password) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put("name", name);
        values.put("email", email);
        values.put("phone", phone);
        values.put("password", password);
        values.put("role", "CUSTOMER");

        long result = db.insert("Users", null, values);

        return result != -1;
    }

    public boolean checkCustomerLogin(String email, String password) {

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(
                "SELECT * FROM Users WHERE email=? AND password=? AND role=?",
                new String[]{email, password, "CUSTOMER"}
        );

        boolean exists = cursor.getCount() > 0;

        cursor.close();

        return exists;
    }

    public String getUserName(String email) {

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(
                "SELECT name FROM Users WHERE email=?",
                new String[]{email}
        );

        String name = "";

        if (cursor.moveToFirst()) {
            name = cursor.getString(0);
        }

        cursor.close();

        return name;
    }

    public int getUserId(String email) {

        SQLiteDatabase db =
                this.getReadableDatabase();

        Cursor cursor = db.rawQuery(
                "SELECT user_id FROM Users WHERE email=?",
                new String[]{email}
        );

        int userId = -1;

        if (cursor.moveToFirst()) {

            userId = cursor.getInt(
                    cursor.getColumnIndexOrThrow(
                            "user_id"
                    )
            );
        }

        cursor.close();

        return userId;
    }

    public void insertSampleCars(SQLiteDatabase db) {

        ContentValues values = new ContentValues();

        values.put("brand", "Hyundai");
        values.put("name", "Creta");
        values.put("model", "SX");
        values.put("price", 1250000);
        values.put("fuel_type", "Petrol");
        values.put("transmission", "Automatic");
        values.put("mileage", "17 km/l");
        values.put("engine", "1497 cc");
        values.put("seating_capacity", 5);
        values.put("features", "Sunroof, Airbags, ABS, Touchscreen");
        values.put("availability", "Available");
        values.put("image", "creta");

        db.insert("Cars", null, values);


        values.clear();

        values.put("brand", "Tata");
        values.put("name", "Nexon");
        values.put("model", "XZ");
        values.put("price", 1000000);
        values.put("fuel_type", "Petrol");
        values.put("transmission", "Manual");
        values.put("mileage", "17.4 km/l");
        values.put("engine", "1199 cc");
        values.put("seating_capacity", 5);
        values.put("features", "Airbags, ABS, Touchscreen, Cruise Control");
        values.put("availability", "Available");
        values.put("image", "nexon");

        db.insert("Cars", null, values);


        values.clear();

        values.put("brand", "Maruti");
        values.put("name", "Swift");
        values.put("model", "VXI");
        values.put("price", 800000);
        values.put("fuel_type", "Petrol");
        values.put("transmission", "Manual");
        values.put("mileage", "22 km/l");
        values.put("engine", "1197 cc");
        values.put("seating_capacity", 5);
        values.put("features", "Airbags, ABS, LED Headlights");
        values.put("availability", "Available");
        values.put("image", "swift");

        db.insert("Cars", null, values);


        values.clear();

        values.put("brand", "Mahindra");
        values.put("name", "XUV700");
        values.put("model", "AX5");
        values.put("price", 1500000);
        values.put("fuel_type", "Diesel");
        values.put("transmission", "Automatic");
        values.put("mileage", "17 km/l");
        values.put("engine", "2198 cc");
        values.put("seating_capacity", 7);
        values.put("features", "Panoramic Sunroof, Airbags, ADAS");
        values.put("availability", "Available");
        values.put("image", "xuv700");

        db.insert("Cars", null, values);


        values.clear();

        values.put("brand", "Kia");
        values.put("name", "Seltos");
        values.put("model", "HTX");
        values.put("price", 1450000);
        values.put("fuel_type", "Petrol");
        values.put("transmission", "Automatic");
        values.put("mileage", "17.7 km/l");
        values.put("engine", "1497 cc");
        values.put("seating_capacity", 5);
        values.put("features", "Sunroof, Airbags, Touchscreen");
        values.put("availability", "Available");
        values.put("image", "seltos");

        db.insert("Cars", null, values);


        values.clear();

        values.put("brand", "Tata");
        values.put("name", "Punch");
        values.put("model", "Creative");
        values.put("price", 900000);
        values.put("fuel_type", "Petrol");
        values.put("transmission", "Manual");
        values.put("mileage", "18.8 km/l");
        values.put("engine", "1199 cc");
        values.put("seating_capacity", 5);
        values.put("features", "Airbags, ABS, Rear Camera");
        values.put("availability", "Available");
        values.put("image", "punch");

        db.insert("Cars", null, values);

        // Car 7 - Toyota Fortuner
        values.clear();

        values.put("brand", "Toyota");
        values.put("name", "Fortuner");
        values.put("model", "4X2 AT");
        values.put("price", 3500000);
        values.put("fuel_type", "Diesel");
        values.put("transmission", "Automatic");
        values.put("mileage", "14.4 km/l");
        values.put("engine", "2755 cc");
        values.put("seating_capacity", 7);
        values.put("features", "Sunroof, Airbags, ABS, Cruise Control");
        values.put("availability", "Available");
        values.put("image", "fortuner");

        db.insert("Cars", null, values);


        // Car 8 - Honda City
        values.clear();

        values.put("brand", "Honda");
        values.put("name", "City");
        values.put("model", "ZX");
        values.put("price", 1600000);
        values.put("fuel_type", "Petrol");
        values.put("transmission", "Automatic");
        values.put("mileage", "18.4 km/l");
        values.put("engine", "1498 cc");
        values.put("seating_capacity", 5);
        values.put("features", "Sunroof, Airbags, ABS, Touchscreen");
        values.put("availability", "Available");
        values.put("image", "city");

        db.insert("Cars", null, values);


        // Car 9 - Hyundai Verna
        values.clear();

        values.put("brand", "Hyundai");
        values.put("name", "Verna");
        values.put("model", "SX");
        values.put("price", 1500000);
        values.put("fuel_type", "Petrol");
        values.put("transmission", "Automatic");
        values.put("mileage", "20.6 km/l");
        values.put("engine", "1482 cc");
        values.put("seating_capacity", 5);
        values.put("features", "ADAS, Sunroof, Airbags, Touchscreen");
        values.put("availability", "Available");
        values.put("image", "verna");

        db.insert("Cars", null, values);


        // Car 10 - Tata Harrier
        values.clear();

        values.put("brand", "Tata");
        values.put("name", "Harrier");
        values.put("model", "XZ");
        values.put("price", 2200000);
        values.put("fuel_type", "Diesel");
        values.put("transmission", "Automatic");
        values.put("mileage", "16.8 km/l");
        values.put("engine", "1956 cc");
        values.put("seating_capacity", 5);
        values.put("features", "Panoramic Sunroof, ADAS, Airbags, Touchscreen");
        values.put("availability", "Available");
        values.put("image", "harrier");

        db.insert("Cars", null, values);


        // Car 11 - Mahindra Scorpio-N
        values.clear();

        values.put("brand", "Mahindra");
        values.put("name", "Scorpio-N");
        values.put("model", "Z8");
        values.put("price", 2400000);
        values.put("fuel_type", "Diesel");
        values.put("transmission", "Automatic");
        values.put("mileage", "15.4 km/l");
        values.put("engine", "2198 cc");
        values.put("seating_capacity", 7);
        values.put("features", "Sunroof, Airbags, ABS, Touchscreen");
        values.put("availability", "Available");
        values.put("image", "scorpion");

        db.insert("Cars", null, values);


        // Car 12 - Kia Sonet
        values.clear();

        values.put("brand", "Kia");
        values.put("name", "Sonet");
        values.put("model", "GTX+");
        values.put("price", 1500000);
        values.put("fuel_type", "Petrol");
        values.put("transmission", "Automatic");
        values.put("mileage", "18.7 km/l");
        values.put("engine", "1197 cc");
        values.put("seating_capacity", 5);
        values.put("features", "Sunroof, Airbags, Touchscreen, Cruise Control");
        values.put("availability", "Available");
        values.put("image", "sonet");

        db.insert("Cars", null, values);
    }

    private void insertNewCars(SQLiteDatabase db) {

        ContentValues values = new ContentValues();

        // Car 7 - Toyota Fortuner
        values.put("brand", "Toyota");
        values.put("name", "Fortuner");
        values.put("model", "4X2 AT");
        values.put("price", 3500000);
        values.put("fuel_type", "Diesel");
        values.put("transmission", "Automatic");
        values.put("mileage", "14.4 km/l");
        values.put("engine", "2755 cc");
        values.put("seating_capacity", 7);
        values.put("features", "Sunroof, Airbags, ABS, Cruise Control");
        values.put("availability", "Available");
        values.put("image", "fortuner");

        db.insert("Cars", null, values);


        // Car 8 - Honda City
        values.clear();

        values.put("brand", "Honda");
        values.put("name", "City");
        values.put("model", "ZX");
        values.put("price", 1600000);
        values.put("fuel_type", "Petrol");
        values.put("transmission", "Automatic");
        values.put("mileage", "18.4 km/l");
        values.put("engine", "1498 cc");
        values.put("seating_capacity", 5);
        values.put("features", "Sunroof, Airbags, ABS, Touchscreen");
        values.put("availability", "Available");
        values.put("image", "city");

        db.insert("Cars", null, values);


        // Car 9 - Hyundai Verna
        values.clear();

        values.put("brand", "Hyundai");
        values.put("name", "Verna");
        values.put("model", "SX");
        values.put("price", 1500000);
        values.put("fuel_type", "Petrol");
        values.put("transmission", "Automatic");
        values.put("mileage", "20.6 km/l");
        values.put("engine", "1482 cc");
        values.put("seating_capacity", 5);
        values.put("features", "ADAS, Sunroof, Airbags, Touchscreen");
        values.put("availability", "Available");
        values.put("image", "verna");

        db.insert("Cars", null, values);


        // Car 10 - Tata Harrier
        values.clear();

        values.put("brand", "Tata");
        values.put("name", "Harrier");
        values.put("model", "XZ");
        values.put("price", 2200000);
        values.put("fuel_type", "Diesel");
        values.put("transmission", "Automatic");
        values.put("mileage", "16.8 km/l");
        values.put("engine", "1956 cc");
        values.put("seating_capacity", 5);
        values.put("features", "Panoramic Sunroof, ADAS, Airbags, Touchscreen");
        values.put("availability", "Available");
        values.put("image", "harrier");

        db.insert("Cars", null, values);


        // Car 11 - Mahindra Scorpio-N
        values.clear();

        values.put("brand", "Mahindra");
        values.put("name", "Scorpio-N");
        values.put("model", "Z8");
        values.put("price", 2400000);
        values.put("fuel_type", "Diesel");
        values.put("transmission", "Automatic");
        values.put("mileage", "15.4 km/l");
        values.put("engine", "2198 cc");
        values.put("seating_capacity", 7);
        values.put("features", "Sunroof, Airbags, ABS, Touchscreen");
        values.put("availability", "Available");
        values.put("image", "scorpion");

        db.insert("Cars", null, values);


        // Car 12 - Kia Sonet
        values.clear();

        values.put("brand", "Kia");
        values.put("name", "Sonet");
        values.put("model", "GTX+");
        values.put("price", 1500000);
        values.put("fuel_type", "Petrol");
        values.put("transmission", "Automatic");
        values.put("mileage", "18.7 km/l");
        values.put("engine", "1197 cc");
        values.put("seating_capacity", 5);
        values.put("features", "Sunroof, Airbags, Touchscreen, Cruise Control");
        values.put("availability", "Available");
        values.put("image", "sonet");

        db.insert("Cars", null, values);
    }

    public boolean areCarsAvailable() {

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(
                "SELECT COUNT(*) FROM Cars",
                null
        );

        cursor.moveToFirst();

        int count = cursor.getInt(0);

        cursor.close();

        return count > 0;
    }

    public Cursor getAllCars() {

        SQLiteDatabase db = this.getReadableDatabase();

        return db.rawQuery(
                "SELECT * FROM Cars",
                null
        );
    }

    public Cursor getCarById(int carId) {

        SQLiteDatabase db = this.getReadableDatabase();

        return db.rawQuery(
                "SELECT * FROM Cars WHERE car_id=?",
                new String[]{String.valueOf(carId)}
        );
    }

    public Cursor searchCars(
            String search,
            String brand,
            String fuelType,
            String transmission) {

        SQLiteDatabase db = this.getReadableDatabase();

        String query =
                "SELECT * FROM Cars WHERE " +
                        "(name LIKE ? OR brand LIKE ?) AND " +
                        "brand LIKE ? AND " +
                        "fuel_type LIKE ? AND " +
                        "transmission LIKE ?";

        String searchValue = "%" + search + "%";

        String brandValue =
                brand.equals("All Brands")
                        ? "%"
                        : brand;

        String fuelValue =
                fuelType.equals("All Fuel Types")
                        ? "%"
                        : fuelType;

        String transmissionValue =
                transmission.equals("All Transmissions")
                        ? "%"
                        : transmission;

        return db.rawQuery(
                query,
                new String[]{
                        searchValue,
                        searchValue,
                        brandValue,
                        fuelValue,
                        transmissionValue
                }
        );
    }
    public void createFavoritesTable() {

        SQLiteDatabase db = this.getWritableDatabase();

        db.execSQL(
                "CREATE TABLE IF NOT EXISTS Favorites (" +
                        "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                        "user_email TEXT," +
                        "car_id INTEGER)"
        );
    }
    public void addFavorite(
            String userEmail,
            int carId) {

        createFavoritesTable();

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put("user_email", userEmail);
        values.put("car_id", carId);

        db.insert(
                "Favorites",
                null,
                values
        );
    }
    public boolean isFavorite(
            String userEmail,
            int carId) {

        createFavoritesTable();

        SQLiteDatabase db =
                this.getReadableDatabase();

        Cursor cursor = db.rawQuery(
                "SELECT favorite_id FROM Favorites WHERE user_email=? AND car_id=?",
                new String[]{
                        userEmail,
                        String.valueOf(carId)
                }
        );

        boolean favorite = cursor.moveToFirst();

        cursor.close();

        return favorite;
    }
    public void removeFavorite(
            String userEmail,
            int carId) {

        createFavoritesTable();

        SQLiteDatabase db =
                this.getWritableDatabase();

        db.delete(
                "Favorites",
                "user_email=? AND car_id=?",
                new String[]{
                        userEmail,
                        String.valueOf(carId)
                }
        );
    }
    public Cursor getFavoriteCars(
            String userEmail) {

        createFavoritesTable();

        SQLiteDatabase db =
                this.getReadableDatabase();

        return db.rawQuery(
                "SELECT Cars.* FROM Cars " +
                        "INNER JOIN Favorites " +
                        "ON Cars.car_id = Favorites.car_id " +
                        "WHERE Favorites.user_email=?",
                new String[]{
                        userEmail
                }
        );
    }

    public boolean deleteUserAccount(String email) {

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery(
                "SELECT user_id FROM Users WHERE email=?",
                new String[]{email}
        );

        if (!cursor.moveToFirst()) {
            cursor.close();
            return false;
        }

        int userId = cursor.getInt(
                cursor.getColumnIndexOrThrow("user_id")
        );

        cursor.close();

        db.beginTransaction();

        try {

            // Delete enquiries
            db.delete(
                    "Enquiries",
                    "user_id=?",
                    new String[]{
                            String.valueOf(userId)
                    }
            );

            // Delete test drives
            db.delete(
                    "TestDrives",
                    "user_id=?",
                    new String[]{
                            String.valueOf(userId)
                    }
            );

            // Delete notifications
            db.delete(
                    "Notifications",
                    "user_id=?",
                    new String[]{
                            String.valueOf(userId)
                    }
            );

            // Delete user
            db.delete(
                    "Users",
                    "user_id=?",
                    new String[]{
                            String.valueOf(userId)
                    }
            );

            db.setTransactionSuccessful();

            return true;

        } finally {

            db.endTransaction();
        }
    }
}