package com.prince.logan.playdate.main.presentation.profile;

import com.prince.logan.playdate.base.BaseView;

/**
 * Created by dmitrijfomenko on 01.03.2018.
 */

public interface ProfileView extends BaseView {
    void setName(String name);

    void setProfilePhoto(String imageUrl);

    void setQuestionImage(String imageUrl);

}
