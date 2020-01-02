package com.example.mentorapp;


import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;
import com.google.android.material.tabs.TabLayout;


public class MainActivity extends AppCompatActivity {

    //The tabs that can change the evaluee tab
    TabLayout tabLayout;
    //Holds the evaluation fragments
    ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Set the content view
        setContentView(R.layout.activity_main);

        //Set the pager and the adapter
        viewPager = (ViewPager) findViewById(R.id.viewPagerID);
        viewPager.setAdapter(new EvaluationFragmentAdapter(getSupportFragmentManager(),getApplicationContext()));

        //Set the tabs and hook it up to the view pager
        tabLayout = (TabLayout) findViewById(R.id.tabView_layout_id);
        tabLayout.setupWithViewPager(viewPager);

        //Set the functions that happen when you click on the tabs
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {

            //When you click on the tab
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            //When you click on a different tab
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                return;
            }

            //When you click on the tab when its already selected
            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }
        });

    }

}