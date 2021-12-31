package com.tareqyassin.bigslice.interfaces;

import com.tareqyassin.bigslice.database.Category;

public interface CategoryClickedListener {
    void categoryItemClicked(Category category, int position);
}
