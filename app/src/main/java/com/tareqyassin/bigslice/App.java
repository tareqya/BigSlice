package com.tareqyassin.bigslice;

import android.app.Application;

import com.tareqyassin.bigslice.database.DatabaseManager;

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        DatabaseManager.DatabaseInit();

    }
}
