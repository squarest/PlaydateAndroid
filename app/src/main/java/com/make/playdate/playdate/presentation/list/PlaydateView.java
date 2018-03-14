package com.make.playdate.playdate.presentation.list;

import com.make.playdate.base.BaseView;
import com.make.playdate.entities.PlaydateModel;

import java.util.List;

/**
 * Created by dmitrijfomenko on 07.03.2018.
 */

public interface PlaydateView extends BaseView {
    void setPlaydates(List<PlaydateModel> playdates);

    void addNewPlaydate(PlaydateModel playdateModel);

    void showPlaydateFailedDialog();

    void showMessage(int messageId);
}