package com.prince.logan.playdate.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.preference.Preference;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.prince.logan.playdate.Adapter.PreferenceItemAdapter;
import com.prince.logan.playdate.Global.Preferences;
import com.prince.logan.playdate.Model.PreferenceItemModel;
import com.prince.logan.playdate.R;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by PRINCE on 12/6/2017.
 */

public class PreferenceItemActivity extends Activity implements View.OnClickListener{
    @Bind(R.id.list_pItem)
    ListView listPitem;
    @Bind(R.id.img_preference_item_back)
    ImageView imgBack;
    @Bind(R.id.txt_pItem_title)
    TextView txtTitle;

    int indexItem;

    public static ArrayList<PreferenceItemModel> arrayPlist = new ArrayList<PreferenceItemModel>();
    PreferenceItemAdapter preAdapter;

    String[] arrayEthnicity = {"White/ Caucasian","Asian","South Asian","Hispanic/ Latino","Pacific Islander","Middle Eastern/ Arab","Black/ African-descent","Native American","Other"};
    String[] arrayReligion = {"Christian","Catholic","Jewish","Muslim","Unitarian/ Universalist","Buddhist","Hindu","Agnostic","Atheist","Other"};
    String[] arrayEducation = {"High Selective","Selective"};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preference_items);
        ButterKnife.bind(this);

        imgBack.setOnClickListener(this);

        Intent intent = this.getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            indexItem = bundle.getInt("item");
            switch (indexItem){
                case 1:
                    txtTitle.setText("Ethnicity");
                    if (Preferences.listEthnicity != null){
                        arrayPlist = Preferences.listEthnicity;
                    }
                    else{
                        for (int i=0; i<arrayEthnicity.length; i++){
                            PreferenceItemModel item = new PreferenceItemModel();
                            item.setTitle(arrayEthnicity[i]);
                            item.setChecked(1);

                            arrayPlist.add(item);
                        }
                    }
                    break;
                case 2:
                    txtTitle.setText("Religion");
                    if (Preferences.listReligion != null){
                        arrayPlist = Preferences.listReligion;
                    }
                    else{
                        for (int i=0; i<arrayReligion.length; i++){
                            PreferenceItemModel item = new PreferenceItemModel();
                            item.setTitle(arrayReligion[i]);
                            item.setChecked(1);

                            arrayPlist.add(item);
                        }
                    }
                    break;
                case 3:
                    txtTitle.setText("Education");
                    if (Preferences.listEducation != null){
                        arrayPlist = Preferences.listEducation;
                    }
                    else{
                        for (int i=0; i<arrayEducation.length; i++){
                            PreferenceItemModel item = new PreferenceItemModel();
                            item.setTitle(arrayEducation[i]);
                            item.setChecked(1);

                            arrayPlist.add(item);
                        }
                    }
                    break;
            }
        }
        preAdapter = new PreferenceItemAdapter(getApplicationContext(), R.layout.adapter_preference_item, arrayPlist);
        listPitem.setAdapter(preAdapter);
        preAdapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.img_preference_item_back:
                switch (indexItem){
                    case 1:
                        Preferences.listEthnicity = arrayPlist;
                        break;
                    case 2:
                        Preferences.listReligion = arrayPlist;
                        break;
                    case 3:
                        Preferences.listEducation = arrayPlist;
                        break;
                }

                arrayPlist = new ArrayList<PreferenceItemModel>();
                this.finish();
                break;
        }
    }
}
