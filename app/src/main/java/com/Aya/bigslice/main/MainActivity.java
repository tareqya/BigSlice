package com.Aya.bigslice.main;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.Aya.bigslice.R;
import com.Aya.bigslice.database.MSPV;
import com.Aya.bigslice.interfaces.CallBack_Main;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

public class MainActivity extends AppCompatActivity {
    private HomeFragment homeFragment;
    private ProfileFragment profileFragment;
    private OrdersFragment ordersFragment;
    private BottomNavigationView bottom_navigation;
    public static final String ORDERS_NUM = "ORDERS_NUM";
    private int count = 0;
    private MSPV localDB;

    private FrameLayout homeFrame, profileFrame, ordersFrame;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        localDB = MSPV.getMe();
        count = localDB.getInt(ORDERS_NUM, 0);
        findViews();
        initViews();



        bottom_navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.menu_home:
                        homeFrame.setVisibility(View.VISIBLE);
                        ordersFrame.setVisibility(View.INVISIBLE);
                        profileFrame.setVisibility(View.INVISIBLE);

                        break;
                    case R.id.menu_orders:
                        count = 0;
                        localDB.putInt(ORDERS_NUM, count);
                        hideOrderBadge();
                        homeFrame.setVisibility(View.INVISIBLE);
                        ordersFrame.setVisibility(View.VISIBLE);
                        profileFrame.setVisibility(View.INVISIBLE);
                        break;
                    case R.id.menu_profile:
                        homeFrame.setVisibility(View.INVISIBLE);
                        ordersFrame.setVisibility(View.INVISIBLE);
                        profileFrame.setVisibility(View.VISIBLE);
                        break;
                }
                return true;
            }
        });
    }

    public void findViews(){
        bottom_navigation = findViewById(R.id.bottom_navigation);
        homeFrame = findViewById(R.id.home_frame_home);
        ordersFrame = findViewById(R.id.home_frame_orders);
        profileFrame = findViewById(R.id.home_frame_profile);

    }

    public void initViews(){
        homeFragment = new HomeFragment();
        homeFragment.setActivity(this);
        homeFragment.setCallBack_main(new CallBack_Main() {
            @Override
            public void onOrderPlaced() {
                count++;
                showOrderBadge(count);
                localDB.putInt(ORDERS_NUM, count);
            }
        });
        getSupportFragmentManager().beginTransaction().add(R.id.home_frame_home, homeFragment).commit();

        profileFragment = new ProfileFragment();
        profileFragment.setActivity(this);
        getSupportFragmentManager().beginTransaction().add(R.id.home_frame_profile, profileFragment).commit();
        profileFrame.setVisibility(View.INVISIBLE);

        ordersFragment = new OrdersFragment();
        ordersFragment.setActivity(this);
        getSupportFragmentManager().beginTransaction().add(R.id.home_frame_orders, ordersFragment).commit();
        ordersFrame.setVisibility(View.INVISIBLE);

        if(count > 0){
            showOrderBadge(count);
        }else{
            hideOrderBadge();
        }

    }


    public void showOrderBadge(int number){
        BadgeDrawable badge = bottom_navigation.getOrCreateBadge(R.id.menu_orders);
        badge.setVisible(true);
        badge.setNumber(number);
    }

    public void hideOrderBadge(){
        BadgeDrawable badge = bottom_navigation.getOrCreateBadge(R.id.menu_orders);
        badge.setVisible(false);
    }


}