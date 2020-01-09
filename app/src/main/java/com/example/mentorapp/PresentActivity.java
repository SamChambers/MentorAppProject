package com.example.mentorapp;

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

import androidx.appcompat.app.AppCompatActivity;


import com.example.mentorapp.Presentation.*;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class PresentActivity extends AppCompatActivity {

    Game game;
    //LinearLayout textLinearView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.game = (Game) getIntent().getSerializableExtra("MyGame");

        setContentView(R.layout.present_game_layout);

        LinearLayout allList = (LinearLayout) findViewById(R.id.linear_layout_all_layout_id);
        LinearLayout flaggedList = (LinearLayout) findViewById(R.id.linear_layout_flagged_layout_id);

        ActionBar actionBar = getSupportActionBar();
        Toolbar mToolbar = findViewById(R.id.toolbar2);
        setSupportActionBar(mToolbar);

        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        PresentationStorage allPresentation = convertGameToPresentation(this.game);
        PresentationStorage flaggedPresentation = allPresentation.getFlaggedPresentationStorage();

        fillLinearView(allPresentation, allList);
        fillLinearView(flaggedPresentation, flaggedList);
        //this.textListView.setAdapter(new PresentationAdapter(getApplicationContext(),allPresentation));
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

    private PresentationStorage convertGameToPresentation(Game game){

        PresentationStorage presentation = new PresentationStorage();

        String tempString = "";

        for (Evaluation currentEvaluation:game.getEvaluationsList()) {
            String evaluationName = currentEvaluation.getOfficial();

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

    private void fillLinearView(PresentationStorage presentation, LinearLayout linearLayout){

        LayoutInflater inflater = LayoutInflater.from(getApplicationContext());
        ViewGroup vg = (ViewGroup)linearLayout;

        for (CategoryPresentation currentCategory:presentation.getCategories()){
            // Add the category sections
            View categoryView = getLayoutInflater().inflate(R.layout.present_category_layout,vg,false);
            TextView categoryNameView = (TextView)categoryView.findViewById(R.id.text_present_category_name_id);
            categoryNameView.setText(currentCategory.getCategory());
            linearLayout.addView(categoryView);

            // Add the task sections
            for(TaskPresentation currentTask:currentCategory.getTaskPresentations()){
                View taskView = getLayoutInflater().inflate(R.layout.present_detail_layout,vg,false);
                TextView taskNameView = (TextView)taskView.findViewById(R.id.text_present_detail_id);
                LinearLayout statsLinearLayoutView = (LinearLayout)taskView.findViewById(R.id.linear_layout_stats_id);
                taskNameView.setText(currentTask.getDescription());
                linearLayout.addView(taskView);

                //Add the officials stats
                for (DetailPresentation currentDetail:currentTask.getStats()){
                    View detailView = getLayoutInflater().inflate(R.layout.present_stats_layout,vg,false);
                    TextView officialNameView = (TextView)detailView.findViewById(R.id.text_present_name_id);
                    TextView officialScoreView = (TextView)detailView.findViewById(R.id.text_present_score_id);
                    LinearLayout commentsLinearLayoutView = (LinearLayout)detailView.findViewById(R.id.linear_layout_comments_id);
                    officialNameView.setText(currentDetail.getOfficial());
                    officialScoreView.setText("(" + currentDetail.getScore()+")");
                    if(currentDetail.getFlagged()){
                        detailView.setBackgroundColor(Color.GRAY);
                    }
                    statsLinearLayoutView.addView(detailView);

                    //Add the comments
                    for(String currentComment:currentDetail.getComments()){
                        View commentView = getLayoutInflater().inflate(R.layout.present_comment_layout,vg,false);
                        TextView commentTextView = (TextView)commentView.findViewById(R.id.text_present_comment_id);
                        commentTextView.setText(currentComment);
                        commentsLinearLayoutView.addView(commentView);
                    }
                }
            }
        }

    }



}



