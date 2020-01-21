package com.example.proiectfinalandroid.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.proiectfinalandroid.R;
import com.example.proiectfinalandroid.Util.Favourite_View;
import com.example.proiectfinalandroid.Util.ItemClick;
import com.example.proiectfinalandroid.ui_controler.tripEdit.EditFragment;
import com.example.proiectfinalandroid.ui_controler.tripNew.NewTripFragment;
import com.example.proiectfinalandroid.ui_controler.tripView.ViewFragment;

public class TripActivity extends AppCompatActivity {
    private final static String TAG_VIEW_FRAGMENT="VIEW_FRAGMENT";
    private final static String TAG_EDIT_FRAGMENT="EDIT_FRAGMENT";
    private final static String TAG_NEW_FRAGMENT="NEW_FRAGMENT";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip);
        Intent i=getIntent();
        FragmentManager fr = getSupportFragmentManager();
        FragmentTransaction ft = fr.beginTransaction();
        if(i!=null && i.hasExtra("ViewTrip")){
            getSupportActionBar().setTitle("View");
         ft.add(R.id.trip_container,new ViewFragment(i.getIntExtra("ViewTrip",0)),TAG_NEW_FRAGMENT);
         ft.commit();
        }else if(i!=null && i.hasExtra("EditTrip")){
            getSupportActionBar().setTitle("Edit");
            ft.add(R.id.trip_container,new EditFragment(i.getIntExtra("EditTrip",0)),TAG_EDIT_FRAGMENT);
            ft.commit();
        }else if(i!=null && i.hasExtra("NewTrip")){
            getSupportActionBar().setTitle("New Trip");
            ft.addToBackStack(null);
            ft.add(R.id.trip_container,new NewTripFragment(),TAG_NEW_FRAGMENT);
            ft.commit();
        }
    }

    public static String getTagEditFragment() {
        return TAG_EDIT_FRAGMENT;
    }

    public static String getTagNewFragment() {
        return TAG_NEW_FRAGMENT;
    }

    public static String getTagViewFragment() {
        return TAG_VIEW_FRAGMENT;
    }

}
