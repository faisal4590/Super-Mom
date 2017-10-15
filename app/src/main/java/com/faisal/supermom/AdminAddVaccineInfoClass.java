package com.faisal.supermom;

/**
 * Created by faisal on 10/13/2017.
 */

public class AdminAddVaccineInfoClass {

    String id,vaacineLocation,vaccineDetails,vaccineAvailableFor;

    public AdminAddVaccineInfoClass(){

    }

    public AdminAddVaccineInfoClass(String id, String vaacineLocation, String vaccineDetails, String vaccineAvailableFor) {
        this.id = id;
        this.vaacineLocation = vaacineLocation;
        this.vaccineDetails = vaccineDetails;
        this.vaccineAvailableFor = vaccineAvailableFor;
    }

    public String getId() {
        return id;
    }

    public String getVaacineLocation() {
        return vaacineLocation;
    }

    public String getVaccineDetails() {
        return vaccineDetails;
    }

    public String getVaccineAvailableFor() {
        return vaccineAvailableFor;
    }
}
