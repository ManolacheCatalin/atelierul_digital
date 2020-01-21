package com.example.proiectfinalandroid.ui_controler.home;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.proiectfinalandroid.Adapters.RecyclerAdapter;
import com.example.proiectfinalandroid.R;
import com.example.proiectfinalandroid.Util.CheckPermission;
import com.example.proiectfinalandroid.Util.ItemClick;
import com.example.proiectfinalandroid.Util.ViewModelFactory;
import com.example.proiectfinalandroid.ViewModel.GlobalViewModel;
import com.example.proiectfinalandroid.model.Trip;

import java.util.List;

public class HomeFragment extends Fragment implements ItemClick {

    private RecyclerView recyclerView;
    private RecyclerAdapter adapter;
    private GlobalViewModel viewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        recyclerView = root.findViewById(R.id.recycler_home_id);
        adapter = new RecyclerAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setHasFixedSize(true);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel=ViewModelProviders.of(this,new ViewModelFactory(this.getActivity().getApplication())).get(GlobalViewModel.class);
        updateUi(viewModel.getMutableTrips());
    }


    @Override
    public void onResume() {
        super.onResume();
        CheckPermission.verifyStoragePermissions(getActivity());
    }

    public void updateUi(LiveData<List<Trip>> trips) {
        trips.observe(this,trip->{
            if(trip!=null){
                adapter.setTripList(trip,getActivity());
            }
        });
    }

    @Override
    public void click(Trip trip) {
     viewModel.insertUpdatedTrip(trip);
     adapter.notifyDataSetChanged();
    }
}