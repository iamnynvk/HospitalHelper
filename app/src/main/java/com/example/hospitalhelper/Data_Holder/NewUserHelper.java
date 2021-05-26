package com.example.hospitalhelper.Data_Holder;

public class NewUserHelper {
    String Firstname,Lastname,Emailid,Mobileno,Genderbutton,Birthdate,Password,profileimg,user;

    public NewUserHelper(String firstname, String lastname, String emailid, String mobileno, String genderbutton, String birthdate, String password, String profileimg, String user) {
        Firstname = firstname;
        Lastname = lastname;
        Emailid = emailid;
        Mobileno = mobileno;
        Genderbutton = genderbutton;
        Birthdate = birthdate;
        Password = password;
        this.profileimg = profileimg;
        this.user = user;
    }

    public NewUserHelper() {

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

    public String getEmailid() {
        return Emailid;
    }

    public void setEmailid(String emailid) {
        Emailid = emailid;
    }

    public String getMobileno() {
        return Mobileno;
    }

    public void setMobileno(String mobileno) {
        Mobileno = mobileno;
    }

    public String getGenderbutton() {
        return Genderbutton;
    }

    public void setGenderbutton(String genderbutton) {
        Genderbutton = genderbutton;
    }

    public String getBirthdate() {
        return Birthdate;
    }

    public void setBirthdate(String birthdate) {
        Birthdate = birthdate;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
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
