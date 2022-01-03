package com.tareqyassin.bigslice.main;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.tareqyassin.bigslice.App;
import com.tareqyassin.bigslice.R;
import com.tareqyassin.bigslice.adapters.CategoryAdapter;
import com.tareqyassin.bigslice.adapters.MenuAdapter;
import com.tareqyassin.bigslice.database.Category;
import com.tareqyassin.bigslice.database.Product;
import com.tareqyassin.bigslice.interfaces.CallBack_Main;
import com.tareqyassin.bigslice.interfaces.CategoryClickedListener;
import com.tareqyassin.bigslice.interfaces.MenuClickedListener;
import com.tareqyassin.bigslice.main.homescreens.ProductActivity;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Objects;


public class HomeFragment extends Fragment {


    private AppCompatActivity activity;
    private ArrayList<Category> categories;
    private RecyclerView home_RV_categories;
    private RecyclerView home_RV_menu;
    private CategoryAdapter adapter_score;
    private MenuAdapter menuAdapter;
    private CallBack_Main callBack_main;

    public void setActivity(AppCompatActivity activity) {
        this.activity = activity;
    }

    public void setCallBack_main (CallBack_Main callBack_main){
        this.callBack_main = callBack_main;
    }
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        initVars();
        findViews(view);
        initViews();

        return view;
    }

    private void initViews() {
        adapter_score = new CategoryAdapter(activity, categories);
        home_RV_categories.setLayoutManager(new LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false));
        home_RV_categories.setHasFixedSize(true);
        home_RV_categories.setItemAnimator(new DefaultItemAnimator());
        home_RV_categories.setAdapter(adapter_score);

        adapter_score.setCategoryClickedListener(new CategoryClickedListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void categoryItemClicked(Category category, int position) {
                Objects.requireNonNull(home_RV_categories.getAdapter()).notifyDataSetChanged();
                menuAdapter.setMenu(categories.get(position).getProducts());
                Objects.requireNonNull(home_RV_menu.getAdapter()).notifyDataSetChanged();

            }
        });


        menuAdapter = new MenuAdapter(activity, categories.get(0).getProducts());
        home_RV_menu.setLayoutManager(new LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false));
        home_RV_menu.setHasFixedSize(true);
        home_RV_menu.setItemAnimator(new DefaultItemAnimator());
        home_RV_menu.setAdapter(menuAdapter);
        menuAdapter.setMenuClickedListener(new MenuClickedListener() {
            @Override
            public void menuItemClicked(Product product, int position) {
                Intent intent = new Intent(activity, ProductActivity.class);
                intent.putExtra("product", product);
                orderActivityResultLauncher.launch(intent);
            }
        });
    }

    private void initVars() {
        categories = App.generateCategoriesData();
    }

    private void findViews(View view) {
        home_RV_categories = view.findViewById(R.id.home_RV_categories);
        home_RV_menu = view.findViewById(R.id.home_RV_menu);
    }

    ActivityResultLauncher<Intent> orderActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == AppCompatActivity.RESULT_OK) {
                        callBack_main.onOrderPlaced();
                    }
                }
            });
    

    
}