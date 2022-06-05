package com.project.bigslice.interfaces;

import com.project.bigslice.database.Order;

public interface OrderClickedListener {
    void OrderItemClicked(Order order, int position);
}
