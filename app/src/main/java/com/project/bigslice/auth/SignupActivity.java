package com.project.bigslice.auth;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;
import com.project.bigslice.R;
import com.project.bigslice.database.Customer;
import com.project.bigslice.database.DatabaseManager;
import com.project.bigslice.interfaces.CallBack_Auth;
import com.project.bigslice.utils.Validator;

import java.util.Objects;

public class SignupActivity extends AppCompatActivity {
    private TextInputLayout signup_TIL_fullName, signup_TIL_email, signup_TIL_password, signup_TIL_confirmPassword;
    private MaterialButton signup_BTN_signup;
    private DatabaseManager db;
    private TextView signup_TV_msg;
    private Validator.Builder full_name_builder, email_builder, password_builder, confirmPassword_builder;
    private Customer newCustomer;
    private LottieAnimationView signup_Lottie_loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        // calling the action bar
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        initViews();
        addValidation();
        db = new DatabaseManager();
        db.setCallBack_auth(callBack_auth);
        signup_Lottie_loading.setVisibility(View.INVISIBLE);


        signup_BTN_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(full_name_builder.getStatus() && email_builder.getStatus() && password_builder.getStatus() && confirmPassword_builder.getStatus()) {
                    signup_Lottie_loading.playAnimation();
                    signup_Lottie_loading.setVisibility(View.VISIBLE);
                    signup_BTN_signup.setEnabled(false);
                    String email = signup_TIL_email.getEditText().getText().toString();
                    String password = signup_TIL_password.getEditText().getText().toString();
                    db.createNewUser(SignupActivity.this, email, password);
                }else {
                    signup_BTN_signup.setEnabled(true);
                    signup_Lottie_loading.setVisibility(View.INVISIBLE);
                }

            }
        });

    }

    private void addValidation() {
        full_name_builder = Validator.Builder
                .make(signup_TIL_fullName)
                .addWatcher(new Validator.Watcher_NotEmpty("Name can't be empty"))
                .addWatcher(new Validator.Watcher_FullName("Enter the full name"))
                .build();

        email_builder = Validator.Builder
                .make(signup_TIL_email)
                .addWatcher(new Validator.Watcher_NotEmpty("Email can't be empty"))
                .addWatcher(new Validator.Watcher_Email("Email not valid"))
                .build();

        password_builder = Validator.Builder
                .make(signup_TIL_password)
                .addWatcher(new Validator.Watcher_NotEmpty("Password can't be empty"))
                .addWatcher(new Validator.Watcher_password("Wrong password format"))
                .build();

        confirmPassword_builder = Validator.Builder
                .make(signup_TIL_confirmPassword)
                .addWatcher(new Validator.Watcher_NotEmpty("Password can't be empty"))
                .addWatcher(new Validator.Watcher_Equal("Confirm password doesn't match password ", signup_TIL_password.getEditText()))
                .build();
    }

    private CallBack_Auth callBack_auth = new CallBack_Auth() {
        @Override
        public void onCreateAccountDone(boolean status, String msg, String uid) {
            if(status){
                newCustomer = new Customer()
                        .setEmail(signup_TIL_email.getEditText().getText().toString())
                        .setFull_name(signup_TIL_fullName.getEditText().getText().toString())
                        .setId(uid)
                        .setPhone("");
                db.login(SignupActivity.this, newCustomer.getEmail(), signup_TIL_password.getEditText().getText().toString());
            }else{
                signup_BTN_signup.setEnabled(true);
                signup_TV_msg.setText(msg);
                signup_TV_msg.setTextColor(Color.RED);
                signup_Lottie_loading.setVisibility(View.INVISIBLE);

            }


        }

        @Override
        public void onLoginDone(boolean status, String msg) {
            if(status){
                db.addNewCustomer(SignupActivity.this, newCustomer);
            }else{
                Toast.makeText(SignupActivity.this, msg, Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public void onAddCustomerDone(boolean status, String msg) {
            if(status) {
                db.logout();
                Toast.makeText(SignupActivity.this, "Account created!", Toast.LENGTH_SHORT).show();
                signup_Lottie_loading.setVisibility(View.INVISIBLE);
                finish();
            }else{
                signup_TV_msg.setText("Failed to create account!");
                signup_TV_msg.setTextColor(Color.RED);
                signup_BTN_signup.setEnabled(true);
                signup_Lottie_loading.setVisibility(View.INVISIBLE);
            }
        }

    };


    private void initViews() {
        signup_TV_msg = findViewById(R.id.signup_TV_msg);
        signup_BTN_signup = findViewById(R.id.signup_BTN_signup);
        signup_TIL_fullName = findViewById(R.id.signup_TIL_fullName);
        signup_TIL_email = findViewById(R.id.signup_TIL_email);
        signup_TIL_password = findViewById(R.id.signup_TIL_password);
        signup_TIL_confirmPassword = findViewById(R.id.signup_TIL_confirmPassword);
        signup_Lottie_loading = findViewById(R.id.signup_Lottie_loading);

    }
}