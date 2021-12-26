package com.tareqyassin.bigslice.interfaces;

public interface CallBack_Auth {
    void onCreateAccountDone(boolean status, String msg, String uid);
    void onLoginDone(boolean status);
    void onAddCustomerDone(boolean status, String msg);
}
