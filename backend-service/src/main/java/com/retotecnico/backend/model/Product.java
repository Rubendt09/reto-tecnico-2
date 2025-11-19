package com.retotecnico.backend.model;

public class Product {
    private int productId;
    private String title;

    public Product() {
    }

    public Product(int productId, String title) {
        this.productId = productId;
        this.title = title;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
