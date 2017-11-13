package com.prince.logan.playdate.Activity;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.widget.Toast;

import com.prince.logan.playdate.Fragment.FirstFragment;
import com.prince.logan.playdate.Fragment.FourthFragment;
import com.prince.logan.playdate.Fragment.SecondFragment;
import com.prince.logan.playdate.Fragment.ThirdFragment;
import com.prince.logan.playdate.R;

import java.util.ArrayList;
import java.util.List;

import me.riddhimanadib.library.BottomBarHolderActivity;
import me.riddhimanadib.library.NavigationPage;

public class MainActivity extends BottomBarHolderActivity implements FirstFragment.OnFragmentInteractionListener, SecondFragment.OnFragmentInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        NavigationPage page1 = new NavigationPage("Home", ContextCompat.getDrawable(this, R.drawable.ic_home_black_24dp), FirstFragment.newInstance());
        NavigationPage page2 = new NavigationPage("Support", ContextCompat.getDrawable(this, R.drawable.ic_home_black_24dp), SecondFragment.newInstance());
        NavigationPage page3 = new NavigationPage("Billing", ContextCompat.getDrawable(this, R.drawable.ic_home_black_24dp), ThirdFragment.newInstance());
        NavigationPage page4 = new NavigationPage("Profile", ContextCompat.getDrawable(this, R.drawable.ic_home_black_24dp), FourthFragment.newInstance());

        List<NavigationPage> navigationPages = new ArrayList<>();
        navigationPages.add(page1);
        navigationPages.add(page2);
        navigationPages.add(page3);
        navigationPages.add(page4);

        super.setupBottomBarHolderActivity(navigationPages);
    }

    @Override
    public void onClicked() {
        Toast.makeText(this, "Clicked!", Toast.LENGTH_SHORT).show();
    }
}