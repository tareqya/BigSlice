package com.tareqyassin.bigslice.main.homescreens;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;
import com.tareqyassin.bigslice.App;
import com.tareqyassin.bigslice.R;
import com.tareqyassin.bigslice.database.Product;
import com.tareqyassin.bigslice.utils.UtilsFunctions;

import java.util.Objects;

public class ProductActivity extends AppCompatActivity {

    private TextView product_TV_title, product_TV_price, product_TV_size, product_TV_deliveryTime;
    private ImageView product_IMG_productImage;
    private MaterialButton product_BTN_order;
    private Product product;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        product = (Product) getIntent().getSerializableExtra("product");
        findViews();
        initViews();

    }

    private void initViews() {
        product_BTN_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

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
    }


}