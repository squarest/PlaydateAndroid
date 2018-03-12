package com.make.playdate.preference.presentation.editprofile;

import com.make.playdate.base.BaseView;
import com.make.playdate.entities.UserModel;

/**
 * Created by dmitrijfomenko on 10.03.2018.
 */

public interface EditProfileView extends BaseView {
    void setUser(UserModel userModel);
}
