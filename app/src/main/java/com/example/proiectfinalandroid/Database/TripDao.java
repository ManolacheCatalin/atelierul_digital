package com.example.proiectfinalandroid.Database;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.proiectfinalandroid.model.Trip;

import java.io.File;
import java.util.List;

@Dao
public interface TripDao {
    @Query("select *from Trips")
    LiveData<List<Trip>> loadAllTrips();

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertSingleTrip(Trip trip);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertUpdatedTrip(Trip trip);

    @Query("select *from Trips where id_trip=:tripID")
    LiveData<Trip> loadTrip(int tripID);

    @Update
    void updateSingleTrip(Trip trip);


}
