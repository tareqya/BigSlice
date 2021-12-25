package com.tareqyassin.bigslice.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.tareqyassin.bigslice.R;


public class OrdersFragment extends Fragment {


    private AppCompatActivity activity;

    public void setActivity(AppCompatActivity activity) {
        this.activity = activity;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_orders, container, false);
        findViews(view);
        initViews();

        return view;
    }

    private void initViews() {

    }

    private void findViews(View view) {

    }


}