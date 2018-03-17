package com.make.playdate.help.faq;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ImageView;
import android.widget.ListView;

import com.make.playdate.R;

import java.util.ArrayList;


/**
 * Created by PRINCE on 11/15/2017.
 */

public class FAQActivity extends Activity {

    private ListView listFaq;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faq);

        ImageView backButton = findViewById(R.id.back_button);
        backButton.setOnClickListener(view -> onBackPressed());
        listFaq = findViewById(R.id.list_faq);
        listFaq.setOnItemClickListener((adapterView, view, i, l) -> {
            Intent detailIntent = new Intent(FAQActivity.this, FAQDetailActivity.class);
            detailIntent.putExtra("faq_id", i);
            startActivity(detailIntent);
        });
        initView();
    }

    private void initView() {
        ArrayList<String> faqList = getItems();
        FaqAdapter adapter_faq = new FaqAdapter(this, R.layout.adapter_faq, faqList);
        listFaq.setAdapter(adapter_faq);
        adapter_faq.notifyDataSetChanged();
    }

    private ArrayList<String> getItems() {

        ArrayList<String> faq_list = new ArrayList<>();

        faq_list.add("How does this all work?");
        faq_list.add("How do I request questions for an activity?");
        faq_list.add("Why does chat only stay open for 24 hours?");
        faq_list.add("Why do I only get one playdate a day?");
        faq_list.add("How can I contribute?");
        faq_list.add("When will playdate come to my area?");
        faq_list.add("When can I make a new playdate everyday?");

        return faq_list;
    }

}
