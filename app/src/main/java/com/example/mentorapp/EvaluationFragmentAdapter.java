package com.example.mentorapp;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.PagerAdapter;

//Class to load the evaluation fragments
public class EvaluationFragmentAdapter extends FragmentPagerAdapter{

    private Game game;
    private long baseId;


    //Standard constructor
    public EvaluationFragmentAdapter(FragmentManager supportFragmentManager, Context applicationContext){
        super(supportFragmentManager);
        this.game = new Game();
    }

    //Full constructor
    public EvaluationFragmentAdapter(FragmentManager supportFragmentManager, Context applicationContext, Game game){
        super(supportFragmentManager);
        this.game = game;
    }

    //When the fragment first gets called
    @Override
    public Fragment getItem(int position){
        return new EvaluationFragment(game.getEvaluationFromPosition(position));
    }

    @Override
    public int getCount(){
        return this.game.getEvaluationCount();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return this.game.getEvaluationFromPosition(position).getOfficial();
    }

    @Override
    public int getItemPosition(@NonNull Object object) {
        return PagerAdapter.POSITION_NONE;
    }

    @Override
    public long getItemId(int position) {
        return baseId + position;
    }

    public void notifyChangeInPosition(){
        baseId += getCount()*2;
        notifyDataSetChanged();
        for (int i=0; i < game.getEvaluationCount(); ++i){
            game.getEvaluationsList().get(i).setEvaluationPosition(i);
        }
    }
}

