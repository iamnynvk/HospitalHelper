package com.example.hospitalhelper.Data_Holder;

public class DoctorAppoimentDataHolder {
    String firstname,lastname,mobileno,birthdate,profileimg,user,doctorbookdate,doctorname,qualification,type,time,doctorurl;

    public DoctorAppoimentDataHolder() {
    }


    public DoctorAppoimentDataHolder(String firstname, String lastname, String mobileno, String birthdate, String profileimg, String user, String doctorbookdate, String doctorname, String qualification, String type, String time, String doctorurl) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.mobileno = mobileno;
        this.birthdate = birthdate;
        this.profileimg = profileimg;
        this.user = user;
        this.doctorbookdate = doctorbookdate;
        this.doctorname = doctorname;
        this.qualification = qualification;
        this.type = type;
        this.time = time;
        this.doctorurl = doctorurl;
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

    public String getMobileno() {
        return mobileno;
    }

    public void setMobileno(String mobileno) {
        this.mobileno = mobileno;
    }

    public String getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(String birthdate) {
        this.birthdate = birthdate;
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

    public String getDoctorbookdate() {
        return doctorbookdate;
    }

    public void setDoctorbookdate(String doctorbookdate) {
        this.doctorbookdate = doctorbookdate;
    }

    public String getDoctorname() {
        return doctorname;
    }

    public void setDoctorname(String doctorname) {
        this.doctorname = doctorname;
    }

    public String getQualification() {
        return qualification;
    }

    public void setQualification(String qualification) {
        this.qualification = qualification;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDoctorurl() {
        return doctorurl;
    }

    public void setDoctorurl(String doctorurl) {
        this.doctorurl = doctorurl;
    }
}
