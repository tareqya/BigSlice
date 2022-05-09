package com.Aya.bigslice.database;

import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.Aya.bigslice.interfaces.CallBack_Auth;
import com.Aya.bigslice.interfaces.CallBack_Profile;
import com.Aya.bigslice.interfaces.Callback_Order;
import com.Aya.bigslice.utils.UtilsFunctions;

import java.util.ArrayList;


public class DatabaseManager {

    private FirebaseAuth mAuth;
    private FirebaseDatabase mDatabase;
    private FirebaseStorage mStorage;
    private CallBack_Auth callBack_auth;
    private CallBack_Profile callBack_profile;
    private Callback_Order callback_order;

    public DatabaseManager(){
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance("https://bigslice-60440-default-rtdb.europe-west1.firebasedatabase.app");
        mStorage = FirebaseStorage.getInstance();
    }

    public DatabaseManager setCallBack_auth(CallBack_Auth callBack_auth){
        this.callBack_auth = callBack_auth;
        return this;
    }

    public DatabaseManager setCallback_Order(Callback_Order callback_order){
        this.callback_order = callback_order;
        return this;
    }

    public DatabaseManager setCallBack_Profile(CallBack_Profile callBack_profile){
        this.callBack_profile = callBack_profile;
        return this;
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
                                if(task.isSuccessful()){
                                    callBack_auth.onLoginDone(true, "");
                                }else{
                                    callBack_auth.onLoginDone(false, task.getException().getMessage());

                                }
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
                if(callBack_auth != null){
                    if(task.isSuccessful()){
                        callBack_auth.onAddCustomerDone(task.isSuccessful(), "Success");

                    }else {
                        callBack_auth.onAddCustomerDone(task.isSuccessful(), task.getException().getMessage());

                    }
                }
            }
        });
    }


    public void getCustomerById(String id) {
        mDatabase.getReference("Customers/"+id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(callBack_profile != null){
                    Customer customer = snapshot.getValue(Customer.class);
                    callBack_profile.onFetchCustomerDataDone(customer);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void updateCustomer(Customer customer){
        mDatabase.getReference("Customers").child(customer.getId()).setValue(customer)
        .addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(callBack_profile != null){
                    if(task.isSuccessful()){
                        callBack_profile.updateCustomerDone(true, "success");
                    }else{
                        callBack_profile.updateCustomerDone(false, task.getException().getMessage());
                    }
                }
            }
        });
    }

    public void uploadImage(AppCompatActivity activity, Uri uri){
        String image_name = this.getCurrentUser().getUid() + "." + UtilsFunctions.getFileExtension(activity, uri);
        StorageReference storageReference = mStorage.getReference("CustomersImage/"+image_name);
        storageReference.putFile(uri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                if(callBack_profile != null){
                    if(task.isSuccessful()){
                        callBack_profile.onImageUploadDone(true, "Image updated", image_name);
                    }else{
                        callBack_profile.onImageUploadDone(false, task.getException().getMessage(), "");
                    }
                }
            }
        });

    }


    public void downloadProfileImage(String imgName){

        StorageReference storageReference = mStorage.getReference("CustomersImage/"+imgName);

        storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                if(callBack_profile != null){
                    callBack_profile.onImageDownloadUriDone(true, "success", uri.toString());
                }
            }
        })
        .addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                if(callBack_profile != null){
                    callBack_profile.onImageDownloadUriDone(false, e.getMessage(), "");
                }
            }
        });
    }


    public void addOrder(AppCompatActivity activity, Order order){
        mDatabase.getReference("Orders").push().setValue(order)
                .addOnCompleteListener(activity, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                       if(callback_order == null)
                           return;

                       if(task.isSuccessful()){
                            callback_order.onOrderAddDone(true, "Order has been placed");
                       }else {
                           callback_order.onOrderAddDone(false, task.getException().getMessage());
                       }
                    }
                });
    }


    public void getCustomerOrders(String customerId){
        mDatabase.getReference("Orders").orderByChild("customerId").equalTo(customerId)
                .addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if(callback_order != null){
                    ArrayList<Order> orders = new ArrayList<>();
                    for (DataSnapshot snap : snapshot.getChildren()){
                        orders.add(snap.getValue(Order.class));
                    }
                    callback_order.onFetchCustomerOrdersDone(orders);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}
