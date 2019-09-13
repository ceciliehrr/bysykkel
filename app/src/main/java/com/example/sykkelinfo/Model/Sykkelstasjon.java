package com.example.sykkelinfo.Model;

import android.os.Parcel;
import android.os.Parcelable;


public class Sykkelstasjon implements Parcelable {
    public int num_bikes_available, num_docks_available;
    public double lat, lon;
    public String station_id, name;

//Konstruktører
    public Sykkelstasjon(String station_id, int num_bikes_available, int num_docks_available, String name) {
        this.station_id = station_id;
        this.num_bikes_available = num_bikes_available;
        this.num_docks_available = num_docks_available;
        this.name = name;
    }

    public Sykkelstasjon(String station_id, String name, double lat, double lon) {
        this.station_id = station_id;
        this.name = name;
        this.lat = lat;
        this.lon = lon;
    }

    public Sykkelstasjon(String name) {
        this.name = name;
    }
//Setters and getters

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    public String getStation_id() {
        return station_id;
    }

    public void setStation_id(String station_id) {
        this.station_id = station_id;
    }

    public int getNum_bikes_available() {
        return num_bikes_available;
    }

    public void setNum_bikes_available(int num_bikes_available) {
        this.num_bikes_available = num_bikes_available;
    }

    public int getNum_docks_available() {
        return num_docks_available;
    }

    public void setNum_docks_available(int num_docks_available) {
        this.num_docks_available = num_docks_available;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Sykkelstasjon{" +
                "num_bikes_available=" + num_bikes_available +
                ", num_docks_available=" + num_docks_available +
                ", lat=" + lat +
                ", lon=" + lon +
                ", station_id='" + station_id + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
    public Sykkelstasjon(Parcel in) {
        super();
        readFromParcel(in);
    }

    public static final Parcelable.Creator<Sykkelstasjon> CREATOR = new Parcelable.Creator<Sykkelstasjon>() {
        public Sykkelstasjon createFromParcel(Parcel in) {
            return new Sykkelstasjon(in);
        }
        public Sykkelstasjon[] newArray(int size) {
            return new Sykkelstasjon[size];
        }
    };

    public void readFromParcel(Parcel in) {
        num_bikes_available = in.readInt();
        num_docks_available = in.readInt();
        lat = in.readDouble();
        lon = in.readDouble();
        station_id = in.readString();
        name = in.readString();

    }
    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(num_bikes_available);
        dest.writeInt(num_docks_available);
        dest.writeDouble(lat);
        dest.writeDouble(lon);
        dest.writeString(station_id);
        dest.writeString(name);
    }

}
