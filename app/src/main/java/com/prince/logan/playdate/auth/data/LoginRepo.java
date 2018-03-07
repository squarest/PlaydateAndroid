package com.prince.logan.playdate.auth.data;

import android.os.Bundle;

import com.facebook.AccessToken;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.iid.FirebaseInstanceId;
import com.prince.logan.playdate.entities.RequestModel;
import com.prince.logan.playdate.entities.UserModel;
import com.prince.logan.playdate.network.ApiClient;

import io.reactivex.Single;
import io.reactivex.SingleEmitter;

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


    private String getUserId() {
        if (firebaseAuth.getCurrentUser() != null) return firebaseAuth.getUid();
        else return null;

    }

    @Override
    public boolean isCurrentUserExist() {
        return firebaseAuth.getCurrentUser() != null & AccessToken.getCurrentAccessToken() != null;
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
                AuthCredential credential = FacebookAuthProvider.getCredential(accessToken.getToken());
                firebaseAuth.signInWithCredential(credential)
                        .addOnCompleteListener(task ->
                        {
                            if (task.isSuccessful()) {
                                loadDataFromFacebook(accessToken, e);
                            } else e.onError(new Exception());

                        });
            } else e.onError(new FacebookException());
        });
    }

    private void loadDataFromFacebook(AccessToken accessToken, SingleEmitter<UserModel> e) {
        UserModel userModel = new UserModel();
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
                        String gender = "";
                        if (response.getJSONObject().has("gender")) {
                            gender = response.getJSONObject().getString("gender");
                        }
                        String image_url = "https://graph.facebook.com/" + facebook_id + "/picture?type=large";

                        userModel.setUserModel(getUserId(), fullName, firstName, lastName, gender, image_url, email);
                        e.onSuccess(userModel);

                    } catch (Exception ex) {
                        e.onError(ex);
                    }
                });
        Bundle parameters = new Bundle();
        parameters.putString("fields", "email, name, birthday, education, hometown, gender, inspirational_people, languages, first_name, last_name, relationship_status, friends,photos");
        request.setParameters(parameters);
        request.executeAsync();

    }

    @Override
    public Single<RequestModel> signUpToServer(UserModel userModel) {
        return apiClient.getApi().signup(userModel);
    }


}