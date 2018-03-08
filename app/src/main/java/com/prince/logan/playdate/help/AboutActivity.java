package com.prince.logan.playdate.help;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.prince.logan.playdate.R;

import butterknife.BindView;

/**
 * Created by PRINCE on 11/15/2017.
 */

public class AboutActivity extends Activity implements ImageView.OnClickListener {

    @BindView(R.id.img_about_back)
    ImageView back;
    @BindView(R.id.txt_twitter)
    TextView txtTwitter;
    @BindView(R.id.lin_about_vision)
    LinearLayout linVision;
    @BindView(R.id.lin_about_knowledge)
    LinearLayout linKnowledge;
    @BindView(R.id.switch_push_notification)
    Switch notification_enable;

//    @Override
//    protected void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_about);
//        ButterKnife.bind(this);
//
//        back.setOnClickListener(this);
//        linKnowledge.setOnClickListener(this);
//        linVision.setOnClickListener(this);
//
//        txtTwitter.setText("@makeplaydate");
//
//        if(MainActivity.userProfile.getIs_pushnotification() == 0){
//            notification_enable.setChecked(false);
//        }
//        else{
//            notification_enable.setChecked(true);
//        }
//
//
//        notification_enable.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                Log.v("Switch State=", ""+isChecked);
//                if(isChecked){
//                    updateNotification(1, MainActivity.userProfile.get_firebase_id());
//                }
//                else{
//                    updateNotification(0, MainActivity.userProfile.get_firebase_id());
//                }
//            }
//
//        });
//    }

//    private void updateNotification(final int isChecked, String user_id) {
//        final ProgressDialog loading = new ProgressDialog(this, R.style.AppTheme_Dark_Dialog);
//        loading.setIndeterminate(true);
//        loading.setMessage("Please wait...");
//        loading.show();
//
//        API apiService = ApiClient.getApi().create(API.class);
//
//        Call<RequestModel> req = apiService.update_notification(isChecked, user_id);
//        req.enqueue(new Callback<RequestModel>() {
//            @Override
//            public void onResponse(Call<ResponseModel> call, retrofit2.Response<ResponseModel> response) {
//                RequestModel responseData = response.body();
//                loading.dismiss();
//                MainActivity.userProfile.setIs_pushnotification(isChecked);
//            }
//
//            @Override
//            public void onFailure(Call<RequestModel> call, Throwable t) {
//                t.printStackTrace();
//                loading.dismiss();
//            }
//        });
//    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_about_back:
                this.finish();
                break;
            case R.id.lin_about_vision:
                showAlert("Vision", "Our vision is to make it easy to meet new people and have fun");
                break;
            case R.id.lin_about_knowledge:
                showAlert("Acknowledgements", "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.");
                break;
        }
    }

    public void showAlert(String title, String msg) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);

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
