package com.prince.logan.playdate.fcm;

import android.app.ProgressDialog;
import android.util.Log;
import android.widget.Toast;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;
import com.prince.logan.playdate.main.presentation.main.MainActivity;
import com.prince.logan.playdate.network.ApiClient;
import com.prince.logan.playdate.network.API;
import com.prince.logan.playdate.entities.RequestModel;
import com.prince.logan.playdate.R;

import retrofit2.Call;
import retrofit2.Callback;

/**
 * Created by Developer on 6/29/2016.
 */
public class MyFirebaseInstanceIDService extends FirebaseInstanceIdService {

    private static final String TAG = "MyFirebaseIIDService";

    /**
     * Called if InstanceID token is updated. This may occur if the security of
     * the previous token had been compromised. Note that this is called when the InstanceID token
     * is initially generated so this is where you would retrieve the token.
     */
    // [START refresh_token]
    @Override
    public void onTokenRefresh() {
        // Get updated InstanceID token.
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.e(TAG, "Refreshed token: " + refreshedToken);

        // TODO: Implement this method to send any registration to your app's servers.
//        sendRegistrationToServer(refreshedToken);
    }
    // [END refresh_token]

    /**
     * Persist token to third-party servers.
     *
     * Modify this method to associate the user's FCM InstanceID token with any server-side account
     * maintained by your application.
     *
     * @param token The new token.
     */
    private void sendRegistrationToServer(final String token) {

        final ProgressDialog loading = new ProgressDialog(this, R.style.AppTheme_Dark_Dialog);
        loading.setIndeterminate(true);
        loading.setMessage("Please wait...");
        loading.show();

        API apiService = ApiClient.getApi().create(API.class);

        Call<RequestModel> req = apiService.register_token(token, 0, MainActivity.userFirebaseID);
        req.enqueue(new Callback<RequestModel>() {
            @Override
            public void onResponse(Call<RequestModel> call, retrofit2.Response<RequestModel> response) {
                RequestModel responseData = response.body();
                loading.dismiss();
                if (responseData.getResult() == 1){
                    Toast.makeText(getApplicationContext(), "Token Registered!", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(getApplicationContext(), "Token Registration Failed", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<RequestModel> call, Throwable t) {
                t.printStackTrace();
                loading.dismiss();
            }
        });
    }
}