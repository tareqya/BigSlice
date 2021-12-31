package com.tareqyassin.bigslice.database;

import java.util.ArrayList;

public class Category {
    private String name;
    private int imageId;
    private ArrayList<Product> products;
    private boolean selected;

    public Category(String name){
        this.name = name;
        this.products = new ArrayList<>();
        this.selected = false;
    }

    public Category(){
        this.products = new ArrayList<>();
        this.selected = false;
    }

    public void addProduct(Product p){
        this.products.add(p);
    }

    public String getName() {
        return name;
    }

    public Category setName(String name) {
        this.name = name;
        return this;
    }

    public int getImageId() {
        return imageId;
    }

    public Category setImageId(int imageId) {
        this.imageId = imageId;
        return this;
    }

    public ArrayList<Product> getProducts() {
        return products;
    }

    public Category setProducts(ArrayList<Product> products) {
        this.products = products;
        return this;
    }

    public boolean isSelected() {
        return selected;
    }

    public Category setSelected(boolean selected) {
        this.selected = selected;
        return this;
    }
}
