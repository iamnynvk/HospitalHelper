package com.example.hospitalhelper.Data_Holder;

public class ContactUsUser {
    String fullname, mobileno, email, message, user;

    //constructor
    ContactUsUser(){}

    public ContactUsUser(String fullname, String mobileno, String email, String message, String user) {
        this.fullname = fullname;
        this.mobileno = mobileno;
        this.email = email;
        this.message = message;
        this.user = user;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getMobileno() {
        return mobileno;
    }

    public void setMobileno(String mobileno) {
        this.mobileno = mobileno;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }
}
