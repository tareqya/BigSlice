package com.Aya.bigslice.interfaces;

import com.Aya.bigslice.database.Category;

public interface CategoryClickedListener {
    void categoryItemClicked(Category category, int position);
}
