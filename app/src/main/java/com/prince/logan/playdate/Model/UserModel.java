package com.prince.logan.playdate.Model;

import java.io.Serializable;

/**
 * Created by PRINCE on 11/15/2017.
 */

public class UserModel implements Serializable {

    private String facebook_id;
    private String user_full_name;
    private String user_first_name;
    private String user_last_name;
    private String gender;
    private String avatar_url;
    private String user_mail;

    public String get_facebook_id(){
        return facebook_id;
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

    public void setUserModel(String facebook_id, String user_full_name, String user_first_name, String user_last_name, String gender, String avatar_url, String user_mail) {
        this.facebook_id = facebook_id;
        this.user_full_name = user_full_name;
        this.user_first_name = user_first_name;
        this.user_last_name = user_last_name;
        this.gender = gender;
        this.avatar_url = avatar_url;
        this.user_mail= user_mail;
    }

}
