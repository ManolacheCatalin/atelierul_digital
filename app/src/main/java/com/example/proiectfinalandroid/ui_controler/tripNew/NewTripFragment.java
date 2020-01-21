package com.example.proiectfinalandroid.ui_controler.tripNew;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.RatingBar;
import android.widget.SeekBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProviders;

import com.example.proiectfinalandroid.R;
import com.example.proiectfinalandroid.Util.BitmapSupport;
import com.example.proiectfinalandroid.Util.ViewModelFactory;
import com.example.proiectfinalandroid.ViewModel.GlobalViewModel;
import com.example.proiectfinalandroid.model.Trip;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class NewTripFragment extends Fragment implements View.OnClickListener, DatePickerDialog.OnDateSetListener, RadioGroup.OnCheckedChangeListener {

    private RatingBar ratingBar;
    private SeekBar bar;
    private int price = 0;
    private EditText start, end, title, destination;
    private Calendar calendar;
    private static int cod_GALLERY = 1;
    private static int cod_CAMERA = 2;
    private GlobalViewModel viewModel;
    private RadioGroup radioGroup;
    private String imagePath = "";
    private String type_trip = "";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        calendar = Calendar.getInstance();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_new, container, false);
        bar = view.findViewById(R.id.price_slider_id);
        radioGroup = view.findViewById(R.id.group_radio);
        Button camera = view.findViewById(R.id.bt_camera_id);
        Button gallery = view.findViewById(R.id.bt_gallery_id);
        Button save = view.findViewById(R.id.btn_save_trip_id);
        start = view.findViewById(R.id.Start_date_id);
        end = view.findViewById(R.id.End_date_id);
        title = view.findViewById(R.id.trip_name_id);
        destination = view.findViewById(R.id.destination_name_id);
        ratingBar = view.findViewById(R.id.rating_name_id);
        camera.setOnClickListener(this);
        gallery.setOnClickListener(this);
        save.setOnClickListener(this);
        start.setOnClickListener(this);
        end.setOnClickListener(this);
        radioGroup.setOnCheckedChangeListener(this);
        bar.setMax(10000);

        bar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                price = i;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                Toast.makeText(getActivity(),String.valueOf(price),Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = ViewModelProviders.of(this, new ViewModelFactory(this.getActivity().getApplication())).get(GlobalViewModel.class);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_camera_id:
                getFotoFromCamera();
                break;
            case R.id.bt_gallery_id:
                getFotoFromGallery();
                break;
            case R.id.btn_save_trip_id:
                saveTrip();
                break;
            case R.id.Start_date_id:
                showDilaog();
                setData(start);
                break;
            case R.id.End_date_id:
                showDilaog();
                setData(end);
                break;
        }
    }

    @Override
    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
        calendar.set(Calendar.YEAR, i);
        calendar.set(Calendar.MONTH, i1);
        calendar.set(Calendar.DAY_OF_MONTH, i2);
    }

    private void setData(EditText data) {
        String format = "MM/DD/yy";
        SimpleDateFormat dateFormat = new SimpleDateFormat(format, Locale.GERMAN);
        data.setText(dateFormat.format(calendar.getTime()));
    }

    private void showDilaog() {
        new DatePickerDialog(getContext(), this, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
    }

    private void getFotoFromGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, cod_GALLERY);
    }

    private void getFotoFromCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (intent.resolveActivity(getActivity().getPackageManager()) != null) {
            startActivityForResult(intent, cod_CAMERA);
        }
    }

    private void saveTrip() {
        Trip object = new Trip();
        object.setName_Trip(title.getText().toString());
        object.setDestination_Trip(destination.getText().toString());
        object.setRating((double) ratingBar.getRating());
        Log.d("DEBUG",String.valueOf((int)ratingBar.getRating()));
        object.setFavourite(false);
        object.setCost_trip(price);
        object.setPathImage(imagePath);
        object.setType(type_trip);
        object.setStart_date(start.getText().toString());
        object.setEnd_date(end.getText().toString());
        viewModel.insertTrip(object);
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        if (fragmentManager.getBackStackEntryCount() > 0) {
            fragmentManager.popBackStack();
        }
        getActivity().finish();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == cod_CAMERA && resultCode == Activity.RESULT_OK) {
            Bundle extras = data.getExtras();
            final Bitmap bitmap = (Bitmap) extras.get("data");
            imagePath = new BitmapSupport().writeImageOnFile(bitmap, getContext());
        }
        if (requestCode == cod_GALLERY && resultCode == Activity.RESULT_OK) {

            try {
                final Uri uri = data.getData();
                String[] filePathColumn = {MediaStore.Images.Media.DATA};
                Cursor cursor = getActivity().getContentResolver().query(uri, filePathColumn, null, null, null);
                ((Cursor) cursor).moveToFirst();
                int columnIndex = ((Cursor) cursor).getColumnIndex(filePathColumn[0]);
                String picturePath = ((Cursor) cursor).getString(columnIndex);
                ((Cursor) cursor).close();
                imagePath = picturePath;
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        getActivity().finish();
    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int i) {
        switch (i) {
            case R.id.radio_city:
                type_trip="City Break";
                break;
            case R.id.radio_mountains:
                type_trip="Mountains";
                break;
            case R.id.radio_seaSide:
                type_trip="Sea Side";
                break;
        }
    }
}
