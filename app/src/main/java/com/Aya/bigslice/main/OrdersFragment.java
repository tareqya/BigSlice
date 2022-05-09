package com.Aya.bigslice.main;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.Aya.bigslice.R;
import com.Aya.bigslice.adapters.OrderAdapter;
import com.Aya.bigslice.database.DatabaseManager;
import com.Aya.bigslice.database.Order;
import com.Aya.bigslice.interfaces.Callback_Order;
import com.Aya.bigslice.interfaces.OrderClickedListener;
import com.Aya.bigslice.utils.DateComparator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;


public class OrdersFragment extends Fragment {


    private AppCompatActivity activity;
    private DatabaseManager db;
    private RecyclerView productFrg_RV_products;
    private LottieAnimationView productFrg_Lottie_loading;

    public void setActivity(AppCompatActivity activity) {
        this.activity = activity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_orders, container, false);
        initVars();
        findViews(view);
        initViews();
        db.getCustomerOrders(db.getCurrentUser().getUid());
        return view;
    }

    private void initVars() {
        db = new DatabaseManager();
        db.setCallback_Order(new Callback_Order() {
            @Override
            public void onOrderAddDone(boolean status, String msg) {

            }

            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onFetchCustomerOrdersDone(ArrayList<Order> orders) {
                if(orders.size() != 0){
                    productFrg_Lottie_loading.setVisibility(View.INVISIBLE);
                }

                orders.sort(new DateComparator());
                Collections.reverse(orders);
                OrderAdapter orderAdapter = new OrderAdapter(activity, orders);

                productFrg_RV_products.setLayoutManager(new LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false));
                productFrg_RV_products.setHasFixedSize(true);
                productFrg_RV_products.setItemAnimator(new DefaultItemAnimator());
                productFrg_RV_products.setAdapter(orderAdapter);

                orderAdapter.setOrderClickedListener(new OrderClickedListener() {
                    @Override
                    public void OrderItemClicked(Order order, int position) {
                        Objects.requireNonNull(productFrg_RV_products.getAdapter()).notifyItemChanged(position);
                    }
                });
            }
        });
    }

    private void initViews() {

    }

    private void findViews(View view) {
        productFrg_RV_products = view.findViewById(R.id.productFrg_RV_products);
        productFrg_Lottie_loading = view.findViewById(R.id.productFrg_Lottie_loading);
    }


}