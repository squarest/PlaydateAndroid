package com.prince.logan.playdate.Model;

/**
 * Created by PRINCE on 12/6/2017.
 */

public class PreferenceItemModel {
    private String title;
    private int checked;

    public String getTitle(){
        return title;
    }
    public int getChecked(){
        return checked;
    }

    public void setTitle(String title){
        this.title = title;
    }

    public void setChecked(int i){
        this.checked = i;
    }
}
