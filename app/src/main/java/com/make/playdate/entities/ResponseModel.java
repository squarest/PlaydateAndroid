package com.make.playdate.entities;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by PRINCESS on 10/15/2017.
 */

public class ResponseModel {
    private UserModel user;
    private ArrayList<QuestionModel> cateQuestion;
    private ArrayList<PlaydateModel> playdates;
    private ArrayList<SubCateModel> subCategory;
    private ArrayList<QuestionModel> answers;
    private List<List<PlaydateModel>> groupedPlaydates;
    private PlaydateModel playdate;
    private String answer_list;

    public String getAnswer_list() {
        return answer_list;
    }

    public PlaydateModel getPlaydate() {
        return playdate;
    }

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

    public ArrayList<PlaydateModel> getMatchedUsers() {
        return playdates;
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
