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

    @GET("getQuestionsById/{id}")
    Call<RequestModel> get_questions(
            @Path("id") int questionCateId
    );
}
