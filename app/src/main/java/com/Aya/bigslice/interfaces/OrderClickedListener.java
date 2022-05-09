package com.Aya.bigslice.interfaces;

import com.Aya.bigslice.database.Order;

public interface OrderClickedListener {
    void OrderItemClicked(Order order, int position);
}
