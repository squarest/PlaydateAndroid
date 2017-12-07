package com.prince.logan.playdate.Fragment;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.facebook.login.LoginManager;
import com.google.android.gms.vision.text.Line;
import com.google.firebase.auth.FirebaseAuth;
import com.prince.logan.playdate.Activity.AboutActivity;
import com.prince.logan.playdate.Activity.ChatListActivity;
import com.prince.logan.playdate.Activity.EditProfileActivity;
import com.prince.logan.playdate.Activity.FAQActivity;
import com.prince.logan.playdate.Activity.LoginActivity;
import com.prince.logan.playdate.Activity.MainActivity;
import com.prince.logan.playdate.Activity.PlaydateListActivity;
import com.prince.logan.playdate.Activity.PreferencesActivity;
import com.prince.logan.playdate.Interface.ApiClient;
import com.prince.logan.playdate.Interface.ApiInterface;
import com.prince.logan.playdate.Model.RequestModel;
import com.prince.logan.playdate.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;

/**
 * Created by Adib on 13-Apr-17.
 */

public class MenuFragment extends Fragment implements View.OnClickListener{

    private View mRootView;
    @Bind(R.id.lin_menu_about)
    LinearLayout btnAbout;
    @Bind(R.id.lin_menu_chats)
    LinearLayout btnChats;
    @Bind(R.id.lin_menu_faq)
    LinearLayout btnFaq;
    @Bind(R.id.lin_menu_feedback)
    LinearLayout btnFeedback;
    @Bind(R.id.lin_menu_playdate)
    LinearLayout btnPlaydate;
    @Bind(R.id.lin_menu_preference)
    LinearLayout btnPreference;
    @Bind(R.id.lin_menu_user_profile)
    LinearLayout btnEditProfile;
    @Bind(R.id.lin_menu_logout)
    LinearLayout btnLogout;
    @Bind(R.id.lin_menu_delete)
    LinearLayout btnDelete;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (mRootView == null){
            Log.e("666","MessageFragment");
            mRootView = inflater.inflate(R.layout.fragment_menu,container,false);
        }
        ViewGroup parent = (ViewGroup) mRootView.getParent();
        if (parent != null){
            parent.removeView(mRootView);
        }
        ButterKnife.bind(this, mRootView);
        return mRootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setEvent();
    }

    public void setEvent(){
        btnAbout.setOnClickListener(this);
        btnChats.setOnClickListener(this);
        btnFaq.setOnClickListener(this);
        btnPlaydate.setOnClickListener(this);
        btnPreference.setOnClickListener(this);
        btnFeedback.setOnClickListener(this);
        btnEditProfile.setOnClickListener(this);
        btnLogout.setOnClickListener(this);
        btnDelete.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.lin_menu_about:
                Intent aboutIntent = new Intent(getContext(), AboutActivity.class);
                startActivity(aboutIntent);
                getActivity().overridePendingTransition(R.anim.slide_in_from_right,
                        R.anim.slide_out_to_left);
                break;
            case R.id.lin_menu_faq:
                Intent faqIntent = new Intent(getContext(), FAQActivity.class);
                startActivity(faqIntent);
                getActivity().overridePendingTransition(R.anim.slide_in_from_right,
                        R.anim.slide_out_to_left);
                break;
            case R.id.lin_menu_playdate:
                if(MainActivity.isPlaydate){
//                    Intent playdateIntent = new Intent(getContext(), PlaydateListActivity.class);
//                    startActivity(playdateIntent);
//                    getActivity().overridePendingTransition(R.anim.slide_in_from_right,
//                            R.anim.slide_out_to_left);
                    MainActivity.mTabHost.setCurrentTab(2);
                }
                else{
                    showAlert("Warning", "You can't receive playdates. Please enable playdates in order to receive the playdates");
                }
                break;
            case R.id.lin_menu_chats:
                Intent chatIntent = new Intent(getContext(), ChatListActivity.class);
                startActivity(chatIntent);
                getActivity().overridePendingTransition(R.anim.slide_in_from_right,
                        R.anim.slide_out_to_left);
                break;
            case R.id.lin_menu_feedback:
                Intent i = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                        "mailto", "makeplaydate@gmail.com", null));
                i.putExtra(android.content.Intent.EXTRA_SUBJECT, "Report a bug");
                i.putExtra(android.content.Intent.EXTRA_TEXT, "");
                startActivity(Intent.createChooser(i, "Send email"));
                getActivity().overridePendingTransition(R.anim.slide_in_from_right,
                        R.anim.slide_out_to_left);
                break;
            case R.id.lin_menu_preference:
                Intent preferenceIntent = new Intent(getContext(), PreferencesActivity.class);
                startActivity(preferenceIntent);
                getActivity().overridePendingTransition(R.anim.slide_in_from_right,
                        R.anim.slide_out_to_left);
                break;
            case R.id.lin_menu_user_profile:
                Intent profileIntent = new Intent(getActivity(), EditProfileActivity.class);
                startActivity(profileIntent);
                getActivity().overridePendingTransition(R.anim.slide_in_from_right,
                        R.anim.slide_out_to_left);
                break;
            case R.id.lin_menu_logout:
                FirebaseAuth.getInstance().signOut();
                LoginManager.getInstance().logOut();
                Intent loginIntent = new Intent(getActivity(), LoginActivity.class);
                startActivity(loginIntent);
                getActivity().overridePendingTransition(R.anim.slide_in_from_right,
                        R.anim.slide_out_to_left);
                getActivity().finish();
                break;
            case R.id.lin_menu_delete:
                delete_user();
                break;
        }
    }

    private void delete_user() {
        final ProgressDialog loading = new ProgressDialog(getContext(), R.style.AppTheme_Dark_Dialog);
        loading.setIndeterminate(true);
        loading.setMessage("Please wait...");
        loading.show();

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

        Call<RequestModel> req = apiService.delete_user(MainActivity.userProfile.get_firebase_id());
        req.enqueue(new Callback<RequestModel>() {
            @Override
            public void onResponse(Call<RequestModel> call, retrofit2.Response<RequestModel> response) {
                RequestModel responseData = response.body();
                loading.dismiss();
                if (responseData.getResult() == 1){
                    FirebaseAuth.getInstance().signOut();
                    LoginManager.getInstance().logOut();
                    Intent loginIntent = new Intent(getActivity(), LoginActivity.class);
                    startActivity(loginIntent);
                    getActivity().finish();
                }
                else{
                    Toast.makeText(getContext(), "Account delete is failed", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<RequestModel> call, Throwable t) {
                t.printStackTrace();
                loading.dismiss();
                Toast.makeText(getContext(), "Server problem!", Toast.LENGTH_LONG).show();
            }
        });
    }

    public void showAlert(String title, String msg){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getContext());

        // Dialog Title
        alertDialog.setTitle(title);
        // Dialog Message
        alertDialog.setMessage(msg);
        // on pressing cancel button
        alertDialog.setNegativeButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        // Showing Alert Message
        alertDialog.show();
    }
}
