package com.make.playdate.playdate.presentation.details;

import com.make.playdate.base.BaseView;
import com.make.playdate.entities.PlaydateModel;

/**
 * Created by dmitrijfomenko on 07.03.2018.
 */

public interface PlaydateDetailView extends BaseView {
    void setUserData(PlaydateModel playdateModel);

    void showChat(PlaydateModel playdateModel);
}
