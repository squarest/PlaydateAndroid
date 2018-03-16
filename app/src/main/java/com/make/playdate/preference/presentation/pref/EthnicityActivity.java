package com.make.playdate.preference.presentation.pref;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;

import com.make.playdate.R;

import java.util.ArrayList;

public class EthnicityActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ethnicity);
        RecyclerView ethnicityList = findViewById(R.id.ethnicity_list);
        ArrayList<String> selected = getIntent().getStringArrayListExtra("selected");
        ethnicityList.setLayoutManager(new LinearLayoutManager(this));
        ethnicityList.setAdapter(new EthnicityAdapter(selected));

        ImageView backButton = findViewById(R.id.back_button);
        backButton.setOnClickListener(view -> {
            Intent intent = new Intent();
            intent.putExtra("selected", selected);
            setResult(RESULT_OK, intent);
            finish();
        });
    }
}
