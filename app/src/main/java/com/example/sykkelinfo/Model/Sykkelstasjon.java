package com.example.sykkelinfo.Model;

public class Sykkelstasjon {
    public int num_bikes_available, num_docks_available;
    public String station_id, name;

//Konstruktører
    public Sykkelstasjon(String station_id, int num_bikes_available, int num_docks_available, String name) {
        this.station_id = station_id;
        this.num_bikes_available = num_bikes_available;
        this.num_docks_available = num_docks_available;
        this.name = name;
    }

    public Sykkelstasjon(String station_id, String name) {
        this.station_id = station_id;
        this.name = name;
    }

//Setters and getters
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
//toString
    @Override
    public String toString() {
        return "Sykkelstasjon{" +
                "num_bikes_available=" + num_bikes_available +
                ", num_docks_available=" + num_docks_available +
                ", station_id='" + station_id + '\'' +
                ", name='" + name + '\'' +
                '}';
    }


}
