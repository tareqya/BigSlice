package com.tareqyassin.bigslice.main;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.tareqyassin.bigslice.R;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

public class MainActivity extends AppCompatActivity {
    private HomeFragment homeFragment;
    private ProfileFragment profileFragment;
    private OrdersFragment ordersFragment;
    private BottomNavigationView bottom_navigation;

    private FrameLayout homeFrame, profileFrame, ordersFrame;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
        getSupportFragmentManager().beginTransaction().add(R.id.home_frame_home, homeFragment).commit();

        profileFragment = new ProfileFragment();
        profileFragment.setActivity(this);
        getSupportFragmentManager().beginTransaction().add(R.id.home_frame_profile, profileFragment).commit();
        profileFrame.setVisibility(View.INVISIBLE);

        ordersFragment = new OrdersFragment();
        ordersFragment.setActivity(this);
        getSupportFragmentManager().beginTransaction().add(R.id.home_frame_orders, ordersFragment).commit();
        ordersFrame.setVisibility(View.INVISIBLE);


    }



}