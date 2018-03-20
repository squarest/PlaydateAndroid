package com.make.playdate.network;

import com.make.playdate.entities.ResponseModel;
import com.make.playdate.entities.UserModel;

import io.reactivex.Single;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by PRINCE on 10/13/2017.
 */

public interface API {

    @POST("login")
    Single<ResponseModel> login(
            @Query("user_id") String userFirebaseID
    );

    @POST("signup")
    Single<ResponseModel> signup(@Body UserModel userModel);

    @GET("get_category")
    Single<ResponseModel> get_category();

    @POST("get_matched_users")
    Single<ResponseModel> getMatchedUsers(@Query("user_id") String user_id);

    @POST("make_play_date/{id}")
    Single<ResponseModel> postMakePLaydate(@Path("id") String user_id);

    @GET("get_grouped_matched_users")
    Single<ResponseModel> getGroupedMatchedUsers(
            @Query("user_id") String user_id);

    @GET("getQuestionsById/25")
    Single<ResponseModel> get_questions();

    @GET("get_answers/{id}")
    Single<ResponseModel> getAnswers(@Path("id") String userId);

    @POST("flag_user")
    Single<ResponseModel> flagUser(@Query("user_id") String userId, @Query("flaggee_id") String flaggeeId);


    @POST("saving_answers")
    Single<ResponseModel> saving_answers(
            @Query("cate_id") int cateId, @Query("sub_cate_id") int subId, @Query("user_id") String userId, @Query("answers") String answer
    );

    @POST("check_answers")
    Single<ResponseModel> check_answers(
            @Query("cate_id") int cateId, @Query("sub_cate_id") int subId, @Query("user_id") String userId
    );

    @GET("delete_user/{id}")
    Single<ResponseModel> delete_user(
            @Path("id") String firebase_id
    );

    @POST("register_token")
    Single<ResponseModel> register_token(
            @Query("token") String token, @Query("platform") int platform, @Query("user_id") String user_id
    );

    @POST("edit_user")
    Single<ResponseModel> edit_user(@Body UserModel userModel);

    @POST("send_notification")
    Single<ResponseModel> send_notification(
            @Query("own_name") String own_name, @Query("own_id") String own_id, @Query("user_id") String user_id, @Query("message") String message
    );

    @POST("update_location")
    Single<ResponseModel> update_location(
            @Query("user_id") String id, @Query("lati") String lati, @Query("longi") String longi
    );

    @POST("update_setting")
    Single<ResponseModel> update_setting(@Body UserModel userModel);

    @POST("update_notification_setting")
    Single<ResponseModel> update_notification(
            @Query("notification") int notification,
            @Query("user_id") String id
    );

    @POST("set_user_playdate")
    Single<ResponseModel> set_user_playdate(@Query("firebase_id") String firebaseId, @Query("user_playdate") int userPlaydate);
}
