package com.tareqyassin.bigslice;

import android.app.Application;

import androidx.appcompat.app.AppCompatDelegate;

import com.tareqyassin.bigslice.database.DatabaseManager;

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

    }
}
