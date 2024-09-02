package com.example.sunny_warehouse.Classes;


import java.util.List;

public class Users {
    String Login;

    String FIO;


    public Users(String login, String FIO) {
        Login = login;
        this.FIO = FIO;
    }


    public String getLogin() {
        return Login;
    }

    public void setLogin(String login) {
        Login = login;
    }

    public String getFIO() {
        return FIO;
    }

    public void setFIO(String FIO) {
        this.FIO = FIO;
    }
}
