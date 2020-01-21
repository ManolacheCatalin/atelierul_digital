package com.example.proiectfinalandroid.Util;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.proiectfinalandroid.Database.AppDatabase;
import com.example.proiectfinalandroid.Database.TripDao;
import com.example.proiectfinalandroid.model.Trip;

import java.util.ArrayList;
import java.util.List;
import java.util.PrimitiveIterator;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class GlobalRepository {

    private TripDao tripDao;
    private MediatorLiveData<List<Trip>> trips=new MediatorLiveData<>();

    public GlobalRepository(Application application){
      tripDao=BasicApplication.getDatabase().tripDao();
        trips.addSource(tripDao.loadAllTrips(),tripsEntities-> {
            trips.postValue(tripsEntities);
        });
    }

    public LiveData<List<Trip>> getTrips() {
        return trips;
    }
    public LiveData<Trip> getSingleTrip(int tripID){
        return tripDao.loadTrip(tripID);
    }

    public void insertTrip(Trip trip){
        Executor executor=Executors.newSingleThreadExecutor();
        executor.execute(()->{
            tripDao.insertSingleTrip(trip);
        });
    }
    public void insertUpdatedTrip(Trip trip){
        Executor executor=Executors.newSingleThreadExecutor();
        executor.execute(()->{
            tripDao.insertUpdatedTrip(trip);
        });
    }
}
