package com.example.bookstore;

public class Product {
    public void setProductId(int productId) {
        this.productId = productId;
    }

    private int productId;

    public int getProductId() {
        return productId;
    }

    private int resourceId;
    private String name;
    private String description;
    private boolean isAddToCart;

    public Product(int resourceId, String name, String description, int productId) {
        this.resourceId = resourceId;
        this.name = name;
        this.description = description;
        this.productId = productId;
    }

    public int getResourceId() {
        return resourceId;
    }

    public void setResourceId(int resourceId) {
        this.resourceId = resourceId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isAddToCart() {
        return isAddToCart;
    }

    public void setAddToCart(boolean addToCart) {
        isAddToCart = addToCart;
    }
}
