package com.example.sunny_warehouse.Classes;

public class Seller {
    private String Login;
    private String MarketName;
    private String Address;

    public Seller(String login, String marketName, String address) {
        Login = login;
        MarketName = marketName;
        Address = address;
    }

    public String getLogin() {
        return Login;
    }

    public void setLogin(String login) {
        Login = login;
    }

    public String getMarketName() {
        return MarketName;
    }

    public void setMarketName(String marketName) {
        MarketName = marketName;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }
}
