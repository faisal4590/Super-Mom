package com.faisal.supermom;

/**
 * Created by faisal on 10/16/2017.
 */

public class AdminHealthMonitorInfoClass {
    String height, weight,age;

    AdminHealthMonitorInfoClass(){

    }

    public AdminHealthMonitorInfoClass(String height, String weight, String age) {
        this.height = height;
        this.weight = weight;
        this.age = age;
    }

    public String getHeight() {
        return height;
    }

    public String getWeight() {
        return weight;
    }

    public String getAge() {
        return age;
    }
}
