package com.prince.logan.playdate.Activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.prince.logan.playdate.R;

/**
 * Created by PRINCE on 11/14/2017.
 */

public class PlaydateListActivity extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playdate_list);
    }
}