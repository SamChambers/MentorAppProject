package com.example.mentorapp;

import android.content.Context;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

//Class to load the evaluation fragments
public class EvaluationFragmentAdapter extends FragmentPagerAdapter{

    //Hardcoded for now.
    //TODO: Hook these up once we get officials implimented
    private String fragments [] = {"Referee 1", "Referee 2","Referee 3", "Referee 4"};

    //Standard constructor
    public EvaluationFragmentAdapter(FragmentManager supportFragmentManager, Context applicationContext){
        super(supportFragmentManager);
    }

    //Wehn the fragment first gets called
    @Override
    public Fragment getItem(int position){
        return new EvaluationFragment();
    }

    @Override
    public int getCount(){
        return fragments.length;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return fragments[position];
    }
}


