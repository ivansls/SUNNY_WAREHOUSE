package com.example.sunny_warehouse;

public class ProductClass {
    int id;
    String name;

    String coast;

    String provider;

    String Picture;

    String info;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCoast() {
        return coast;
    }

    public void setCoast(String coast) {
        this.coast = coast;
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public String getPicture() {
        return Picture;
    }

    public void setPicture(String picture) {
        Picture = picture;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public ProductClass(String name, String coast, String provider, String picture, String info) {
        this.name = name;
        this.coast = coast;
        this.provider = provider;
        Picture = picture;
        this.info = info;
    }
}
