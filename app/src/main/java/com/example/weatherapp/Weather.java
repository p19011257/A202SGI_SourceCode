package com.example.weatherapp;

public class Weather {
    String citysName;
    String temparature;
    String feeling;

    public Weather(){
    }
    public Weather(String citysName, String temparature,  String feeling) {

        this.citysName = citysName;
        this.temparature = temparature;
        this.feeling = feeling;
    }

    public String getCitysName() {

        return citysName;
    }
    public String getTemparature() {

        return temparature;
    }
    public String getFeeling() {

        return feeling;
    }


}
