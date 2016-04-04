package com.example.daddyz.turtleboys.subclasses;

import com.parse.ParseClassName;
import com.parse.ParseFile;
import com.parse.ParseGeoPoint;
import com.parse.ParseUser;

import java.util.Date;

/**
 * Created by snow on 6/14/2015.
 */
@ParseClassName("_User")
public class GigUser extends ParseUser {
    private String userId;

    public GigUser(){

    }


    //getters
    public ParseGeoPoint getUserHome(){
        ParseGeoPoint point = getParseGeoPoint("userHome");
        return point;
    }

    public ParseFile getUserImage(){
        ParseFile user = getParseFile("userImage");
        return user;
    }
    public Date getBirthday(){
        Date user = getDate("birthday");
        return user;
    }
    public String getFirstName(){
        String first = getString("firstName");
        return first;
    }
    public String getLastName(){
        String first = getString("lastName");
        return first;
    }


    public Number getUserLevel(){
        Number UserLevel = getNumber("userLevel");
        return UserLevel;
    }
    public Number getUserTotalPost(){
        Number UserTotalPost = getNumber("userTotalPost");
        return UserTotalPost;
    }
    public Number getUserEventsAttended(){
        Number UserEventsAttended = getNumber("userEventsAttended");
        return UserEventsAttended;
    }

    public String getApiKey() {
        String apiKey = getString("apiKey");
        return apiKey;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }


    //setters
    public void setUserImage(ParseFile file){
        put("userImage",file);

    }
    public void setBirthday(Date date){
        put("birthday",date);
    }
    public void setFirstName(String first){
        put("firstName",first);
    }
    public void setLastName(String last){ put("lastName",last); }
    public void setUserLevel(Number UserLevel){
        put("userLevel",UserLevel);
    }
    public void setUserTotalPost(Number UserTotalPost){
        put("userTotalPost",UserTotalPost);
    }
    public void setUserEventsAttended(Number UserEventsAttended){ put("userEventsAttended",UserEventsAttended); }
    public void setApiKey(String key){
        put("apiKey",key);
    }
    public void setUserHome(ParseGeoPoint latlon) { put("userHome",latlon);}

}
