package com.example.proiectfinalandroid.model;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.ColumnInfo;
import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.File;
@Entity(tableName = "Trips")
public class Trip {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id_trip")
    private int id_trip = 0;
    @ColumnInfo(name = "name_Trip")
    private String name_Trip = "";
    @ColumnInfo(name = "destination_Trip")
    private String destination_Trip = "";
    @ColumnInfo(name = "cost_trip")
    private int cost_trip = 0;
    @ColumnInfo(name = "favourite")
    private boolean favourite = false;
    @ColumnInfo(name = "rating")
    private double rating=0;
    @ColumnInfo(name = "pathImage")
    private String pathImage;
    @ColumnInfo(name = "type trip")
    private String type;
    @ColumnInfo(name = "start_date")
    private String start_date;
    @ColumnInfo(name = "end_date")
    private String end_date;
    public Trip(){

    }

    public Trip(Trip object) {
        this.id_trip = object.getId_trip();
        this.cost_trip = object.getCost_trip();
        this.destination_Trip = object.getDestination_Trip();
        this.favourite = object.isFavourite();
        this.name_Trip = object.getName_Trip();
        this.rating=object.getRating();
        this.pathImage=object.getPathImage();
        this.type=object.getType();
        this.start_date=object.getStart_date();
        this.end_date=object.getEnd_date();

    }

    public String getEnd_date() {
        return end_date;
    }

    public String getStart_date() {
        return start_date;
    }

    public void setEnd_date(String end_date) {
        this.end_date = end_date;
    }

    public void setStart_date(String start_date) {
        this.start_date = start_date;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setPathImage(String pathImage) {
        this.pathImage = pathImage;
    }

    public String getPathImage() {
        return pathImage;
    }

    public int getCost_trip() {
        return cost_trip;
    }

    public int getId_trip() {
        return id_trip;
    }

    public String getDestination_Trip() {
        return destination_Trip;
    }

    public String getName_Trip() {
        return name_Trip;
    }

    public boolean isFavourite() {
        return favourite;
    }

    public double getRating() {
        return rating;
    }


    public void setCost_trip(int cost_trip) {
        this.cost_trip = cost_trip;
    }

    public void setDestination_Trip(String destination_Trip) {
        this.destination_Trip = destination_Trip;
    }

    public void setFavourite(boolean favourite) {
        this.favourite = favourite;
    }

    public void setId_trip(int id_trip) {
        this.id_trip = id_trip;
    }

    public void setName_Trip(String name_Trip) {
        this.name_Trip = name_Trip;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }
}
