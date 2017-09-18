package com.faisal.supermom;

/**
 * Created by faisal on 9/16/2017.
 */

public class NewNewsfeedClass {

    String newBabyInfoID, newBabyInfoHeadline,newBabyInfo,newNewsfeedImageName,newNewsfeedImageUrl;

    NewNewsfeedClass(){

    }

    public NewNewsfeedClass(String newBabyInfoID, String newBabyInfoHeadline, String newBabyInfo,
                            String newNewsfeedImageName, String newNewsfeedImageUrl) {
        this.newBabyInfoID = newBabyInfoID;
        this.newBabyInfoHeadline = newBabyInfoHeadline;
        this.newBabyInfo = newBabyInfo;
        this.newNewsfeedImageName = newNewsfeedImageName;
        this.newNewsfeedImageUrl = newNewsfeedImageUrl;
    }

    public String getNewBabyInfoID() {
        return newBabyInfoID;
    }

    public String getNewBabyInfoHeadline() {
        return newBabyInfoHeadline;
    }

    public String getNewBabyInfo() {
        return newBabyInfo;
    }

    public String getNewNewsfeedImageName() {
        return newNewsfeedImageName;
    }

    public String getNewNewsfeedImageUrl() {
        return newNewsfeedImageUrl;
    }
}
