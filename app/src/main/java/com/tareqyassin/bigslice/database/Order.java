package com.tareqyassin.bigslice.database;

import com.google.firebase.database.Exclude;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Order{
    private int orderNumber;
    private String orderDate;
    private Product product;
    private int orderStatus;
    private String customerId;
    private boolean extend;

    public Order(int orderNumber, String orderDate, Product product, int orderStatus, String customerId, boolean extend) {
        this.orderNumber = orderNumber;
        this.orderDate = orderDate;
        this.product = product;
        this.orderStatus = orderStatus;
        this.customerId = customerId;
        this.extend = extend;
    }

    public Order(){
        this.extend = false;
    }

    public int getOrderNumber() {
        return orderNumber;
    }

    public Order setOrderNumber(int orderNumber) {
        this.orderNumber = orderNumber;
        return this;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public Order setOrderDate(String orderDate) {
        this.orderDate = orderDate;
        return this;
    }

    public Product getProduct() {
        return product;
    }

    public Order setProduct(Product product) {
        this.product = product;
        return this;
    }

    public int getOrderStatus() {
        return orderStatus;
    }

    public Order setOrderStatus(int orderStatus) {
        this.orderStatus = orderStatus;
        return this;
    }

    public String getCustomerId() {
        return customerId;
    }

    public Order setCustomerId(String customerId) {
        this.customerId = customerId;
        return this;
    }

    @Exclude
    public boolean isExtend() {
        return extend;
    }

    public Order setExtend(boolean extend) {
        this.extend = extend;
        return this;
    }


    public Date convertToDate(){
        try {
            return new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").parse(this.orderDate); //01/01/2022 11:46:57
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

}
