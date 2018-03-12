package com.make.playdate.playdate.data;

import android.location.Location;

import com.make.playdate.entities.PlaydateModel;
import com.make.playdate.entities.QuestionModel;
import com.make.playdate.entities.ResponseModel;

import java.util.List;

import io.reactivex.Single;

/**
 * Created by dmitrijfomenko on 07.03.2018.
 */

public interface IPlaydateRepo {
    Single<ResponseModel> postMakePlaydate();

    Single<List<PlaydateModel>> getMatchedUsers();

    Single<List<QuestionModel>> get_questions();

    Single<String[]> getAnswers(String uId);

    double getDistanceTo(Location location);
}
