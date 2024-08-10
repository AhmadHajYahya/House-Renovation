package com.ahmad.houserenovationapp.utils;

import com.ahmad.houserenovationapp.enums.Category;

public class HelperFunctions {
    public static String formatCategoryName(Category category) {
        String name = category.name().replace("_", " ").toLowerCase();
        return name.substring(0, 1).toUpperCase() + name.substring(1);
    }

}
