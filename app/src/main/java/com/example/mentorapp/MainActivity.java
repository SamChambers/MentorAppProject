package com.example.mentorapp;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import com.google.android.material.tabs.TabLayout;
import android.app.Activity;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    //The tabs that can change the evaluee tab
    TabLayout tabLayout;
    //Holds the evaluation fragments
    ViewPager viewPager;

    //Holds the game info along with the evaluations
    Game game;

    private String fragments [] = {"Referee 1", "Referee 2","Referee 3", "Referee 4"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Set the content view
        setContentView(R.layout.activity_main);

        ArrayList<Evaluation> evaluations = new ArrayList<Evaluation>();
        for(int i=0; i < fragments.length; ++i){
            Evaluation eval = ExampleDataPump.getData(i);
            eval.setOfficial(fragments[i]);
            evaluations.add(eval);
        }

        this.game = new Game("Example Game", evaluations);

        //Set the pager and the adapter
        viewPager = (ViewPager) findViewById(R.id.viewPagerID);
        viewPager.setAdapter(new EvaluationFragmentAdapter(getSupportFragmentManager(),getApplicationContext(), this.game));

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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        System.out.println("Request Code: " + String.valueOf(requestCode));


        ArrayList<String> comments = (ArrayList<String>) data.getSerializableExtra("UpdatedComments");
        Integer category = (Integer) data.getSerializableExtra("Category");
        Integer position = (Integer) data.getSerializableExtra("Position");
        Integer evaluation = (Integer) data.getSerializableExtra("Evaluation");

        game.getEvaluationFromPosition(evaluation).getTaskFromCategory(category,position).setComments(comments);

        EvaluationFragmentAdapter tempAdapter = (EvaluationFragmentAdapter) viewPager.getAdapter();
        tempAdapter.notifyChangeInPosition();
    }
}