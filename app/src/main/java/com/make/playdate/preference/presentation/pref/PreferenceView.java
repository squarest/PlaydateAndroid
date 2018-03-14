package com.make.playdate.preference.presentation.pref;

import com.make.playdate.base.BaseView;
import com.make.playdate.entities.UserModel;

/**
 * Created by dmitrijfomenko on 10.03.2018.
 */

public interface PreferenceView extends BaseView{
    void setPreferences(UserModel userModel);
}
