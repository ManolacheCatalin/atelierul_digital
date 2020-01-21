package com.example.proiectfinalandroid.Adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.renderscript.ScriptGroup;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.proiectfinalandroid.Activity.TripActivity;
import com.example.proiectfinalandroid.R;
import com.example.proiectfinalandroid.Util.BitmapSupport;
import com.example.proiectfinalandroid.Util.ItemClick;
import com.example.proiectfinalandroid.model.Trip;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.net.URL;
import java.util.BitSet;
import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerHolder> {
    ItemClick Callback;
    private List<Trip> tripList;
    private Context context;

    public RecyclerAdapter(ItemClick callback) {
        this.Callback=callback;
        setHasStableIds(true);
    }

    public void setTripList(final List<Trip> list, Context context) {
        this.tripList = list;
        this.context = context;
        notifyDataSetChanged();
        if(tripList==null){
            Log.d("DEBUG","TRIP LIST IN ADAPTER IS NULL");
        }
    }

    @NonNull
    @Override
    public RecyclerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View v = layoutInflater.inflate(R.layout.item_home, parent, false);
        return new RecyclerHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerHolder holder, int position) {
        holder.getTitle().setText(tripList.get(position).getName_Trip());
        holder.getBody().setText(tripList.get(position).getDestination_Trip());
        holder.getCost().setText(String.valueOf(tripList.get(position).getCost_trip()) + " " + "RON");
        holder.getBar().setRating((float) tripList.get(position).getRating());
        Log.d("DEBUG",String.valueOf(tripList.get(position).getRating()));
        holder.getBar().setIsIndicator(true);
        Glide.with(context).asBitmap().load(tripList.get(position).getPathImage()).placeholder(R.drawable.paris_back).into(holder.getMedia());
        if(tripList.get(position).isFavourite()){
            holder.getFavorite().setImageDrawable(context.getResources().getDrawable(R.drawable.ic_favorite_item));
        }else{
            holder.getFavorite().setImageDrawable(context.getResources().getDrawable(R.drawable.ic_favorite_black));
        }
        holder.getFavorite().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               if(tripList.get(position).isFavourite()){
                   tripList.get(position).setFavourite(false);
               }else{
                    tripList.get(position).setFavourite(true);
               }
               Callback.click(tripList.get(position));
            }
        });
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context,TripActivity.class);
                intent.putExtra("ViewTrip",tripList.get(position).getId_trip());
                context.startActivity(intent);

            }
        });
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                Intent intent=new Intent(context,TripActivity.class);
                intent.putExtra("EditTrip",tripList.get(position).getId_trip());
                context.startActivity(intent);
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        if (tripList == null) return 0;
        else return tripList.size();
    }

}
