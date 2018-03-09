package com.prince.logan.playdate.playdate.presentation.details;

import com.prince.logan.playdate.base.BaseView;
import com.prince.logan.playdate.entities.PlaydateModel;

/**
 * Created by dmitrijfomenko on 07.03.2018.
 */

public interface PlaydateDetailView extends BaseView {
    void setUserData(PlaydateModel playdateModel);

    void showChat(PlaydateModel playdateModel);
}
