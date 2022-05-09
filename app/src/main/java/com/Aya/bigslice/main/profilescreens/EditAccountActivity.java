package com.Aya.bigslice.main.profilescreens;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;
import com.Aya.bigslice.R;
import com.Aya.bigslice.database.Customer;
import com.Aya.bigslice.database.DatabaseManager;
import com.Aya.bigslice.interfaces.CallBack_Profile;
import com.Aya.bigslice.utils.Validator;

import java.util.Objects;

public class EditAccountActivity extends AppCompatActivity {

    private TextInputLayout editAccount_TIL_fullName, editAccount_TIL_phone;
    private MaterialButton editAccount_BTN_update;
    private LottieAnimationView editAccount_Lottie_loading;
    private Validator.Builder full_name_builder, phone_builder;
    private DatabaseManager db;
    private Customer currentCustomer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_account);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        findViews();
        addValidation();

        editAccount_Lottie_loading.setVisibility(View.INVISIBLE);
        db =new DatabaseManager();
        db.setCallBack_Profile(callBack_profile);
        db.getCustomerById(db.getCurrentUser().getUid());

        editAccount_BTN_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(full_name_builder.getStatus() && phone_builder.getStatus() && currentCustomer != null) {
                    editAccount_Lottie_loading.setVisibility(View.VISIBLE);
                    editAccount_Lottie_loading.playAnimation();
                    currentCustomer.setPhone(editAccount_TIL_phone.getEditText().getText().toString());
                    currentCustomer.setFull_name(editAccount_TIL_fullName.getEditText().getText().toString());
                    db.updateCustomer(currentCustomer);
                }
            }
        });

    }

    private void findViews() {
        editAccount_TIL_fullName = findViewById(R.id.editAccount_TIL_fullName);
        editAccount_TIL_phone = findViewById(R.id.editAccount_TIL_phone);
        editAccount_BTN_update = findViewById(R.id.editAccount_BTN_update);
        editAccount_Lottie_loading = findViewById(R.id.editAccount_Lottie_loading);

    }

    private void addValidation() {
        full_name_builder = Validator.Builder
                .make(editAccount_TIL_fullName)
                .addWatcher(new Validator.Watcher_NotEmpty("Name can't be empty"))
                .addWatcher(new Validator.Watcher_FullName("Enter the full name"))
                .build();

        phone_builder = Validator.Builder
                .make(editAccount_TIL_phone)
                .addWatcher(new Validator.Watcher_NotEmpty("Email can't be empty"))
                .addWatcher(new Validator.Watcher_Phone("Phone number not valid"))
                .build();
    }


    private CallBack_Profile callBack_profile = new CallBack_Profile() {
        @Override
        public void onFetchCustomerDataDone(Customer customer) {
            if(customer != null) {
                currentCustomer = customer;
                editAccount_TIL_phone.getEditText().setText(currentCustomer.getPhone());
                editAccount_TIL_fullName.getEditText().setText(customer.getFull_name());
            }else{
                Toast.makeText(EditAccountActivity.this, "Failed to fetch customer data", Toast.LENGTH_SHORT).show();
                editAccount_Lottie_loading.setVisibility(View.INVISIBLE);
            }
        }

        @Override
        public void onImageUploadDone(boolean status, String msg, String imgName) {

        }

        @Override
        public void updateCustomerDone(boolean status, String msg) {
            if(status){
                Toast.makeText(EditAccountActivity.this, "Updated", Toast.LENGTH_SHORT).show();
                finish();
            }else {
                Toast.makeText(EditAccountActivity.this, msg, Toast.LENGTH_SHORT).show();
                editAccount_Lottie_loading.setVisibility(View.INVISIBLE);

            }
        }

        @Override
        public void onImageDownloadUriDone(boolean status, String msg, String downloadUrl) {

        }
    };
}