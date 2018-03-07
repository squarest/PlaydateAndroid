package com.prince.logan.playdate.main.presentation.main;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTabHost;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.prince.logan.playdate.R;
import com.prince.logan.playdate.base.BaseActivity;
import com.prince.logan.playdate.chat.presentation.chatlist.ChatListFragment;
import com.prince.logan.playdate.main.presentation.menu.MenuFragment;
import com.prince.logan.playdate.main.presentation.profile.ProfileFragment;
import com.prince.logan.playdate.playdate.PlaydateFragment;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity implements MainView {

    private FragmentTabHost mTabHost;
    private ViewPager mViewPager;
    private List<Fragment> mFragmentList;
    private Class mClass[] = {ProfileFragment.class, PlaydateFragment.class, ChatListFragment.class, MenuFragment.class};
    private Fragment mFragment[] = {new ProfileFragment(), new PlaydateFragment(), new ChatListFragment(), new MenuFragment()};
    private String mTitles[] = {"Profile", "Playdate", "Chat", "Menu"};
    private int mImages[] = {
            R.drawable.tab_profile,
            R.drawable.tab_playdate,
            R.drawable.chat_icon,
            R.drawable.tab_menu
    };

    @InjectPresenter
    public MainPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        initView();
        initEvent();
        presenter.viewCreated();
    }

    private void initView() {

        mTabHost = findViewById(android.R.id.tabhost);
        mViewPager = findViewById(R.id.view_pager);
        mFragmentList = new ArrayList<>();
        mTabHost.setup(this, getSupportFragmentManager(), android.R.id.tabcontent);
        mTabHost.getTabWidget().setDividerDrawable(null);

        for (int i = 0; i < mFragment.length; i++) {
            TabHost.TabSpec tabSpec = mTabHost.newTabSpec(mTitles[i]).setIndicator(getTabView(i));
            mTabHost.addTab(tabSpec, mClass[i], null);
            mFragmentList.add(mFragment[i]);
            mTabHost.getTabWidget().getChildAt(i).setBackgroundColor(Color.WHITE);
        }

        mViewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return mFragmentList.get(position);
            }

            @Override
            public int getCount() {
                return mFragmentList.size();
            }
        });
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

    private void initEvent() {

        mTabHost.setOnTabChangedListener(tabId -> mViewPager.setCurrentItem(mTabHost.getCurrentTab()));

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                mTabHost.setCurrentTab(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }
}
