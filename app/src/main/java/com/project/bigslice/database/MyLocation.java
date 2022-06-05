package com.project.bigslice.database;

public class MyLocation {

    private String city;
    private String country;
    private String street;

    public MyLocation(String city, String country, String street) {
        this.city = city;
        this.country = country;
        this.street = street;
    }

    public MyLocation(){
        this.city = "";
        this.country = "";
        this.street = "";
    }



    public String getCity() {
        return city;
    }

    public MyLocation setCity(String city) {
        this.city = city;
        return this;
    }

    public String getCountry() {
        return country;
    }

    public MyLocation setCountry(String country) {
        this.country = country;
        return this;
    }

    public String getStreet() {
        return street;
    }

    public MyLocation setStreet(String street) {
        this.street = street;
        return this;
    }
}
