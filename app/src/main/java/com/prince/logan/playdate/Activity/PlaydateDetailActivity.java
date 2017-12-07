package com.prince.logan.playdate.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.prince.logan.playdate.Adapter.QuestionAdapter;
import com.prince.logan.playdate.Model.QuestionModel;
import com.prince.logan.playdate.Model.UserModel;
import com.prince.logan.playdate.R;
import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by PRINCE on 11/14/2017.
 */

public class PlaydateDetailActivity extends Activity implements View.OnClickListener{

    @Bind(R.id.img_playdate_detail_back)
    ImageView back;
    @Bind(R.id.txt_playdate_user_name)
    TextView txtUserName;
    @Bind(R.id.img_playdate_profile)
    ImageView imgProfileImage;
    @Bind(R.id.playdate_detail_list)
    ListView playdateDetailList;
    @Bind(R.id.btn_chat_start)
    Button btn_start_chat;

    UserModel userDetail;

    ArrayList<QuestionModel> arrayQuestion = new ArrayList<QuestionModel>();
    QuestionAdapter questionAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playdate_detail);
        ButterKnife.bind(this);

        initData();
        setEvent();
    }

    private void setEvent() {
        back.setOnClickListener(this);
        btn_start_chat.setOnClickListener(this);
    }

    private void initData() {
        Intent intent = this.getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            userDetail = (UserModel)bundle.getSerializable("userModel");
            initView();
        }
    }

    private void initView() {
        txtUserName.setText(userDetail.get_user_full_name());
        String avatarImg = userDetail.get_user_avatar();
        avatarImg = avatarImg.replace("http://", "https://");
        Picasso.with(this)
                .load(avatarImg)
                .placeholder(R.drawable.user) // optional
                .error(R.drawable.user)         // optional
                .into(imgProfileImage);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.img_playdate_detail_back:
                this.finish();
                break;
            case R.id.btn_chat_start:
                Intent startChattingIntent = new Intent(PlaydateDetailActivity.this, ChatActivity.class);
//                Bundle bundle = new Bundle();
//                bundle.putSerializable("userModel", (Serializable) userDetail);
//                startChattingIntent.putExtras(bundle);

                startChattingIntent.putExtra("user_id", userDetail.get_firebase_id());
                startChattingIntent.putExtra("user_name", userDetail.get_user_full_name());
                startActivity(startChattingIntent);
                break;
        }
    }
}
