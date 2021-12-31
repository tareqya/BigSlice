package com.tareqyassin.bigslice;

import android.app.Application;

import androidx.appcompat.app.AppCompatDelegate;

import com.tareqyassin.bigslice.database.Category;
import com.tareqyassin.bigslice.database.Product;

import java.util.ArrayList;

public class App extends Application {
    public static final int DELIVERY_TIME = 15; // min
    @Override
    public void onCreate() {
        super.onCreate();
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

    }

    public static  ArrayList<Category> generateCategoriesData(){
        ArrayList<Category> categories = new ArrayList<>();
        categories.add(new Category().setName("Pizza").setImageId(R.drawable.pizza_icon).setSelected(true));
        categories.get(0).addProduct(new Product()
                .setId(1)
                .setName("Neapolitan Pizza")
                .setImageId(R.drawable.pizza1)
                .setPrice(79.9)
                .setRate(4.3)
                .setSize("Medium 14\"")
                .setPrepareTimeInMin(15));

        categories.get(0).addProduct(new Product()
                .setId(2)
                .setName("Detroit Pizza")
                .setImageId(R.drawable.pizza2)
                .setPrice(60)
                .setRate(3.5)
                .setSize("Large 16\"")
                .setPrepareTimeInMin(20));

        categories.get(0).addProduct(new Product()
                .setId(3)
                .setName("New York-Style Pizza")
                .setImageId(R.drawable.pizza3)
                .setSize("Family size 18\"")
                .setPrepareTimeInMin(20)
                .setPrice(85)
                .setRate(5));

        categories.get(0).addProduct(new Product()
                .setId(4)
                .setName("Crustless Pizza")
                .setImageId(R.drawable.pizza4)
                .setPrice(80)
                .setRate(4.2)
                .setSize("Medium 14\"")
                .setPrepareTimeInMin(10));

        categories.add(new Category().setName("Seafood").setImageId(R.drawable.shrimp_icon));

        categories.get(1).addProduct(new Product()
                .setId(5)
                .setName("Fish")
                .setImageId(R.drawable.seafood3)
                .setPrice(120)
                .setRate(4.7)
                .setSize("250 gr")
                .setPrepareTimeInMin(30));

        categories.get(1).addProduct(new Product()
                .setId(6)
                .setName("Salmon")
                .setImageId(R.drawable.seafood4)
                .setPrice(100)
                .setRate(4.0)
                .setSize("200 gr")
                .setPrepareTimeInMin(25));


        categories.add(new Category().setName("Soft Drink").setImageId(R.drawable.soda_icon));
        categories.get(2).addProduct(new Product()
                .setId(8)
                .setName("7 Up")
                .setImageId(R.drawable.softdrink1)
                .setPrice(5)
                .setRate(5.0)
                .setSize("330 m"));

        categories.get(2).addProduct(new Product()
                .setId(9)
                .setName("Coa Cola")
                .setImageId(R.drawable.softdrink2)
                .setPrice(5)
                .setRate(5.0)
                .setSize("330 m"));

        categories.get(2).addProduct(new Product()
                .setId(10)
                .setName("Pepsi")
                .setImageId(R.drawable.softdrink3)
                .setPrice(5)
                .setRate(5.0)
                .setSize("330 m"));

        categories.get(2).addProduct(new Product()
                .setId(11)
                .setName("FANTA")
                .setImageId(R.drawable.softdrink4)
                .setPrice(5)
                .setRate(5.0)
                .setSize("330 m"));

        return categories;

    }
}
