package com.faisal.supermom;

/**
 * Created by faisal on 8/21/2017.
 */

public class adminBabyInfo {

    String babyInfoID, babyInfoHeadline,babyInfo;

    adminBabyInfo(){

    }

    public adminBabyInfo(String babyInfoID, String babyInfoHeadline, String babyInfo) {
        this.babyInfoID = babyInfoID;
        this.babyInfoHeadline = babyInfoHeadline;
        this.babyInfo = babyInfo;

    }

    public String getBabyInfoID() {
        return babyInfoID;
    }

    public String getBabyInfoHeadline() {
        return babyInfoHeadline;
    }

    public String getBabyInfo() {
        return babyInfo;
    }

}
