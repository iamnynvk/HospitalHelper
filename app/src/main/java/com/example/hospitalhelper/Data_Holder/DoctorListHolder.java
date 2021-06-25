package com.example.hospitalhelper.Data_Holder;

public class DoctorListHolder {
    String doctorurl,doctorname,qualification,type,time;

    public DoctorListHolder() {
    }

    public DoctorListHolder(String doctorurl, String doctorname, String qualification, String type, String time) {
        this.doctorurl = doctorurl;
        this.doctorname = doctorname;
        this.qualification = qualification;
        this.type = type;
        this.time = time;
    }

    public String getDoctorurl() {
        return doctorurl;
    }

    public void setDoctorurl(String doctorurl) {
        this.doctorurl = doctorurl;
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
}
