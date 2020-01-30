package com.example.mentorapp.Activities;

import android.app.AlertDialog;
import android.app.Presentation;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.text.InputType;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ArrayAdapter;
import android.content.Context;
import android.view.View;
//import android.app.ActionBar;
import android.content.Intent;
//import android.widget.Toolbar;
import android.app.Activity;
import android.widget.TextView;
import android.text.method.ScrollingMovementMethod;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.Toolbar;
import android.widget.Toast;

import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup.LayoutParams;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;


import com.example.mentorapp.Category;
import com.example.mentorapp.DataBase.DBHelper;
import com.example.mentorapp.Evaluation;
import com.example.mentorapp.Game;
import com.example.mentorapp.Helpers.EvaluationFragmentAdapter;
import com.example.mentorapp.Presentation.*;
import com.example.mentorapp.R;
import com.example.mentorapp.Task;
import com.google.android.material.tabs.TabLayout;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class PresentActivity extends AppCompatActivity {

    private Game game;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.game = (Game) getIntent().getSerializableExtra("MyGame");

        setContentView(R.layout.present_game_with_fragments_layout);

        this.tabLayout = (TabLayout) findViewById(R.id.tabView_present_layout_id);
        this.viewPager = (ViewPager) findViewById(R.id.viewPager_present_ID);

        DBHelper dbh = new DBHelper(getApplicationContext());
        ArrayList<Evaluation> evaluationArrayList = new ArrayList<>();
        for(Integer id : this.game.getEvaluationsList()){
            evaluationArrayList.add(dbh.getEvaluation(id));
        }

        PresentationStorage ps;
        ArrayList<PresentationStorage> presentationList = new ArrayList<>();
        if (this.game.getEvaluationCount() > 1) {
            ps = convertEvaluationToPresentation(evaluationArrayList);
            presentationList.add(ps);
            ps.setTitle("All");
        }
        for(Evaluation e : evaluationArrayList) {
            ArrayList<Evaluation> el = new ArrayList<Evaluation>();
            el.add(e);
            ps = convertEvaluationToPresentation(el);
            ps.setTitle(e.getOfficialName(getApplicationContext()));
            presentationList.add(ps);
        }

        this.viewPager.setAdapter(new PresentationFragmentAdapter(getSupportFragmentManager(), getApplicationContext(), presentationList));
        this.tabLayout.setupWithViewPager(viewPager);

        //Set the functions that happen when you click on the tabs
        this.tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {

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


        ActionBar actionBar = getSupportActionBar();
        Toolbar mToolbar = findViewById(R.id.toolbar_present_id);
        setSupportActionBar(mToolbar);

        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });




    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.game_options_menu, menu);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle(this.game.getIdentifier());

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        return super.onOptionsItemSelected(item);
    }

    private PresentationStorage convertEvaluationToPresentation(ArrayList<Evaluation> evaluationList){

        PresentationStorage presentation = new PresentationStorage();

        String tempString = "";

        for (Evaluation currentEvaluation:evaluationList) {
            String evaluationName = currentEvaluation.getOfficialName(getApplicationContext());

            for(Category currentCategory:currentEvaluation.getCategories()){
                String categoryName = currentCategory.getTitle();
                int categoryIndex = presentation.getCategoryPosition(categoryName);
                if( categoryIndex == -1){
                    presentation.getCategories().add(new CategoryPresentation(categoryName,new ArrayList<TaskPresentation>()));
                    categoryIndex = presentation.getCategoryPosition(categoryName);
                }
                for(Task currentTask:currentCategory.getAllTasks()){
                    String description = currentTask.getDescription();
                    int taskIndex = presentation.getCategories().get(categoryIndex).getTaskPosition(description);
                    if(taskIndex == -1){
                        presentation.getCategories().get(categoryIndex).getTaskPresentations().add(new TaskPresentation(description,new ArrayList<DetailPresentation>()));
                        taskIndex = presentation.getCategories().get(categoryIndex).getTaskPosition(description);
                    }
                    ArrayList<String> comments = (ArrayList<String>) currentTask.getComments();
                    Boolean flagged = currentTask.getFlagged();
                    Integer score = currentTask.getScore();
                    presentation.getCategories().get(categoryIndex).getTaskPresentations().get(taskIndex).getStats().add(new DetailPresentation(evaluationName,score,comments,flagged));
                }
            }
        }

        for(CategoryPresentation category:presentation.getCategories()){
            tempString += (category.getCategory()+'\n');
            for(TaskPresentation task:category.getTaskPresentations()){
                tempString += (task.getDescription()+'\n');
                for (DetailPresentation detail:task.getStats()){
                    tempString += (detail.getOfficial() + " (" + detail.getScore() + ") " + detail.getComments()+'\n');
                }
            }
        }

        return presentation;

    }





}



