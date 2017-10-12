package com.faisal.supermom;

/**
 * Created by faisal on 10/11/2017.
 */

public class NewBabyInfoProfileClass {

    String newBabyID,newBabyName,birthDate,birthMonth,birthYear;

    NewBabyInfoProfileClass(){

    }

    public NewBabyInfoProfileClass(String newBabyID, String newBabyName, String birthDate, String birthMonth, String birthYear) {
        this.newBabyID = newBabyID;
        this.newBabyName = newBabyName;
        this.birthDate = birthDate;
        this.birthMonth = birthMonth;
        this.birthYear = birthYear;
    }

    public String getNewBabyID() {
        return newBabyID;
    }

    public String getNewBabyName() {
        return newBabyName;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public String getBirthMonth() {
        return birthMonth;
    }

    public String getBirthYear() {
        return birthYear;
    }
}
