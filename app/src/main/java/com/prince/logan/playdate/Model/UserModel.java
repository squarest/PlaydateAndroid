package com.prince.logan.playdate.Model;

import java.io.Serializable;

/**
 * Created by PRINCE on 11/15/2017.
 */

public class UserModel implements Serializable {

    private String firebase_id;
    private String user_full_name;
    private String user_first_name;
    private String user_last_name;
    private String gender;
    private String avatar_url;
    private String user_mail;
    private int user_playdate;
    private String user_match;
    private float user_lati;
    private float user_longi;

    public String get_firebase_id(){
        return firebase_id;
    }

    public String get_user_full_name() {
        return user_full_name;
    }

    public String get_user_first_name() {
        return user_first_name;
    }

    public String get_user_last_name() {
        return user_last_name;
    }

    public String get_user_gender() {
        return gender;
    }

    public String get_user_mail() {
        return user_mail;
    }

    public String get_user_avatar() {
        return avatar_url;
    }

    public int getUser_playdate(){
        return user_playdate;
    }

    public String getUser_match(){
        return user_match;
    }

    public float getUser_lati(){
        return user_lati;
    }

    public float getUser_longi(){
        return user_longi;
    }

    public void setUser_playdate(int cate_id){
        this.user_playdate = cate_id;
    }

    public void setUser_match(String match){
        this.user_match = match;
    }

    public void setUserModel(String firebaseId, String userFullName, String userFirstName, String userLastName, String userGender, String avatarUrl, String userMail) {
        this.firebase_id = firebaseId;
        this.user_full_name = userFullName;
        this.user_first_name = userFirstName;
        this.user_last_name = userLastName;
        this.gender = userGender;
        this.avatar_url = avatarUrl;
        this.user_mail= userMail;
    }

}
