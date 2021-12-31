package com.tareqyassin.bigslice.database;

import java.io.Serializable;

public class Product implements Serializable {
    private int id;
    private String name;
    private double price;
    private int imageId;
    private double rate;
    private String size;
    private int prepareTimeInMin;


    public Product(){
        this.prepareTimeInMin = 0;
    }

    public Product(int id, String name, double price, int imageId, double rate) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.imageId = imageId;
        this.rate = rate;
        this.prepareTimeInMin = 0;
    }

    public int getId() {
        return id;
    }

    public Product setId(int id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public Product setName(String name) {
        this.name = name;
        return this;
    }

    public double getPrice() {
        return price;
    }

    public Product setPrice(double price) {
        this.price = price;
        return this;
    }

    public int getImageId() {
        return imageId;
    }

    public Product setImageId(int imageId) {
        this.imageId = imageId;
        return this;
    }

    public double getRate() {
        return rate;
    }

    public Product setRate(double rate) {
        this.rate = rate;
        return this;
    }

    public String getSize() {
        return size;
    }

    public Product setSize(String size) {
        this.size = size;
        return this;
    }

    public int getPrepareTimeInMin() {
        return prepareTimeInMin;
    }

    public Product setPrepareTimeInMin(int prepareTimeInMin) {
        this.prepareTimeInMin = prepareTimeInMin;
        return this;
    }
}
