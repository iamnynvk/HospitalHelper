package com.example.hospitalhelper.Data_Holder;

public class NewPasswordHolder {
    String Password,user;

    public NewPasswordHolder() {
    }

    public NewPasswordHolder(String password, String user) {
        Password = password;
        this.user = user;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }
}
