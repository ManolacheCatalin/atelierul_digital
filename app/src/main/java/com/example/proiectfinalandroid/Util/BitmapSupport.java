package com.example.proiectfinalandroid.Util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

public class BitmapSupport {
    private File pathImag;
    public BitmapSupport(){

    }
    public static Bitmap createBitmap(byte [] arrayImag){
        Bitmap bitmap=BitmapFactory.decodeByteArray(arrayImag,0,arrayImag.length);
        return bitmap;
    }
    public static byte[] createBityArray(Bitmap bitmap){
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 85, stream);
        byte[] byteArray = stream.toByteArray();
        bitmap.recycle();
        return byteArray;
    }

    public String writeImageOnFile(Bitmap bitmap, Context context){
        FileOutputStream stream=null;
        File directory=null;
        String imageName=null;
        File path=null;
      try {
          String timeStamp = new SimpleDateFormat("ddMMyyyy_HHmm").format(new Date());
          imageName="MI_"+ timeStamp +".jpg";
          directory=createDirecory(context);
           path=new File(directory,imageName);
          stream=new FileOutputStream(path);
          bitmap.compress(Bitmap.CompressFormat.PNG,90,stream);
      }catch (Exception ex){
          ex.printStackTrace();
      }finally {
          try {
              stream.close();
          }catch (Exception ex){
              ex.printStackTrace();
          }
      }
      return path.getAbsolutePath();
    }
    public String writeImageOnFile(File file, Context context){
        FileOutputStream stream=null;
        File directory=null;
        File path=null;
        try {
            directory=createDirecory(context);
        }catch (Exception ex){
            ex.printStackTrace();
        }finally {
            try {
                stream.close();
            }catch (Exception ex){
                ex.printStackTrace();
            }
        }
        return path.getAbsolutePath();
    }
    public File createDirecory(Context context){
        File directory=null;
        try { directory=context.getDir("Images",context.MODE_PRIVATE);
          if(!directory.exists()){
              directory.mkdirs();
          }
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return directory;
    }
}
