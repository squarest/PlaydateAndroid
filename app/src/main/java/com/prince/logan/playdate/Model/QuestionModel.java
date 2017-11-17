package com.prince.logan.playdate.Model;

/**
 * Created by PRINCE on 11/16/2017.
 */

public class QuestionModel{
    private int id;
    private int qa_cate_id;
    private String category;
    private String questions;
    private String img_cate;

    public int getId(){
        return id;
    }

    public int getQa_cate_id() {
        return qa_cate_id;
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
}
