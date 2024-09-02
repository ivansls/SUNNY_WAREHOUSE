package com.example.sunny_warehouse.Classes;

public class Order {

    String Product;
    String login;

    String Count;
    String seller;

    public Order(String product, String login, String count, String seller) {
        Product = product;
        this.login = login;
        Count = count;
        this.seller = seller;
    }

    public String getProduct() {
        return Product;
    }

    public void setProduct(String product) {
        Product = product;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getCount() {
        return Count;
    }

    public void setCount(String count) {
        Count = count;
    }

    public String getSeller() {
        return seller;
    }

    public void setSeller(String seller) {
        this.seller = seller;
    }
}
