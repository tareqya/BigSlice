package com.tareqyassin.bigslice.interfaces;

import com.tareqyassin.bigslice.database.Order;

public interface OrderClickedListener {
    void OrderItemClicked(Order order, int position);
}
