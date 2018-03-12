package com.make.playdate.main.presentation.profile;

import com.make.playdate.base.BaseView;

/**
 * Created by dmitrijfomenko on 01.03.2018.
 */

public interface ProfileView extends BaseView {
    void setName(String name);

    void setProfilePhoto(String imageUrl);

    void setQuestionImage(String imageUrl);

}
