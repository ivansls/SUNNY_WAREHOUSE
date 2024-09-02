package com.example.sunny_warehouse.Classes;

public class Product {

    private String productName;
    private String category;
    private String seller;
    private String fotoPath;

    private String coast;

    private String info;

    public Product(String productName, String category, String seller, String fotoPath, String coast, String info) {
        this.productName = productName;
        this.category = category;
        this.seller = seller;
        this.fotoPath = fotoPath;
        this.coast = coast;
        this.info = info;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getSeller() {
        return seller;
    }

    public void setSeller(String seller) {
        this.seller = seller;
    }

    public String getFotoPath() {
        return fotoPath;
    }

    public void setFotoPath(String fotoPath) {
        this.fotoPath = fotoPath;
    }

    public String getCoast() {
        return coast;
    }

    public void setCoast(String coast) {
        this.coast = coast;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}
