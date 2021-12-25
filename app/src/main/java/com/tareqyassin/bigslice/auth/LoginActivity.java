package com.tareqyassin.bigslice.auth;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseUser;
import com.tareqyassin.bigslice.R;
import com.tareqyassin.bigslice.database.DatabaseManager;
import com.tareqyassin.bigslice.main.MainActivity;
import com.tareqyassin.bigslice.interfaces.CallBack_Auth;

public class LoginActivity extends AppCompatActivity {

    private Button login_BTN_createAccount;
    private MaterialButton login_BTN_login;
    private TextInputEditText login_TIE_email, login_TIE_password;
    private DatabaseManager db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();

        login_BTN_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = login_TIE_email.getText().toString();
                String password = login_TIE_password.getText().toString();
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
        login_TIE_email = findViewById(R.id.login_TIE_email);
        login_TIE_password = findViewById(R.id.login_TIE_password);

    }

    private CallBack_Auth callBack_auth = new CallBack_Auth() {
        @Override
        public void onCreateAccountDone(boolean status, String msg, String uid) {

        }

        @Override
        public void onLoginDone(boolean status) {
            if(status) {
                openHomeScreen();
            }else{
                Toast.makeText(LoginActivity.this, "Failed to login!", Toast.LENGTH_SHORT).show();
            }
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
        db = DatabaseManager.getMe();
        db.setCallBack_auth(callBack_auth);
        FirebaseUser user = db.getCurrentUser();
        if(user != null){
            openHomeScreen();
        }
    }
}