package com.Aya.bigslice.main.homescreens;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.material.button.MaterialButton;
import com.Aya.bigslice.App;
import com.Aya.bigslice.R;
import com.Aya.bigslice.database.DatabaseManager;
import com.Aya.bigslice.database.Order;
import com.Aya.bigslice.database.Product;
import com.Aya.bigslice.interfaces.Callback_Order;
import com.Aya.bigslice.utils.UtilsFunctions;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;
import java.util.Random;

public class ProductActivity extends AppCompatActivity {

    private TextView product_TV_title, product_TV_price, product_TV_size, product_TV_deliveryTime;
    private ImageView product_IMG_productImage;
    private MaterialButton product_BTN_order;
    private Product product;
    private DatabaseManager db;
    private LottieAnimationView product_Lottie_loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        product = (Product) getIntent().getSerializableExtra("product");
        db = new DatabaseManager();
        db.setCallback_Order(callback_order);
        findViews();
        initViews();

    }

    private Callback_Order callback_order = new Callback_Order() {
        @Override
        public void onOrderAddDone(boolean status, String msg) {
            if(status){
                Toast.makeText(ProductActivity.this, msg, Toast.LENGTH_SHORT).show();
                setResult(AppCompatActivity.RESULT_OK);
                finish();
            }else{
                Toast.makeText(ProductActivity.this, msg, Toast.LENGTH_SHORT).show();
            }
            product_Lottie_loading.setVisibility(View.INVISIBLE);
        }

        @Override
        public void onFetchCustomerOrdersDone(ArrayList<Order> orders) {

        }
    };

    private void initViews() {
        product_Lottie_loading.setVisibility(View.INVISIBLE);
        product_BTN_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                product_Lottie_loading.setVisibility(View.VISIBLE);
                Random rnd = new Random();
                SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                Date date = new Date();

                Order order = new Order()
                        .setOrderNumber(rnd.nextInt(100000) + 10000)
                        .setOrderDate(formatter.format(date))
                        .setProduct(product)
                        .setOrderStatus(0)
                        .setCustomerId(db.getCurrentUser().getUid());

                db.addOrder(ProductActivity.this, order);
            }
        });

        product_TV_title.setText(product.getName());
        product_TV_price.setText(product.getPrice() + " ₪");
        product_IMG_productImage.setImageResource(product.getImageId());
        product_TV_size.setText(product.getSize());
        product_TV_deliveryTime.setText(UtilsFunctions.convertMinToStringTime((product.getPrepareTimeInMin() + App.DELIVERY_TIME)));

    }

    private void findViews() {
        product_TV_title = findViewById(R.id.product_TV_title);
        product_TV_price = findViewById(R.id.product_TV_price);
        product_TV_size = findViewById(R.id.product_TV_size);
        product_TV_deliveryTime = findViewById(R.id.product_TV_deliveryTime);
        product_IMG_productImage = findViewById(R.id.product_IMG_productImage);
        product_BTN_order = findViewById(R.id.product_BTN_order);
        product_Lottie_loading = findViewById(R.id.product_Lottie_loading);
    }


}