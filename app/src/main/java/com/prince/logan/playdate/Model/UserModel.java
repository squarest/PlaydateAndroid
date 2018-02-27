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
    private int user_age;
    private String  user_height;
    private int  height_option;
    private int user_ethnicity;
    private int user_religion;
    private int user_education;
    private int looking_for;
    private int age_from;
    private int age_to;
    private float height_from;
    private float height_to;
    private int distance;
    private String ethnicity;
    private String religion;
    private String education;
    private int is_pushnotification;
    private String user_id;

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

    public int getUser_age(){
        return user_age;
    }

    public String getUser_height(){
        return user_height;
    }

    public int getHeight_option(){
        return height_option;
    }

    public int getUser_ethnicity(){
        return user_ethnicity;
    }

    public int getUser_religion(){
        return user_religion;
    }

    public int getUser_education(){
        return user_education;
    }

    public int getLooking_for(){return looking_for;}

    public int getAge_from(){return age_from;}

    public int getAge_to(){return age_to;}

    public float getHeight_from(){return height_from;}

    public float getHeight_to(){return height_to;}

    public int getDistance(){return distance;}

    public String getEthnicity(){return ethnicity;}

    public String getReligion(){return religion;}

    public String getEducation(){return education;}

    public int getIs_pushnotification(){return is_pushnotification;}

    public void setUser_playdate(int cate_id){
        this.user_playdate = cate_id;
    }

    public void setIs_pushnotification(int notification){
        this.is_pushnotification = notification;
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
