package com.prince.logan.playdate.Interface;

import com.prince.logan.playdate.Model.RequestModel;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
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

    @POST("add_favorites")
    Call<RequestModel>addLike(
            @Query("function") String strFunction, @Query("email") String userMail, @Query("trackID") String trackId, @Query("stationName") String stationTitle
    );

    @POST("search_favorites")
    Call<RequestModel>get_fav_music(
            @Query("email") String strUserMail
    );

    @POST("getting_streaming")
    Call<RequestModel>getting_streaming(
            @Query("stream_id") String stream_id
    );

    @POST("forgot_password")
    Call<RequestModel>forgot_password(
            @Query("email") String user_email
    );

    @POST("reset_password")
    Call<RequestModel>reset_password(
            @Query("user_password") String user_password, @Query("user_id") String user_id
    );
}
