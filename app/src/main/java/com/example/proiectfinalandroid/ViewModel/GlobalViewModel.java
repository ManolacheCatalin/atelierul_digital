package com.example.proiectfinalandroid.ViewModel;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.proiectfinalandroid.Database.AppDatabase;
import com.example.proiectfinalandroid.Util.BasicApplication;
import com.example.proiectfinalandroid.Util.GlobalRepository;
import com.example.proiectfinalandroid.model.Trip;

import java.util.List;

public class GlobalViewModel extends AndroidViewModel {

    private final MediatorLiveData<List<Trip>> mObservableTrips = new MediatorLiveData<>();
    private GlobalRepository repository;

    public GlobalViewModel(Application application) {
        super(application);
        repository = new GlobalRepository(application);
        mObservableTrips.setValue(null);
        LiveData<List<Trip>> liveData = repository.getTrips();
        mObservableTrips.addSource(liveData, mObservableTrips::setValue);
    }

    public LiveData<List<Trip>> getMutableTrips() {
        return mObservableTrips;
    }
    public LiveData<Trip> getSingleTrip(int tripID){
        return repository.getSingleTrip(tripID);
    }


    public void insertTrip(Trip trip) {
        repository.insertTrip(trip);
        Log.d("DEBUG", "INSERT TRIP VIEWMODEL");
    }
    public void insertUpdatedTrip(Trip trip){
        repository.insertUpdatedTrip(trip);
    }
}
