package com.example.proiectfinalandroid.Util;

import android.app.Application;
import android.util.Log;

import com.example.proiectfinalandroid.Database.AppDatabase;

public class BasicApplication extends Application {

    private static AppDatabase database;

    @Override
    public void onCreate() {
        super.onCreate();
        database = AppDatabase.createDatabase(this);
        Log.d("DEBUG", "BASICAPPLICATION CONSTRUCTOR CREATE DATABASE");
        if (database == null) {
            Log.d("DEBUG", "DATABSE INSTANCE IS NULL");
        }
    }

    public static AppDatabase getDatabase() {
        return database;
    }
}
