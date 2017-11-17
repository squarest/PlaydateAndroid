package com.prince.logan.playdate.Model;

import java.util.ArrayList;

/**
 * Created by PRINCESS on 10/15/2017.
 */

public class RequestModel {
    UserModel user;
    private ArrayList<QuestionModel> cateQuestion;
    private String msg;
    private int result;

    public UserModel getUser(){
        return user;
    }

    public ArrayList<QuestionModel> getCateQuestion(){
        return cateQuestion;
    }

    public String getMsg(){
        return msg;
    }

    public int getResult(){
        return result;
    }
}
