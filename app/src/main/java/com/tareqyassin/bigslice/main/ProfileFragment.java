package com.tareqyassin.bigslice.main;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.airbnb.lottie.LottieAnimationView;
import com.bumptech.glide.Glide;
import com.tareqyassin.bigslice.R;
import com.tareqyassin.bigslice.auth.LoginActivity;
import com.tareqyassin.bigslice.database.Customer;
import com.tareqyassin.bigslice.database.DatabaseManager;
import com.tareqyassin.bigslice.interfaces.CallBack_Profile;

import java.io.FileNotFoundException;
import java.io.InputStream;


public class ProfileFragment extends Fragment {

    private AppCompatActivity activity;
    private ImageView profile_IMG_uploadImageProfile, profile_image;
    private LinearLayout profile_LL_editDetails, profile_LL_changeLocation, profile_LL_logout;
    private TextView profile_TV_name, profile_TV_phone, profile_TV_email;
    private DatabaseManager db;
    private Customer currentCustomer;
    private LottieAnimationView profile_Lottie_loading;
    private final CallBack_Profile callBack_profile = new CallBack_Profile() {
        @Override
        public void onFetchCustomerDataDone(Customer customer) {
                currentCustomer = customer;
                profile_TV_email.setText(customer.getEmail());
                profile_TV_name.setText(customer.getFull_name());
                profile_TV_phone.setText(customer.getPhone());
                if(!customer.getImgName().equals("")) {
                    db.downloadProfileImage(customer.getImgName());
                }else {
                    profile_Lottie_loading.setVisibility(View.INVISIBLE);
                }
        }

        @Override
        public void onImageUploadDone(boolean status, String msg, String imgName) {

            if(status){
                currentCustomer.setImgName(imgName);
                db.updateCustomer(currentCustomer);
            }else {
                Toast.makeText(activity, msg, Toast.LENGTH_SHORT).show();
                profile_Lottie_loading.setVisibility(View.INVISIBLE);
            }
        }

        @Override
        public void updateCustomerDone(boolean status, String msg) {
            Toast.makeText(activity, msg, Toast.LENGTH_SHORT).show();
            profile_Lottie_loading.setVisibility(View.INVISIBLE);

        }

        @Override
        public void onImageDownloadUriDone(boolean status, String msg, String downloadUrl) {
            if(status){
                Glide.with(activity).load(downloadUrl).into(profile_image);
            }else{
                Toast.makeText(activity, msg, Toast.LENGTH_SHORT).show();
            }

            profile_Lottie_loading.setVisibility(View.INVISIBLE);
        }
    };

    public void setActivity(AppCompatActivity activity) {
        this.activity = activity;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        findViews(view);
        initViews();

        return view;
    }

    private void initViews() {
        db = DatabaseManager.getMe();
        db.setCallBack_Profile(callBack_profile);
        db.getCustomerById(db.getCurrentUser().getUid());
        profile_LL_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db.logout();
                Intent intent = new Intent(activity, LoginActivity.class);
                startActivity(intent);
                activity.finish();
            }
        });

        profile_IMG_uploadImageProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uploadImage();
            }
        });

        profile_LL_editDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        profile_LL_changeLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    private void findViews(View view) {
        profile_IMG_uploadImageProfile = view.findViewById(R.id.profile_IMG_uploadImageProfile);
        profile_LL_editDetails = view.findViewById(R.id.profile_LL_editDetails);
        profile_LL_changeLocation = view.findViewById(R.id.profile_LL_changeLocation);
        profile_LL_logout = view.findViewById(R.id.profile_LL_logout);

        profile_TV_email = view.findViewById(R.id.profile_TV_email);
        profile_TV_phone = view.findViewById(R.id.profile_TV_phone);
        profile_TV_name = view.findViewById(R.id.profile_TV_name);
        profile_image = view.findViewById(R.id.profile_image);
        profile_Lottie_loading = view.findViewById(R.id.profile_Lottie_loading);

    }

    ActivityResultLauncher<Intent> someActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == AppCompatActivity.RESULT_OK) {
                        // There are no request codes
                        Intent data = result.getData();
                        try {
                            final Uri imageUri = data.getData();
                            final InputStream imageStream = activity.getContentResolver().openInputStream(imageUri);
                            final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                            profile_image.setImageBitmap(selectedImage);
                            db.uploadImage(activity, imageUri);
                            profile_Lottie_loading.setVisibility(View.VISIBLE);
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                            Toast.makeText(activity, "Something went wrong", Toast.LENGTH_LONG).show();
                        }
                    }else {
                        Toast.makeText(activity, "You haven't picked Image",Toast.LENGTH_LONG).show();
                    }
                }
            });

    public void uploadImage(){
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        photoPickerIntent.setType("image/*");
        someActivityResultLauncher.launch(photoPickerIntent);
    }

}