package com.Aya.bigslice.interfaces;

public interface CallBack_Auth {
    void onCreateAccountDone(boolean status, String msg, String uid);
    void onLoginDone(boolean status, String msg);
    void onAddCustomerDone(boolean status, String msg);
}
