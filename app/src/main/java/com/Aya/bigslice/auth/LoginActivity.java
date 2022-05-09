package com.Aya.bigslice.auth;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseUser;
import com.Aya.bigslice.R;
import com.Aya.bigslice.database.DatabaseManager;
import com.Aya.bigslice.main.MainActivity;
import com.Aya.bigslice.interfaces.CallBack_Auth;

public class LoginActivity extends AppCompatActivity {

    private Button login_BTN_createAccount;
    private MaterialButton login_BTN_login;
    private TextInputLayout login_TIL_email, login_TIL_password;
    private DatabaseManager db;
    private LottieAnimationView login_Lottie_loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initView();
        login_Lottie_loading.setVisibility(View.INVISIBLE);
        login_BTN_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = login_TIL_email.getEditText().getText().toString();
                String password = login_TIL_password.getEditText().getText().toString();
                if(email.equals("") || password.equals("")) {
                    Toast.makeText(LoginActivity.this, "Email or Password can't be empty", Toast.LENGTH_SHORT).show();
                    return;
                }
                login_Lottie_loading.setVisibility(View.VISIBLE);
                db.login(LoginActivity.this, email, password);

            }
        });

        login_BTN_createAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, SignupActivity.class));
            }
        });
    }

    private void initView() {
        login_BTN_login = findViewById(R.id.login_BTN_login);
        login_BTN_createAccount = findViewById(R.id.login_BTN_createAccount);
        login_TIL_email = findViewById(R.id.login_TIL_email);
        login_TIL_password = findViewById(R.id.login_TIL_password);
        login_Lottie_loading = findViewById(R.id.login_Lottie_loading);

    }

    private CallBack_Auth callBack_auth = new CallBack_Auth() {
        @Override
        public void onCreateAccountDone(boolean status, String msg, String uid) {

        }

        @Override
        public void onLoginDone(boolean status, String msg) {
            if(status) {
                openHomeScreen();
            }else{
                Toast.makeText(LoginActivity.this, msg, Toast.LENGTH_SHORT).show();
                login_Lottie_loading.setVisibility(View.INVISIBLE);
            }
        }

        @Override
        public void onAddCustomerDone(boolean status, String msg) {

        }
    };

    public void openHomeScreen(){
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onStart() {
        super.onStart();
        db = new DatabaseManager();
        db.setCallBack_auth(callBack_auth);
        FirebaseUser user = db.getCurrentUser();
        if(user != null){
            openHomeScreen();
        }
    }
}