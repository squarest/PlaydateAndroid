package com.make.playdate.help;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.make.playdate.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by PRINCE on 11/22/2017.
 */

public class ReportActivity extends Activity {
    @BindView(R.id.ed_report)
    EditText edReport;
    @BindView(R.id.btn_send_report)
    Button btnSend;

    Context context;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_bug);
        ButterKnife.bind(this);

        context = this;

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                send_report();
            }
        });

    }

//    private void send_report() {
//        final ProgressDialog loading = new ProgressDialog(this, R.style.AppTheme_Dark_Dialog);
//        loading.setIndeterminate(true);
//        loading.setMessage("Please wait...");
//        loading.show();
//
//        String name = MainActivity.userProfile.get_user_full_name();
//        String mail = MainActivity.userProfile.get_user_mail();
//        String message = edReport.getText().toString();
//
//        API apiService = ApiClient.getApi().create(API.class);
//
//        Call<RequestModel> req = apiService.report_bug(name, mail, message);
//        req.enqueue(new Callback<RequestModel>() {
//            @Override
//            public void onResponse(Call<ResponseModel> call, retrofit2.Response<ResponseModel> response) {
//                loading.dismiss();
//                RequestModel responseData = response.body();
//
//                if (responseData.getResult() == 1){
//                    Toast.makeText(getApplicationContext(), responseData.getMsg(), Toast.LENGTH_LONG).show();
//                    ((Activity)context).finish();
//                }
//                else{
//                    showAlert("Alert", "Failed! Please try again!");
//                }
//            }
//
//            @Override
//            public void onFailure(Call<RequestModel> call, Throwable t) {
//                t.printStackTrace();
//                loading.dismiss();
//                showAlert("Alert", "Failed!");
//            }
//        });
//    }

    public void showAlert(String title, String msg){
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
