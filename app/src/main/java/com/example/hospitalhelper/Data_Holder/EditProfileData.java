package com.example.hospitalhelper.Data_Holder;

public class EditProfileData {
    String Firstname,Lastname,Mobileno,Birthdate,profileimg,user;

    public EditProfileData(String firstname, String lastname, String mobileno, String birthdate, String profileimg, String user) {
        Firstname = firstname;
        Lastname = lastname;
        Mobileno = mobileno;
        Birthdate = birthdate;
        this.profileimg = profileimg;
        this.user = user;
    }

    public String getFirstname() {
        return Firstname;
    }

    public void setFirstname(String firstname) {
        Firstname = firstname;
    }

    public String getLastname() {
        return Lastname;
    }

    public void setLastname(String lastname) {
        Lastname = lastname;
    }

    public String getMobileno() {
        return Mobileno;
    }

    public void setMobileno(String mobileno) {
        Mobileno = mobileno;
    }

    public String getBirthdate() {
        return Birthdate;
    }

    public void setBirthdate(String birthdate) {
        Birthdate = birthdate;
    }

    public String getProfileimg() {
        return profileimg;
    }

    public void setProfileimg(String profileimg) {
        this.profileimg = profileimg;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }
}
