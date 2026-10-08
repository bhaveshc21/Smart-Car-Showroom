package com.example.smartcarshowroom;

import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.smartcarshowroom.database.DatabaseHelper;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class NotificationsActivity extends AppCompatActivity {

    ListView listNotifications;

    DatabaseHelper databaseHelper;

    ArrayList<String> notificationList;

    ArrayList<Integer> notificationIds;

    ArrayAdapter<String> adapter;

    int userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(
                R.layout.activity_notifications
        );

        listNotifications =
                findViewById(
                        R.id.listNotifications
                );

        databaseHelper =
                new DatabaseHelper(this);

        notificationList =
                new ArrayList<>();

        notificationIds =
                new ArrayList<>();

        adapter =
                new ArrayAdapter<>(
                        this,
                        R.layout.item_notification,
                        R.id.tvNotification,
                        notificationList
                );

        listNotifications.setAdapter(adapter);

        String userEmail =
                getSharedPreferences(
                        "SmartCarPrefs",
                        MODE_PRIVATE
                ).getString(
                        "USER_EMAIL",
                        ""
                );

        userId =
                databaseHelper.getUserId(
                        userEmail
                );

        loadNotifications();

        listNotifications.setOnItemClickListener(
                (parent, view, position, id) -> {

                    int notificationId =
                            notificationIds.get(position);

                    databaseHelper
                            .getWritableDatabase()
                            .execSQL(
                                    "UPDATE Notifications " +
                                            "SET is_read=1 " +
                                            "WHERE notification_id=?",
                                    new Object[]{
                                            notificationId
                                    }
                            );

                    loadNotifications();
                }
        );
    }

    private void loadNotifications() {

        Cursor cursor =
                databaseHelper
                        .getReadableDatabase()
                        .rawQuery(
                                "SELECT * " +
                                        "FROM Notifications " +
                                        "WHERE user_id=? " +
                                        "ORDER BY notification_id DESC",
                                new String[]{
                                        String.valueOf(
                                                userId
                                        )
                                }
                        );

        notificationList.clear();

        notificationIds.clear();

        while (cursor.moveToNext()) {

            int notificationId =
                    cursor.getInt(
                            cursor.getColumnIndexOrThrow(
                                    "notification_id"
                            )
                    );

            String title =
                    cursor.getString(
                            cursor.getColumnIndexOrThrow(
                                    "title"
                            )
                    );

            String message =
                    cursor.getString(
                            cursor.getColumnIndexOrThrow(
                                    "message"
                            )
                    );

            String createdAt =
                    cursor.getString(
                            cursor.getColumnIndexOrThrow(
                                    "created_at"
                            )
                    );

            String formattedDate = formatDateTime(createdAt);

            String notification =
                    title +
                            "\n" +
                            message +
                            "\n" +
                            formattedDate;

            notificationList.add(
                    notification
            );

            notificationIds.add(
                    notificationId
            );
        }

        cursor.close();

        adapter.notifyDataSetChanged();
    }

    private String formatDateTime(String timestamp) {

        try {

            long timeInMillis =
                    Long.parseLong(timestamp);

            SimpleDateFormat dateFormat =
                    new SimpleDateFormat(
                            "dd MMM yyyy, hh:mm a",
                            Locale.getDefault()
                    );

            return dateFormat.format(
                    new Date(timeInMillis)
            );

        } catch (Exception e) {

            return timestamp;
        }
    }

    @Override
    protected void onResume() {

        super.onResume();

        if (adapter != null) {

            loadNotifications();
        }
    }
}