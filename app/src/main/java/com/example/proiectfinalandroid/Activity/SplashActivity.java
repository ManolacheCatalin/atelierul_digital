package com.example.proiectfinalandroid.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.widget.Toast;

import com.example.proiectfinalandroid.Database.AppDatabase;
import com.example.proiectfinalandroid.R;
import com.example.proiectfinalandroid.Util.BasicApplication;
import com.example.proiectfinalandroid.Util.BitmapSupport;
import com.example.proiectfinalandroid.Util.CheckPermission;
import com.example.proiectfinalandroid.ui_controler.FirstLoginFragment;
import com.example.proiectfinalandroid.ui_controler.SplashFragment;

import java.util.List;

public class SplashActivity extends AppCompatActivity {
    FragmentManager fr = getSupportFragmentManager();
    FragmentTransaction ft = fr.beginTransaction();

    private ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        if (readPreference(this)) {
            ft.add(R.id.contenitor_splash, new SplashFragment());
            ft.commit();
        } else {
            writePreference(this);
            ft.add(R.id.contenitor_splash, new FirstLoginFragment());
            ft.commit();
        }
      /*  dialog = new ProgressDialog(SplashActivity.this);
        dialog.setMessage("Loading data...");
        dialog.show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                dialog.dismiss();
                Intent i = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(i);
                finish();
            }
        }, 1000);*/
    }

    private static class JobTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {

            return null;
        }
    }

    private void writePreference(Activity activity) {
        SharedPreferences sharedPreferences = activity.getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(getString(R.string.myPreference_key), "Login");
        editor.commit();
    }

    private boolean readPreference(Activity activity) {
        boolean flag = false;
        SharedPreferences sharedPreferences = activity.getPreferences(Context.MODE_PRIVATE);
        String defaultVal = getResources().getString(R.string.myPreference_key);
        String key = sharedPreferences.getString(getString(R.string.myPreference_key), defaultVal);
        if (key.compareTo("Login")==0) {
            flag = true;
        }
        return flag;
    }
}
