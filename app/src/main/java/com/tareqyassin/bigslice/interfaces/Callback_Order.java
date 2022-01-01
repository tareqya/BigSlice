package com.tareqyassin.bigslice.interfaces;

import com.tareqyassin.bigslice.database.Order;

import java.util.ArrayList;

public interface Callback_Order {
    void onOrderAddDone(boolean status, String msg);
    void onFetchCustomerOrdersDone(ArrayList<Order> orders);
}
