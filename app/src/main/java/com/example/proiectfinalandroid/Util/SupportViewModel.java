package com.example.proiectfinalandroid.Util;

import android.app.Application;
import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProviders;

import com.example.proiectfinalandroid.ViewModel.GlobalViewModel;
import com.example.proiectfinalandroid.model.Trip;

import java.util.List;

public class SupportViewModel {
    private Context context;
    private LiveData<List<Trip>> list;
    private GlobalViewModel viewModel;
    private Application application;
    public SupportViewModel(Context context, Application application){
        this.context=context;
        this.application=application;
      //  viewModel= ViewModelProviders.of(context,new ViewModelFactory(application)).get(GlobalViewModel.class);
    }
}
