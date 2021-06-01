package com.example.hospitalhelper.Data_Holder;

public class BloodDonateRequestUser {
    String fullname,mobilno,email,age,genderbtn,bloodgroup,user;

    BloodDonateRequestUser(){}

    public BloodDonateRequestUser(String fullname, String mobilno, String email, String age, String genderbtn, String bloodgroup, String user) {
        this.fullname = fullname;
        this.mobilno = mobilno;
        this.email = email;
        this.age = age;
        this.genderbtn = genderbtn;
        this.bloodgroup = bloodgroup;
        this.user = user;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getMobilno() {
        return mobilno;
    }

    public void setMobilno(String mobilno) {
        this.mobilno = mobilno;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getGenderbtn() {
        return genderbtn;
    }

    public void setGenderbtn(String genderbtn) {
        this.genderbtn = genderbtn;
    }

    public String getBloodgroup() {
        return bloodgroup;
    }

    public void setBloodgroup(String bloodgroup) {
        this.bloodgroup = bloodgroup;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }
}
