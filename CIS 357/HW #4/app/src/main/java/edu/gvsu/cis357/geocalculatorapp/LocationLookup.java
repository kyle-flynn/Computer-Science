package edu.gvsu.cis357.geocalculatorapp;

import org.parceler.Parcel;

/**
 * Created by daniel on 11/14/17.
 */
@Parcel
public class LocationLookup {
    String _key;
    String timestamp;
    double origLat;
    double origLng;
    double endLat;
    double endLng;


    public String get_timestamp() {
        return timestamp;
    }

    public void set_timestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public double getOrigLat() {
        return origLat;
    }

    public void setOrigLat(double origLat) {
        this.origLat = origLat;
    }

    public double getOrigLng() {
        return origLng;
    }

    public void setOrigLng(double origLng) {
        this.origLng = origLng;
    }

    public double getEndLat() {
        return endLat;
    }

    public void setEndLat(double endLat) {
        this.endLat = endLat;
    }

    public double getEndLng() {
        return endLng;
    }

    public void setEndLng(double endLng) {
        this.endLng = endLng;
    }
}
