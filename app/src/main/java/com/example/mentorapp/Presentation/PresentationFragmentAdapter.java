package com.example.mentorapp.Presentation;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.PagerAdapter;

import com.example.mentorapp.Game;
import com.example.mentorapp.Helpers.EvaluationFragment;

import java.util.ArrayList;

//Class to load the evaluation fragments
public class PresentationFragmentAdapter extends FragmentPagerAdapter{

    private ArrayList<PresentationStorage> presentationList;
    private long baseId;


    //Standard constructor
    public PresentationFragmentAdapter(FragmentManager supportFragmentManager, Context applicationContext){
        super(supportFragmentManager);
        this.presentationList = new ArrayList<>();
    }

    //Full constructor
    public PresentationFragmentAdapter(FragmentManager supportFragmentManager, Context applicationContext, ArrayList<PresentationStorage> presentationList){
        super(supportFragmentManager);
        this.presentationList = presentationList;
    }

    //When the fragment first gets called
    @Override
    public Fragment getItem(int position){
        return new PresentationFragment(this.presentationList.get(position));
    }

    @Override
    public int getCount(){
        return this.presentationList.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return this.presentationList.get(position).getTitle();
    }

    @Override
    public int getItemPosition(@NonNull Object object) {
        return PagerAdapter.POSITION_NONE;
    }

    @Override
    public long getItemId(int position) {
        return baseId + position;
    }

}


