package com.example.proiectfinalandroid.ui_controler;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.provider.ContactsContract;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.proiectfinalandroid.Activity.MainActivity;
import com.example.proiectfinalandroid.R;
public class FirstLoginFragment extends Fragment implements View.OnClickListener {

    private Button button;
    private EditText user_text,email_text;
    public FirstLoginFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.first_login, container, false);
        button=view.findViewById(R.id.btn_register);
        user_text=view.findViewById(R.id.et_name);
        email_text=view.findViewById(R.id.et_email);
        button.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View view) {
        Login();

    }
    private void writeData(String user,String email){
        SharedPreferences sharedPreferences=getActivity().getSharedPreferences("Login", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putString("User",user);
        editor.putString("Email",email);
        editor.apply();
    }
    private void startHome(){
        Intent intent=new Intent(getActivity(), MainActivity.class);
        getActivity().startActivity(intent);
        getActivity().finish();
    }
    private void Login(){
        boolean flag=false;
        String user=user_text.getText().toString();
        String email=email_text.getText().toString();
        if(!TextUtils.isEmpty(user) && !TextUtils.isEmpty(email)){
         writeData(user,email);
         startHome();
        }
        else {
            Toast.makeText(getActivity(),"Try again",Toast.LENGTH_SHORT).show();
        }
    }
}
