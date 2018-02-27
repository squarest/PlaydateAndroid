package com.prince.logan.playdate.Activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.prince.logan.playdate.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by PRINCE on 11/20/2017.
 */

public class FAQDetailActivity extends Activity {

    Context context;
    @Bind(R.id.img_faq_detail_back)
    ImageView back;
    @Bind(R.id.txt_faq_title)
    TextView txtFaqTitle;
    @Bind(R.id.txt_faq_content)
    TextView txtFaqConent;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faq_detail);
        ButterKnife.bind(this);

        context = this;

        initView();

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((Activity)context).finish();
            }
        });
    }

    private void initView() {
        int faqIndex = -1;
        Intent intent = this.getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            faqIndex = bundle.getInt("faq_id");
        }

        switch (faqIndex){
            case 0:
                txtFaqTitle.setText("How does this all work?");
                txtFaqConent.setText("It’s easy as 1-2-3! \n(1) You log in with your Facebook account and set up your preferences. \n" +
                        "(2) You answer the questions in the Q&A section. \n(3) You get one playdate a day.");
                break;
            case 1:
                txtFaqTitle.setText("How do I request questions for an activity?");
                txtFaqConent.setText("You can send us feedback in the About section of the Menu. We always try to respond to you within 24 hours!");
                break;
            case 2:
                txtFaqTitle.setText("Why does chat only stay open for 24 hours?");
                txtFaqConent.setText("We feel that this follows the spirit of playdate and encourages actually meeting new people. " +
                        "\nIf you would like chat to remain open longer, " +
                        "please feel free to send us your feedback in the About section of the Menu!");
                break;
            case 3:
                txtFaqTitle.setText("Why do I only get one playdate a day?");
                txtFaqConent.setText("We believe that having only one playdate a day may incentivize people to value their playdates. " +
                        "\nFeel free to drop us a line in the About section of the Menu if you would like this to change. \nWe’ll respond as quickly as we can.");
                break;
            case 4:
                txtFaqTitle.setText("How can I contribute?");
                txtFaqConent.setText("Thank you for your interest! \nIf you have something to contribute, we would love to hear from you. " +
                        "\nContact us at logan@makeplaydate.com or in the About section of the Menu and feel free to tweet at us @makeplaydate. We look forward to hearing from you!");
                break;
        }
    }
}
