package com.make.playdate.main.presentation.main;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTabHost;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.make.playdate.R;
import com.make.playdate.base.BaseActivity;
import com.make.playdate.chat.presentation.chatlist.ChatListFragment;
import com.make.playdate.main.presentation.menu.MenuFragment;
import com.make.playdate.main.presentation.profile.ProfileFragment;
import com.make.playdate.playdate.presentation.list.PlaydateFragment;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity implements MainView {

    private Class mClass[] = {ProfileFragment.class, PlaydateFragment.class, ChatListFragment.class, MenuFragment.class};
    private Fragment mFragment[] = {new ProfileFragment(), new PlaydateFragment(), new ChatListFragment(), new MenuFragment()};
    private String mTitles[] = {"Profile", "Playdate", "Chat", "Menu"};
    private int mImages[] = {
            R.drawable.tab_profile,
            R.drawable.tab_playdate,
            R.drawable.tab_chat,
            R.drawable.tab_menu
    };

    @InjectPresenter
    public MainPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        presenter.viewCreated();
    }



    private void initView() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("");
        TextView screenTitle = toolbar.findViewById(R.id.toolbar_title);
        screenTitle.setText(mTitles[0]);
        setSupportActionBar(toolbar);
        FragmentTabHost mTabHost = findViewById(android.R.id.tabhost);
        List<Fragment> mFragmentList = new ArrayList<>();
        mTabHost.setup(this, getSupportFragmentManager(), android.R.id.tabcontent);
        mTabHost.getTabWidget().setDividerDrawable(null);

        for (int i = 0; i < mFragment.length; i++) {
            TabHost.TabSpec tabSpec = mTabHost.newTabSpec(mTitles[i]).setIndicator(getTabView(i));
            mTabHost.addTab(tabSpec, mClass[i], null);
            mFragmentList.add(mFragment[i]);
            mTabHost.getTabWidget().getChildAt(i).setBackgroundColor(Color.WHITE);
        }
        mTabHost.setOnTabChangedListener(s -> screenTitle.setText(s));

    }

    private View getTabView(int index) {
        View view;
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.tab_item, null);
        ImageView image = view.findViewById(R.id.image);
        TextView title = view.findViewById(R.id.title);
        image.setImageResource(mImages[index]);
        title.setText(mTitles[index]);
        return view;
    }
}
