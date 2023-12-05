package com.nhnacademy.shoppingmall.categories.domain;

import java.util.Objects;

public class Categories {
    private int categoryID;
    private String categoryName;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Categories that = (Categories) o;
        return categoryID == that.categoryID && Objects.equals(categoryName, that.categoryName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(categoryID, categoryName);
    }

    public Categories( String categoryName) {
        this.categoryName = categoryName;
    }

    public Categories(int categoryID, String categoryName) {
        this.categoryID = categoryID;
        this.categoryName = categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public int getCategoryID() {
        return categoryID;
    }

    public String getCategoryName() {
        return categoryName;
    }
}
