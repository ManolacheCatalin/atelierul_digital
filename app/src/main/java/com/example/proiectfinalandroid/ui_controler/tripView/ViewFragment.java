package com.example.proiectfinalandroid.ui_controler.tripView;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;

import android.app.Application;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.proiectfinalandroid.R;
import com.example.proiectfinalandroid.Util.Favourite_View;
import com.example.proiectfinalandroid.Util.ItemClick;
import com.example.proiectfinalandroid.Util.ViewModelFactory;
import com.example.proiectfinalandroid.ViewModel.GlobalViewModel;
import com.example.proiectfinalandroid.model.Trip;

public class ViewFragment extends Fragment {

    private TextView title,destination,price,type;
    private RatingBar bar;
    private ImageView media;
    private final int id_trip;
    private GlobalViewModel viewModel;

    public ViewFragment(final int id_trip) {
        this.id_trip=id_trip;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_trip, container, false);
        title=view.findViewById(R.id.title_view_id);
        type=view.findViewById(R.id.type_view_id);
        price=view.findViewById(R.id.price_view_id);
        destination=view.findViewById(R.id.destination_view_id);
        bar=view.findViewById(R.id.rating_view_id);
        media=view.findViewById(R.id.media_view_id);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = ViewModelProviders.of(this, new ViewModelFactory(this.getActivity().getApplication())).get(GlobalViewModel.class);
        updateUi(viewModel.getSingleTrip(id_trip));
    }
    public void updateUi(LiveData<Trip> trips){
        trips.observe(this,trip->{
            Glide.with(getContext()).asBitmap().load(trip.getPathImage()).placeholder(R.drawable.paris_back).into(media);
            title.setText(trip.getName_Trip());
            destination.setText(trip.getDestination_Trip());
            price.setText(String.valueOf(trip.getCost_trip())+" "+"RON");
            bar.setRating((float)trip.getRating());
            Log.d("DEBUG",String.valueOf(trip.getRating()));
            type.setText(trip.getType());
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        getActivity().finish();
    }
}
