package com.prince.logan.playdate.network;

import com.prince.logan.playdate.entities.ResponseModel;
import com.prince.logan.playdate.entities.UserModel;

import io.reactivex.Single;
import retrofit2.Call;
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

    @POST("get_all_users")
    Single<ResponseModel> get_all_users(
            @Query("user_id") String user_id
    );

    @GET("getQuestionsById/25")
    Single<ResponseModel> get_questions();

    @GET("get_answers/{id}")
    Single<ResponseModel> getAnswers(@Path("id") String userId);

    @GET("get_sub_category/{id}")
    Call<ResponseModel> get_sub_category(
            @Path("id") int questionCateId
    );

    @POST("report_bug")
    Call<ResponseModel> report_bug(
            @Query("name") String name, @Query("mail") String mail, @Query("message") String message
    );

    @POST("set_user_playdate")
    Call<ResponseModel> set_user_playdate(
            @Query("firebase_id") String firebaseId, @Query("cate_id") int cateId
    );

    @POST("saving_answers")
    Call<ResponseModel> saving_answers(
            @Query("cate_id") int cateId, @Query("sub_cate_id") int subId, @Query("user_id") String userId, @Query("answers") String answer
    );

    @POST("check_answers")
    Call<ResponseModel> check_answers(
            @Query("cate_id") int cateId, @Query("sub_cate_id") int subId, @Query("user_id") String userId
    );

    @POST("update_answers")
    Call<ResponseModel> update_answers(
            @Query("answer_id") int answerId, @Query("answers") String question
    );

    @GET("getting_answered_question/{id}")
    Call<ResponseModel> getting_answered_question(
            @Path("id") int answerId
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
    Call<ResponseModel> edit_user(
            @Query("firebase_id") String firebase_id,
            @Query("user_age") int user_age,
            @Query("user_height") String user_height,
            @Query("user_ethnicity") int user_ethnicity,
            @Query("user_religion") int user_religion,
            @Query("user_education") int user_education,
            @Query("height_option") int heightOption
    );

    @POST("send_notification")
    Single<ResponseModel> send_notification(
            @Query("own_name") String own_name, @Query("own_id") String own_id, @Query("user_id") String user_id, @Query("message") String message
    );

    @POST("update_location")
    Single<ResponseModel> update_location(
            @Query("user_id") String id, @Query("lati") String lati, @Query("longi") String longi
    );

    @POST("update_setting")
    Call<ResponseModel> update_setting(
            @Query("user_id") String id,
            @Query("looking_for") int looking_for,
            @Query("age_from") int age_from,
            @Query("age_to") int age_to,
            @Query("height_from") float height_from,
            @Query("height_to") float height_to,
            @Query("distance") int distance,
            @Query("ethnicity") String ethnicity,
            @Query("religion") String religion,
            @Query("education") String education
    );

    @POST("update_notification_setting")
    Call<ResponseModel> update_notification(
            @Query("notification") int notification,
            @Query("user_id") String id
    );
}
