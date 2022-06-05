package com.project.bigslice.interfaces;

import com.project.bigslice.database.Customer;

public interface CallBack_Profile {
    void onFetchCustomerDataDone(Customer customer);
    void onImageUploadDone(boolean status, String msg, String imgName);
    void updateCustomerDone(boolean status, String msg);
    void onImageDownloadUriDone(boolean status, String msg, String downloadUrl);
}
