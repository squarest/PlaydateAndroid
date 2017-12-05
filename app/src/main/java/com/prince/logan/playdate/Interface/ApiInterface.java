package com.prince.logan.playdate.Interface;

import com.prince.logan.playdate.Model.RequestModel;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by PRINCE on 10/13/2017.
 */

public interface ApiInterface {

    @POST("login")
    Call<RequestModel> login(
            @Query("user_id") String userFirebaseID
    );

    @POST("signup")
    Call<RequestModel>signup(
            @Query("firebase_id") String firebase_id, @Query("user_full_name") String user_full_name, @Query("user_first_name") String user_first_name, @Query("user_last_name") String user_last_name, @Query("gender") String gender, @Query("avatar_url") String avatar_url, @Query("user_mail") String user_mail
    );

    @GET("get_category")
    Call<RequestModel> get_category();

    @POST("get_matched_users")
    Call<RequestModel> get_matched_users(
            @Query("user_id") String user_id, @Query("cate_id") int cate_id, @Query("sub_cate_id") int sub_cate_id
    );

    @POST("get_all_users")
    Call<RequestModel> get_all_users(
            @Query("user_id") String user_id
    );

    @GET("get_sub_category/{id}")
    Call<RequestModel> get_sub_category(
            @Path("id") int questionCateId
    );

    @GET("getQuestionsById/{id}")
    Call<RequestModel> get_questions(
            @Path("id") int questionCateId
    );

    @POST("report_bug")
    Call<RequestModel> report_bug(
            @Query("name") String name, @Query("mail") String mail, @Query("message") String message
    );

    @POST("set_user_playdate")
    Call<RequestModel> set_user_playdate(
            @Query("firebase_id") String firebaseId, @Query("cate_id") int cateId
    );

    @POST("saving_answers")
    Call<RequestModel> saving_answers(
            @Query("cate_id") int cateId, @Query("sub_cate_id") int subId, @Query("user_id") String userId, @Query("answers") String answer
    );

    @POST("check_answers")
    Call<RequestModel> check_answers(
            @Query("cate_id") int cateId, @Query("sub_cate_id") int subId, @Query("user_id") String userId
    );

    @POST("update_answers")
    Call<RequestModel> update_answers(
            @Query("answer_id") int answerId, @Query("answers") String question
    );

    @GET("getting_answered_question/{id}")
    Call<RequestModel> getting_answered_question(
            @Path("id") int answerId
    );

    @GET("delete_user/{id}")
    Call<RequestModel> delete_user(
            @Path("id") String firebase_id
    );
}
