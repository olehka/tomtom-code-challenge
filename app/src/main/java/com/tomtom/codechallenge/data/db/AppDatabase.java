package com.tomtom.codechallenge.data.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.tomtom.codechallenge.data.Document;


@Database(entities = {Document.class}, version = 1, exportSchema = false)
@TypeConverters({DocumentTypeConverters.class})
public abstract class AppDatabase extends RoomDatabase {

    public static final String DATABASE_NAME = "tomtom-db";

    private static AppDatabase instance;

    public abstract DocumentDao documentDao();

    public static AppDatabase getInstance(Context context) {
        if (instance == null) {
            synchronized (AppDatabase.class) {
                if (instance == null) {
                    instance = createDatabase(context.getApplicationContext());
                }
            }
        }
        return instance;
    }

    private static AppDatabase createDatabase(Context context) {
        return Room.databaseBuilder(context, AppDatabase.class, DATABASE_NAME)
                .build();
    }
}
