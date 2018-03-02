package com.prince.logan.playdate.auth.data;

import android.os.Bundle;

import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.iid.FirebaseInstanceId;
import com.prince.logan.playdate.entities.RequestModel;
import com.prince.logan.playdate.entities.UserModel;
import com.prince.logan.playdate.network.ApiClient;

import org.json.JSONException;

import io.reactivex.Single;

/**
 * Created by dmitrijfomenko on 27.02.2018.
 */

public class LoginRepo implements ILoginRepo {
    private ApiClient apiClient;
    private FirebaseAuth firebaseAuth;

    public LoginRepo(ApiClient apiClient, FirebaseAuth auth) {
        this.apiClient = apiClient;
        firebaseAuth = auth;
    }


    @Override
    public String getUserId() {
        if (firebaseAuth.getCurrentUser() != null) return firebaseAuth.getUid();
        else return null;

    }

    @Override
    public Single<RequestModel> setTokenToServer() {
        String token = FirebaseInstanceId.getInstance().getToken();
        return apiClient.getApi().register_token(token, 0, getUserId());
    }

    @Override
    public Single<UserModel> getUserFromFacebook() {
        return Single.create(e ->
        {
            AccessToken accessToken = AccessToken.getCurrentAccessToken();
            if (accessToken != null) {
                GraphRequest request = GraphRequest.newMeRequest(
                        accessToken,
                        (object, response) -> {
                            // Application code
                            try {
                                String facebook_id = object.getString("id");
                                String firstName = object.getString("first_name");
                                String lastName = object.getString("last_name");
                                String email = object.getString("email");
                                String fullName = response.getJSONObject().getString("name");
                                String gender = response.getJSONObject().getString("gender");
                                String image_url = "https://graph.facebook.com/" + facebook_id + "/picture?type=large";
                                UserModel userModel = new UserModel();
                                userModel.setUserModel(getUserId(), fullName, firstName, lastName, gender, image_url, email);
                                e.onSuccess(userModel);

                            } catch (JSONException ex) {
                                e.onError(ex);
                            }
                        });
                Bundle parameters = new Bundle();
                parameters.putString("fields", "email, name, birthday, education, hometown, gender, inspirational_people, languages, first_name, last_name, relationship_status, friends,photos");
                request.setParameters(parameters);
                request.executeAndWait();
            }
        });
    }


    @Override
    public Single<RequestModel> loginToServer() {
        return apiClient.getApi().login(getUserId());
    }

    @Override
    public Single<RequestModel> signUpToServer(UserModel userModel) {
        return apiClient.getApi().signup(userModel);
    }


}
