package com.prince.logan.playdate.Activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
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

public class FAQActivity extends Activity {

    @Bind(R.id.list_faq)
    ListView listFaq;
    @Bind(R.id.search_faq)
    SearchView searchFaq;

    FaqAdapter adapter_faq;

    ArrayList<String> faqList = new ArrayList<String>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faq);
        ButterKnife.bind(this);

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

        faq_list.add("I'm confused. Where do I get an overview?");
        faq_list.add("I'm confused. Where do I get an overview?");
        faq_list.add("I'm confused. Where do I get an overview?");
        faq_list.add("I'm confused. Where do I get an overview?");
        faq_list.add("I'm confused. Where do I get an overview?");
        faq_list.add("I'm confused. Where do I get an overview?");
        faq_list.add("I'm confused. Where do I get an overview?");
        faq_list.add("I'm confused. Where do I get an overview?");

        return faq_list;
    }
}
