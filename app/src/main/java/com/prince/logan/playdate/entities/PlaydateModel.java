package com.prince.logan.playdate.entities;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by dmitrijfomenko on 05.03.2018.
 */

public class PlaydateModel implements Serializable {

    @SerializedName("id")
    public String id;

    @SerializedName("firebase_id")
    public String firebaseId;

    @SerializedName("user_full_name")
    public String userFullName;

    @SerializedName("user_first_name")
    public String userFirstName;

    @SerializedName("user_last_name")
    public String userLastName;

    @SerializedName("gender")
    public String gender;

    @SerializedName("avatar_url")
    public String avatarUrl;

    @SerializedName("user_mail")
    public String userMail;

    @SerializedName("user_playdate")
    public String userPlaydate;

    @SerializedName("user_match")
    public String userMatch;

    @SerializedName("user_lati")
    public String userLati;

    @SerializedName("user_longi")
    public String userLongi;

    @SerializedName("user_age")
    public String userAge;

    @SerializedName("user_birthday")
    public Object userBirthday;

    @SerializedName("user_friends")
    public Object userFriends;

    @SerializedName("user_ethnicity")
    public String userEthnicity;

    @SerializedName("is_premium")
    public String isPremium;

    @SerializedName("is_pushnotification")
    public String isPushnotification;

    @SerializedName("updated_at")
    public String updatedAt;

    @SerializedName("created_at")
    public String createdAt;

    @SerializedName("user_id")
    public String userId;

    @SerializedName("cate_id")
    public String cateId;

    @SerializedName("sub_cate_id")
    public String subCateId;

    @SerializedName("playdate_id")
    public String playdateId;

    @SerializedName("compatibility")
    public String compatibility;

    @SerializedName("date_created")
    public String dateCreated;

    @SerializedName("month_created")
    public String monthCreated;

    @SerializedName("day_created")
    public String dayCreated;

    @SerializedName("day_of_week_created")
    public String dayOfWeekCreated;

    @SerializedName("is_active")
    public String isActive;

    @SerializedName("chat_active")
    public String chatActive;

    public double distance;

    public List<QAModel> questions;


}
