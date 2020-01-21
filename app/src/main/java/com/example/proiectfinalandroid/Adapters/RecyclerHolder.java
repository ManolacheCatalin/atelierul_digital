package com.example.proiectfinalandroid.Adapters;

import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.proiectfinalandroid.R;
import com.example.proiectfinalandroid.Util.ItemClick;
import com.example.proiectfinalandroid.model.Trip;

public class RecyclerHolder extends RecyclerView.ViewHolder implements View.OnLongClickListener {
    private TextView title;
    private TextView body;
    private TextView cost;
    private ImageView media;
    private ImageView favorite;
    private RatingBar bar;
    public RecyclerHolder(@NonNull View itemView) {
        super(itemView);
        title=itemView.findViewById(R.id.title_trip);
        body=itemView.findViewById(R.id.destination_trip);
        media=itemView.findViewById(R.id.media_back);
        favorite=itemView.findViewById(R.id.preference_id);
        cost=itemView.findViewById(R.id.cost_trip);
        bar=itemView.findViewById(R.id.rating_id);
    }

    public ImageView getMedia() {
        return media;
    }

    public TextView getBody() {
        return body;
    }

    public TextView getTitle() {
        return title;
    }

    public ImageView getFavorite() {
        return favorite;
    }

    public RatingBar getBar() {
        return bar;
    }

    public TextView getCost() {
        return cost;
    }

    @Override
    public boolean onLongClick(View view) {
        return false;
    }
}
