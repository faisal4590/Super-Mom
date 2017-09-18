package com.faisal.supermom;

/**
 * Created by faisal on 8/22/2017.
 */

public class DoctorClassNew {

    String doctorNameVar,doctorHospitalVar,doctorSpecialityVar,doctorAvailibilityVar,
    doctorContactNoVar,doctorIDVar;

    DoctorClassNew(){

    }

    public DoctorClassNew(String doctorIDVar,String doctorNameVar, String doctorHospitalVar,
                          String doctorSpecialityVar, String doctorAvailibilityVar,
                          String doctorContactNoVar) {
        this.doctorNameVar = doctorNameVar;
        this.doctorHospitalVar = doctorHospitalVar;
        this.doctorSpecialityVar = doctorSpecialityVar;
        this.doctorAvailibilityVar = doctorAvailibilityVar;
        this.doctorContactNoVar = doctorContactNoVar;
        this.doctorIDVar = doctorIDVar;
    }


    public String getDoctorNameVar() {
        return doctorNameVar;
    }

    public String getDoctorHospitalVar() {
        return doctorHospitalVar;
    }

    public String getDoctorSpecialityVar() {
        return doctorSpecialityVar;
    }

    public String getDoctorAvailibilityVar() {
        return doctorAvailibilityVar;
    }

    public String getDoctorContactNoVar() {
        return doctorContactNoVar;
    }


    public String getDoctorIDVar() {
        return doctorIDVar;
    }
}
