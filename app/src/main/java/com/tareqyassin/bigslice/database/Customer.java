package com.tareqyassin.bigslice.database;

public class Customer {
    private String id;
    private String full_name;
    private String phone;
    private String email;
    private String imgName;

    public Customer(String id, String full_name, String phone, String email, String imgName) {
        this.id = id;
        this.full_name = full_name;
        this.phone = phone;
        this.email = email;
        this.imgName = imgName;

    }

    public Customer(){

    }

    public String getId() {
        return id;
    }

    public Customer setId(String id) {
        this.id = id;
        return this;
    }

    public String getFull_name() {
        return full_name;
    }

    public Customer setFull_name(String full_name) {
        this.full_name = full_name;
        return this;
    }

    public String getPhone() {
        if(phone == null)
            phone = "";
        return phone;
    }

    public Customer setPhone(String phone) {
        this.phone = phone;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public Customer setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getImgName() {
        if(imgName == null)
            imgName = "";
        return imgName;
    }

    public Customer setImgName(String imgName) {
        this.imgName = imgName;
        return this;
    }
}
