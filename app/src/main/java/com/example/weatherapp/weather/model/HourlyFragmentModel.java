package com.example.weatherapp.weather.model;

public class HourlyFragmentModel implements  Comparable<HourlyFragmentModel>{
    private double dt;
    private int id;
    private double temp;
    private int TYPE;

    public HourlyFragmentModel() {
    }

    public HourlyFragmentModel(double dt, int id, double temp, int TYPE) {
        this.dt = dt;
        this.id = id;
        this.temp = temp;
        this.TYPE = TYPE;
    }

    public double getDt() {
        return dt;
    }

    public void setDt(double dt) {
        this.dt = dt;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getTemp() {
        return temp;
    }

    public void setTemp(double temp) {
        this.temp = temp;
    }

    public int getTYPE() {
        return TYPE;
    }

    public void setTYPE(int TYPE) {
        this.TYPE = TYPE;
    }

    @Override
    public int compareTo(HourlyFragmentModel o) {
        if(this.dt < o.getDt())
            return -1;
        else if(o.getDt() < this.dt)
            return 1;
        return 0;
    }
}
