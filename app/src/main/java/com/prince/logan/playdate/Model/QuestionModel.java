package com.prince.logan.playdate.Model;

/**
 * Created by PRINCE on 11/16/2017.
 */

public class QuestionModel{
    private int id;
    private int sub_cate_id;
    private int cate_id;
    private String sub_cate;
    private String category;
    private String questions;
    private String img_cate;
    private int is_answer;

    public int getId(){
        return id;
    }

    public int getQa_cate_id() {
        return sub_cate_id;
    }

    public int getCate_id() {
        return cate_id;
    }

    public String getSub_cate(){
        return sub_cate;
    }

    public String getCategory() {
        return category;
    }

    public String getImgCate() {
        return img_cate;
    }

    public String getQuestions() {
        return questions;
    }

    public int getIs_answer(){
        return is_answer;
    }
}
