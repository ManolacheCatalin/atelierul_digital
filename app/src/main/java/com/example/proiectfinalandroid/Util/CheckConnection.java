package com.example.proiectfinalandroid.Util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.widget.Toast;

public class CheckConnection {

    private Context context;
    public CheckConnection(Context context){
       this.context=context;
    }
    public boolean isConnected(){
        boolean flag=false;
        try {
            ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            if(cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected()){
                flag=true;
            }
        }catch (Exception ex){
            Toast.makeText(context,ex.getMessage().toString(),Toast.LENGTH_LONG).show();
        }
        return flag;
    }
}
