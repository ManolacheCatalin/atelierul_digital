package com.example.proiectfinalandroid.Database;

import android.content.Context;
import android.util.Log;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.proiectfinalandroid.model.Trip;

@Database(entities = {Trip.class},version = 1,exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    public abstract TripDao tripDao();
    private static String DB_NAME="Trip_DB";
    private static AppDatabase instance;

    public static AppDatabase createDatabase(final Context context){
        if(instance==null){
            synchronized (AppDatabase.class){
                if(instance==null){
                    instance=Room.databaseBuilder(context.getApplicationContext(),AppDatabase.class,DB_NAME).build();
                    Log.d("DEBUG","CREATE DATABASE APPLICATION");
                }
            }
        }
        return instance;
    }
}

