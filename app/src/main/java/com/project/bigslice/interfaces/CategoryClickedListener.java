package com.project.bigslice.interfaces;

import com.project.bigslice.database.Category;

public interface CategoryClickedListener {
    void categoryItemClicked(Category category, int position);
}
