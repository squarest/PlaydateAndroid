package com.prince.logan.playdate.help.faq;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;

import com.prince.logan.playdate.Adapter.FaqAdapter;
import com.prince.logan.playdate.R;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by PRINCE on 11/15/2017.
 */

public class FAQActivity extends Activity implements View.OnClickListener{

    @Bind(R.id.list_faq)
    ListView listFaq;
    @Bind(R.id.search_faq)
    SearchView searchFaq;
    @Bind(R.id.img_faq_back)
    ImageView back;

    FaqAdapter adapter_faq;

    ArrayList<String> faqList = new ArrayList<String>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faq);
        ButterKnife.bind(this);

        back.setOnClickListener(this);

        listFaq.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent detailIntent = new Intent(FAQActivity.this, FAQDetailActivity.class);
                detailIntent.putExtra("faq_id", i);
                startActivity(detailIntent);
            }
        });

        initView();
    }

    private void initView() {

        faqList = getItems();

        adapter_faq = new FaqAdapter(this, R.layout.adapter_faq, faqList);
        listFaq.setAdapter(adapter_faq);
        adapter_faq.notifyDataSetChanged();
    }

    private ArrayList<String> getItems(){

        ArrayList<String> faq_list = new ArrayList<>();

        faq_list.add("How does this all work?");
        faq_list.add("How do I request questions for an activity?");
        faq_list.add("Why does chat only stay open for 24 hours?");
        faq_list.add("Why do I only get one playdate a day?");
        faq_list.add("How can I contribute?");

        return faq_list;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.img_faq_back:
                this.finish();
                break;
        }
    }
}
