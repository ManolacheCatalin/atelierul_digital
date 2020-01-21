package com.example.proiectfinalandroid.ui_controler;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.proiectfinalandroid.Activity.MainActivity;
import com.example.proiectfinalandroid.R;

public class SplashFragment extends Fragment {

    public SplashFragment(){

    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.splash_fragment,container,false);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        doWordk();
    }

    private void doWordk(){
        Intent intent=new Intent(getActivity(), MainActivity.class);
        getActivity().startActivity(intent);
    }
}
