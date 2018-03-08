package com.prince.logan.playdate.playdate.presentation.details;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.prince.logan.playdate.R;
import com.prince.logan.playdate.chat.presentation.chat.ChatActivity;
import com.prince.logan.playdate.entities.UserModel;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by PRINCE on 11/14/2017.
 */

public class PlaydateDetailActivity extends Activity implements View.OnClickListener{

    @BindView(R.id.img_playdate_detail_back)
    ImageView back;
    @BindView(R.id.txt_playdate_user_name)
    TextView txtUserName;
    @BindView(R.id.img_playdate_profile)
    ImageView imgProfileImage;
    @BindView(R.id.playdate_detail_list)
    ListView playdateDetailList;
    @BindView(R.id.btn_chat_start)
    Button btn_start_chat;

    UserModel userDetail;


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
