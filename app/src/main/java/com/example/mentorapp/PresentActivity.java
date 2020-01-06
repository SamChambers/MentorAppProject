package com.example.mentorapp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.InputType;
import android.util.Pair;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ArrayAdapter;
import android.content.Context;
import android.view.View;
//import android.app.ActionBar;
import android.content.Intent;
//import android.widget.Toolbar;
import android.app.Activity;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.Toolbar;
import android.widget.Toast;

import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class PresentActivity extends AppCompatActivity {

    Game game;
    TextView textListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.game = (Game) getIntent().getSerializableExtra("MyGame");

        setContentView(R.layout.present_game_layout);

        this.textListView = (TextView) findViewById(R.id.text_present_id);

        ActionBar actionBar = getSupportActionBar();
        Toolbar mToolbar = findViewById(R.id.toolbar2);
        setSupportActionBar(mToolbar);

        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent returnIntent = new Intent();
                finish();
            }
        });

        setTextView();
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

    public void setTextView(){
        TextView view = this.textListView;

        presentation presentation = new presentation();

        String tempString = "";

        for (Evaluation currentEvaluation:this.game.getEvaluationsList()) {
            String evaluationName = currentEvaluation.getOfficial();

            for(Category currentCategory:currentEvaluation.getCategories()){
                String categoryName = currentCategory.getTitle();
                int categoryIndex = presentation.getCategoryPosition(categoryName);
                if( categoryIndex == -1){
                    presentation.categories.add(new categoryPresentation(categoryName,new ArrayList<taskPresentation>()));
                    categoryIndex = presentation.getCategoryPosition(categoryName);
                }
                for(Task currentTask:currentCategory.getAllTasks()){
                    String description = currentTask.getDescription();
                    int taskIndex = presentation.categories.get(categoryIndex).getTaskPosition(description);
                    if(taskIndex == -1){
                        presentation.categories.get(categoryIndex).taskPresentations.add(new taskPresentation(description,new ArrayList<detailPresentation>()));
                        taskIndex = presentation.categories.get(categoryIndex).getTaskPosition(description);
                    }
                    ArrayList<String> comments = (ArrayList<String>) currentTask.getComments();
                    Boolean flagged = currentTask.getFlagged();
                    Integer score = currentTask.getScore();
                    presentation.categories.get(categoryIndex).taskPresentations.get(taskIndex).stats.add(new detailPresentation(evaluationName,score,comments,flagged));
                }
            }
        }

        for(categoryPresentation category:presentation.categories){
            tempString += (category.category+'\n');
            for(taskPresentation task:category.taskPresentations){
                tempString += (task.description+'\n');
                for (detailPresentation detail:task.stats){
                    tempString += (detail.official + " (" + detail.score + ") " + detail.comments+'\n');
                }
            }
        }

        System.out.println(tempString);

        view.setText(tempString);

    }

    class presentation{
        ArrayList<categoryPresentation> categories = new ArrayList<>();
        int getCategoryPosition(String category){
            for(int i=0; i< categories.size(); ++i){
                if (categories.get(i).category == category){
                    return i;
                }
            }
            return -1;
        }
    }

    class categoryPresentation{

        String category;
        ArrayList<taskPresentation> taskPresentations;

        categoryPresentation(String name, ArrayList<taskPresentation> tasks){
            this.category = name;
            this.taskPresentations = tasks;
        }

        int getTaskPosition(String description){
            for(int i=0; i< taskPresentations.size(); ++i){
                if (taskPresentations.get(i).description == description){
                    return i;
                }
            }
            return -1;
        }

    }

    class taskPresentation{
        String description;
        ArrayList<detailPresentation> stats;

        taskPresentation(String description, ArrayList<detailPresentation> stats){
            this.description = description;
            this.stats = stats;
        }
    }

    class detailPresentation{
        String official;
        ArrayList<String> comments;
        Integer score;
        Boolean flagged;

        detailPresentation(String official, Integer score, ArrayList<String> comments, Boolean flagged){
            this.official = official;
            this.score = score;
            this.comments = comments;
            this.flagged = flagged;
        }
    }

}



