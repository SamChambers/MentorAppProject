package com.example.mentorapp.Helpers;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.PagerAdapter;

import com.example.mentorapp.Evaluation;
import com.example.mentorapp.Game;
import com.example.mentorapp.Helpers.EvaluationFragment;

import java.util.ArrayList;

//Class to load the evaluation fragments
public class EvaluationFragmentAdapter extends FragmentPagerAdapter{

    private ArrayList<Evaluation> gameEvaluations;
    private long baseId;
    private Context context;


    //Standard constructor
    public EvaluationFragmentAdapter(FragmentManager supportFragmentManager, Context applicationContext){
        super(supportFragmentManager);
        this.gameEvaluations = new ArrayList<>();
        this.context = applicationContext;
    }

    //Full constructor
    public EvaluationFragmentAdapter(FragmentManager supportFragmentManager, Context applicationContext, ArrayList<Evaluation> gameEvaluations){
        super(supportFragmentManager);
        this.gameEvaluations = gameEvaluations;
        this.context = applicationContext;
    }

    //When the fragment first gets called
    @Override
    public Fragment getItem(int position){
        return new EvaluationFragment(this.gameEvaluations.get(position));
    }

    @Override
    public int getCount(){
        return this.gameEvaluations.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return this.gameEvaluations.get(position).getOfficialName(this.context);
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
    }

    public void setGameEvaluations(ArrayList<Evaluation> gameEvaluations) {
        this.gameEvaluations = gameEvaluations;
    }
}


