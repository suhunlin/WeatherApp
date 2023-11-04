package com.suhun.weatherapp;

public class WeatherInfo {
    private String city;
    private String time;
    private String weather;

    public void setCity(String city){
        this.city = city;
    }

    public String getCity(){
        return this.city;
    }

    public void setTime(String time){
        this.time = time;
    }

    public String getTime(){
        return this.time;
    }

    public void setWeather(String weather){
        this.weather = weather;
    }

    public String getWeather(){
        return this.weather;
    }
}
