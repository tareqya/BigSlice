package com.tareqyassin.bigslice.main.profilescreens;


import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;
import com.tareqyassin.bigslice.R;
import com.tareqyassin.bigslice.database.Customer;
import com.tareqyassin.bigslice.database.DatabaseManager;
import com.tareqyassin.bigslice.database.MyLocation;
import com.tareqyassin.bigslice.interfaces.CallBack_Profile;
import com.tareqyassin.bigslice.utils.Validator;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;

import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class UpdateLocationActivity extends AppCompatActivity {

    private MaterialButton updateLocation_BTN_currentLocation, updateLocation_BTN_updateLocation;
    private TextInputLayout updateLocation_TIL_street, updateLocation_TIL_city, updateLocation_TIL_country;
    private Validator.Builder city_builder, country_builder, street_builder;
    private DatabaseManager db;
    private Customer currentCustomer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_location);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        findViews();
        initViews();
        initVars();
        addValidation();

    }

    private void findViews(){
        updateLocation_BTN_currentLocation = findViewById(R.id.updateLocation_BTN_currentLocation);
        updateLocation_BTN_updateLocation = findViewById(R.id.updateLocation_BTN_updateLocation);
        updateLocation_TIL_street = findViewById(R.id.updateLocation_TIL_street);
        updateLocation_TIL_city = findViewById(R.id.updateLocation_TIL_city);
        updateLocation_TIL_country = findViewById(R.id.updateLocation_TIL_country);


    }

    private void initViews() {
        updateLocation_BTN_currentLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateCurrentAddress();
            }
        });


        updateLocation_BTN_updateLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(country_builder.getStatus() && city_builder.getStatus() && street_builder.getStatus()) {
                    String city = updateLocation_TIL_city.getEditText().getText().toString();
                    String country = updateLocation_TIL_country.getEditText().getText().toString();
                    String street = updateLocation_TIL_street.getEditText().getText().toString();

                    currentCustomer.setLocation(new MyLocation().setCity(city)
                            .setCountry(country)
                            .setStreet(street));
                    db.updateCustomer(currentCustomer);
                }
            }
        });
    }


    public void updateCurrentAddress() {

        Geocoder geoCoder = new Geocoder(this, Locale.getDefault()); //it is Geocoder
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        Location locationGPS = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        try {
            double latitude = locationGPS.getLatitude();
            double longitude = locationGPS.getLongitude();
            List<Address> address = geoCoder.getFromLocation(latitude, longitude, 1);


            String city = address.get(0).getLocality();
            String country = address.get(0).getCountryName();
            String street = address.get(0).getFeatureName();
            updateLocation_TIL_city.getEditText().setText(city);
            updateLocation_TIL_country.getEditText().setText(country);
            updateLocation_TIL_street.getEditText().setText(street);
        } catch (IOException e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        } catch (NullPointerException e) {
            Toast.makeText(this, "Please turn GPS", Toast.LENGTH_SHORT).show();
        }
    }


    public void initVars() {
        db = new DatabaseManager();
        db.setCallBack_Profile(callBack_profile);
        db.getCustomerById(db.getCurrentUser().getUid());
        try {
            if (ContextCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED ) {
                ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 101);
            }
        } catch (Exception e){
            e.printStackTrace();
        }

    }

    private CallBack_Profile callBack_profile = new CallBack_Profile() {
        @Override
        public void onFetchCustomerDataDone(Customer customer) {
            currentCustomer = customer;
            if(customer != null && customer.getLocation() != null){
                updateLocation_TIL_city.getEditText().setText(customer.getLocation().getCity());
                updateLocation_TIL_country.getEditText().setText(customer.getLocation().getCountry());
                updateLocation_TIL_street.getEditText().setText(customer.getLocation().getStreet());
            }
        }

        @Override
        public void onImageUploadDone(boolean status, String msg, String imgName) {

        }

        @Override
        public void updateCustomerDone(boolean status, String msg) {

            if(status){
                Toast.makeText(UpdateLocationActivity.this, "Update success", Toast.LENGTH_SHORT).show();
                finish();
            }else{
                Toast.makeText(UpdateLocationActivity.this, msg, Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public void onImageDownloadUriDone(boolean status, String msg, String downloadUrl) {

        }
    };


    private void addValidation() {
        city_builder = Validator.Builder
                .make(updateLocation_TIL_city)
                .addWatcher(new Validator.Watcher_NotEmpty("City can't be empty"))
                .build();

        country_builder = Validator.Builder
                .make(updateLocation_TIL_country)
                .addWatcher(new Validator.Watcher_NotEmpty("Country can't be empty"))
                .build();


        street_builder = Validator.Builder
                .make(updateLocation_TIL_street)
                .addWatcher(new Validator.Watcher_NotEmpty("Street address can't be empty"))
                .build();
    }

}