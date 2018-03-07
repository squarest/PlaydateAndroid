package com.prince.logan.playdate.entities;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by PRINCESS on 10/15/2017.
 */

public class RequestModel {
    UserModel user;
    private ArrayList<QuestionModel> cateQuestion;
    private ArrayList<UserModel> userList;
    private ArrayList<SubCateModel> subCategory;
    private ArrayList<QuestionModel> answers;
    private List<List<PlaydateModel>> groupedPlaydates;

    private String msg;
    private int result;
    private int answer_id;

    public UserModel getUser() {
        return user;
    }

    public ArrayList<QuestionModel> getCateQuestion() {
        return cateQuestion;
    }

    public ArrayList<QuestionModel> getAnswers() {
        return answers;
    }

    public ArrayList<UserModel> getMatchedUser() {
        return userList;
    }

    public List<List<PlaydateModel>> getGroupedPlaydates() {
        return groupedPlaydates;
    }

    public ArrayList<SubCateModel> getSubCategory() {
        return subCategory;
    }

    public String getMsg() {
        return msg;
    }

    public int getResult() {
        return result;
    }

    public int getAnswer_id() {
        return answer_id;
    }
}
