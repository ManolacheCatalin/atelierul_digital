package com.example.proiectfinalandroid.ui_controler.tripEdit;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.LiveData;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RatingBar;
import android.widget.SeekBar;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.proiectfinalandroid.R;
import com.example.proiectfinalandroid.Util.BitmapSupport;
import com.example.proiectfinalandroid.Util.ViewModelFactory;
import com.example.proiectfinalandroid.ViewModel.GlobalViewModel;
import com.example.proiectfinalandroid.model.Trip;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class EditFragment extends Fragment implements View.OnClickListener, DatePickerDialog.OnDateSetListener, RadioGroup.OnCheckedChangeListener {


    private static int cod_GALLERY = 1;
    private static int cod_CAMERA = 2;
    private final int id_trip;
    private EditText title, destination, start, end;
    private RatingBar bar;
    private SeekBar seekBar;
    private Button btn_save, btn_gallery, btn_camera;
    private GlobalViewModel viewModel;
    private String imagePath, type_trip;
    private int newPrice = 0;
    private Calendar calendar;
    private Trip tripObject;
    private RadioGroup radioGroup;

    public EditFragment(final int id_trip) {
        this.id_trip = id_trip;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        calendar = Calendar.getInstance();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edit, container, false);
        title = view.findViewById(R.id.title_edit_id);
        destination = view.findViewById(R.id.destination_edit_id);
        seekBar = view.findViewById(R.id.price_slider_edit_id);
        btn_save = view.findViewById(R.id.btn_save_edit_trip_id);
        btn_camera = view.findViewById(R.id.btn_edit_camera_id);
        start = view.findViewById(R.id.Start_edit_id);
        end = view.findViewById(R.id.End_edit_id);
        btn_gallery = view.findViewById(R.id.btn_edit_gallery_id);
        bar = view.findViewById(R.id.rating_edit_id);
        radioGroup=view.findViewById(R.id.group_radio_edit);
        btn_save.setOnClickListener(this);
        btn_gallery.setOnClickListener(this);
        btn_camera.setOnClickListener(this);
        start.setOnClickListener(this);
        end.setOnClickListener(this);
        radioGroup.setOnCheckedChangeListener(this);
        seekBar.setMax(10000);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                newPrice = i;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
             Toast.makeText(getActivity(),String.valueOf(newPrice),Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = ViewModelProviders.of(this, new ViewModelFactory(this.getActivity().getApplication())).get(GlobalViewModel.class);
        UpdateUi(viewModel.getSingleTrip(id_trip),view);
    }

    private void UpdateUi(LiveData<Trip> trip, View view) {
        trip.observe(getViewLifecycleOwner(), new Observer<Trip>() {
            @Override
            public void onChanged(Trip trip) {
                tripObject=trip;
                Log.i("DEBUG_EDIT", "CALL ONCHANGED");
                if (trip != null) {
                    title.setText(trip.getName_Trip());
                    destination.setText(trip.getDestination_Trip());
                    seekBar.setProgress(trip.getCost_trip());
                    bar.setRating((float) trip.getRating());
                    start.setText(trip.getStart_date());
                    end.setText(trip.getEnd_date());
                    if (trip.getType()=="City Break") {
                        RadioButton button = (RadioButton) view.findViewById(R.id.radio_edit_city);
                        button.setChecked(true);
                    } else if (trip.getType()=="Mountains") {
                        RadioButton button = (RadioButton) view.findViewById(R.id.radio_edit_mountains);
                        button.setChecked(true);
                    } else {
                        RadioButton button = (RadioButton) view.findViewById(R.id.radio_edit_seaSide);
                        button.setChecked(true);
                    }
                }
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_edit_camera_id:
                getFotoFromCamera();
                break;
            case R.id.btn_edit_gallery_id:
                getFotoFromGallery();
                break;
            case R.id.btn_save_edit_trip_id:
                updateTrip(tripObject);
                break;
            case R.id.Start_edit_id:
                showDilaog();
                setData(start);
                break;
            case R.id.End_edit_id:
                showDilaog();
                setData(end);
                break;
        }
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

    private void updateTrip(Trip trip) {
        Trip newTrip = new Trip();
        newTrip.setId_trip(trip.getId_trip());
        if (imagePath == null) {
            newTrip.setPathImage(trip.getPathImage());
        } else {
            newTrip.setPathImage(imagePath);
        }
        newTrip.setCost_trip(newPrice);
        newTrip.setName_Trip(title.getText().toString());
        newTrip.setDestination_Trip(destination.getText().toString());
        newTrip.setRating(bar.getNumStars());
        newTrip.setEnd_date(end.getText().toString());
        newTrip.setStart_date(start.getText().toString());
        newTrip.setType(type_trip);
        viewModel.insertUpdatedTrip(newTrip);
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
    public void onStop() {
        super.onStop();

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

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int i) {
        switch (i) {
            case R.id.radio_edit_city:
                type_trip = "City Break";
                Log.d("DEBUG","CITY BUTTON PRESSED"+type_trip.toString());
                break;
            case R.id.radio_edit_mountains:
                type_trip = "Mountains";
                Log.d("DEBUG","MOUNTIANS BUTTON PRESSED"+type_trip.toString());
                break;
            case R.id.radio_edit_seaSide:
                type_trip = "Sea Side";
                Log.d("DEBUG","SEA SIDE BUTTON PRESSED"+type_trip.toString());
                break;
        }
    }
}
