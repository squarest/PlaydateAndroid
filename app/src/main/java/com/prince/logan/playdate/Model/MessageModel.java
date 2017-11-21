package com.prince.logan.playdate.Model;

/**
 * Created by PRINCE on 11/20/2017.
 */

public class MessageModel {
    private String content;
    public MessageModel(){

    }
    public MessageModel(String content){
        this.content = content;
    }

    public String getContent(){
        return content;
    }

    public void setContent(String content){
        this.content = content;
    }
}
