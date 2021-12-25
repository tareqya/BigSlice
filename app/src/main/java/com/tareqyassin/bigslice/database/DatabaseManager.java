package com.tareqyassin.bigslice.database;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.tareqyassin.bigslice.interfaces.CallBack_Auth;
import com.tareqyassin.bigslice.interfaces.CallBack_Database;


public class DatabaseManager {

    private FirebaseAuth mAuth;
    private FirebaseDatabase mDatabase;
    private static DatabaseManager me = null;
    private CallBack_Auth callBack_auth;
    private CallBack_Database callBack_database;


    private DatabaseManager(){
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance("https://bigslice-60440-default-rtdb.europe-west1.firebasedatabase.app");
    }

    public static void DatabaseInit(){
        if(me == null){
            me = new DatabaseManager();
        }

    }

    public DatabaseManager setCallBack_auth(CallBack_Auth callBack_auth){
        this.callBack_auth = callBack_auth;
        return this;
    }

    public DatabaseManager setCallBack_Database(CallBack_Database callBack_database){
        this.callBack_database = callBack_database;
        return this;
    }

    public static DatabaseManager getMe(){
        return me;
    }

    public FirebaseUser getCurrentUser(){
        return mAuth.getCurrentUser();
    }

    public void createNewUser(AppCompatActivity activity, String email, String password){
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(activity, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            if(callBack_auth != null){

                                callBack_auth.onCreateAccountDone(true, "", task.getResult().getUser().getUid());
                            }
                        }else{
                            callBack_auth.onCreateAccountDone(false, task.getException().getMessage(), "");
                        }
                    }
                });
    }

    public void login(AppCompatActivity activity, String email, String password){
        mAuth.signInWithEmailAndPassword(email, password).
                addOnCompleteListener(activity, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                            if(callBack_auth != null) {
                                callBack_auth.onLoginDone(task.isSuccessful());
                            }
                    }
                });
    }

    public void logout() {
        mAuth.signOut();
    }

    public void addNewCustomer(AppCompatActivity activity, Customer customer){
        mDatabase.getReference("Customers").child(customer.getId()).setValue(customer)
        .addOnCompleteListener(activity, new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(callBack_database != null){
                    if(task.isSuccessful()){
                        callBack_database.onAddCustomerDone(task.isSuccessful(), "Success");

                    }else {
                        callBack_database.onAddCustomerDone(task.isSuccessful(), task.getException().getMessage());

                    }
                }
            }
        });
    }

}
