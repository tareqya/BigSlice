package com.tareqyassin.bigslice.utils;

import com.tareqyassin.bigslice.database.Order;

import java.util.Comparator;

public class DateComparator implements Comparator<Order> {
    @Override
    public int compare(Order o1, Order o2) {
        return o1.convertToDate().compareTo(o2.convertToDate());
    }
}
