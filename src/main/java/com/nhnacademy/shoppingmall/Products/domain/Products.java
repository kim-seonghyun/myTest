package com.nhnacademy.shoppingmall.Products.domain;

import java.util.Objects;

public class Products {
    private int productId;
    private int categoryId;
    private String modelNumber;
    private String modelName;
    private String productImage;
    private int UnitCost;
    private String Description;


    public Products(int productId, int categoryId, String modelNumber, String modelName, String productImage,
                    int unitCost, String description) {
        this.productId = productId;
        this.categoryId = categoryId;
        this.modelNumber = modelNumber;
        this.modelName = modelName;
        this.productImage = productImage;
        UnitCost = unitCost;
        Description = description;
    }

    public Products(int categoryId, String modelNumber, String modelName, String productImage, int unitCost,
                    String description) {
        this.categoryId = categoryId;
        this.modelNumber = modelNumber;
        this.modelName = modelName;
        this.productImage = productImage;
        UnitCost = unitCost;
        Description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Products products = (Products) o;
        return categoryId == products.categoryId && UnitCost == products.UnitCost && Objects.equals(modelNumber,
                products.modelNumber) && Objects.equals(modelName, products.modelName)
                && Objects.equals(productImage, products.productImage) && Objects.equals(Description,
                products.Description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(categoryId, modelNumber, modelName, productImage, UnitCost, Description);
    }

    public void setModelNumber(String modelNumber) {
        this.modelNumber = modelNumber;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }

    public void setUnitCost(int unitCost) {
        UnitCost = unitCost;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public int getProductId() {
        return productId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public String getModelNumber() {
        return modelNumber;
    }

    public String getModelName() {
        return modelName;
    }

    public String getProductImage() {
        return productImage;
    }

    public int getUnitCost() {
        return UnitCost;
    }

    public String getDescription() {
        return Description;
    }
}
