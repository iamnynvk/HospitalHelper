package com.example.hospitalhelper.Data_Holder;

public class NewUserHelper {
    String profileimage,firstname,lastname,email,mobileno,gender,password,birthdate,bloodgroup,userid;

    // constructor

    public NewUserHelper() {
    }

    public NewUserHelper(String profileimage, String firstname, String lastname, String email, String mobileno, String gender, String password, String birthdate, String bloodgroup, String userid) {
        this.profileimage = profileimage;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.mobileno = mobileno;
        this.gender = gender;
        this.password = password;
        this.birthdate = birthdate;
        this.bloodgroup = bloodgroup;
        this.userid = userid;
    }

    // getter-setter

    public String getProfileimage() {
        return profileimage;
    }

    public void setProfileimage(String profileimage) {
        this.profileimage = profileimage;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobileno() {
        return mobileno;
    }

    public void setMobileno(String mobileno) {
        this.mobileno = mobileno;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(String birthdate) {
        this.birthdate = birthdate;
    }

    public String getBloodgroup() {
        return bloodgroup;
    }

    public void setBloodgroup(String bloodgroup) {
        this.bloodgroup = bloodgroup;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }
}
