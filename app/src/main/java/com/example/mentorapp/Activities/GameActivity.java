package com.example.mentorapp.Activities;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import com.example.mentorapp.DataBase.DBHelper;
import com.example.mentorapp.Evaluation;
import com.example.mentorapp.Helpers.EvaluationFragment;
import com.example.mentorapp.Helpers.EvaluationFragmentAdapter;
import com.example.mentorapp.Game;
import com.example.mentorapp.Official.Official;
import com.example.mentorapp.R;
import com.google.android.material.tabs.TabLayout;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class GameActivity extends AppCompatActivity {

    //The tabs that can change the evaluee tab
    TabLayout tabLayout;
    //Holds the evaluation fragments
    ViewPager viewPager;
    EvaluationFragmentAdapter EFA;

    //Holds the game info along with the evaluations
    Game game;

    DBHelper dbh;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Set the content view
        setContentView(R.layout.game_layout);

        this.dbh = new DBHelper(this);

        this.game = (Game) getIntent().getSerializableExtra("Game");
        if (this.game.getId() == null){
            Long gameId = dbh.addGame(this.game);
            this.game.setId(gameId.intValue());
            goToOptions();
        }

        //Set the pager and the adapter
        viewPager = (ViewPager) findViewById(R.id.main_viewPager);
        this.EFA = new EvaluationFragmentAdapter(getSupportFragmentManager(), getApplicationContext(), this.game);
        viewPager.setAdapter(this.EFA);

        //Set the tabs and hook it up to the view pager
        tabLayout = (TabLayout) findViewById(R.id.main_tabLayout);
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


        ActionBar actionBar = getSupportActionBar();
        Toolbar mToolbar = findViewById(R.id.game_toolbar_id);
        setSupportActionBar(mToolbar);

        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backFunction();
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        System.out.println("Request Code: " + String.valueOf(requestCode));

        switch (requestCode) {
            case 100:
                ArrayList<String> comments = (ArrayList<String>) data.getSerializableExtra("UpdatedComments");
                Integer category = (Integer) data.getSerializableExtra("Category");
                Integer position = (Integer) data.getSerializableExtra("Position");
                Integer evaluation = (Integer) data.getSerializableExtra("Evaluation");

                game.getEvaluationFromPosition(evaluation).getTaskFromCategory(category, position).setComments(comments);

                EvaluationFragmentAdapter tempAdapter = (EvaluationFragmentAdapter) viewPager.getAdapter();
                tempAdapter.notifyChangeInPosition();
                break;
            case 800:
                this.game = (Game) data.getSerializableExtra("Game");
                System.out.println(this.game.getEvaluationCount());
                this.game.updateEvaluationPositions();
                EvaluationFragmentAdapter tempAdapter2 = (EvaluationFragmentAdapter) viewPager.getAdapter();
                tempAdapter2.setGame(this.game);
                tempAdapter2.notifyChangeInPosition();
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //PopupMenu menu = new PopupMenu(this, );

        String menuOptions[] = {"Present","Options"};

        for (int i=0; i<menuOptions.length; ++i) {
            menu.add(menuOptions[i]);
        }

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Game Title");

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (String.valueOf(item.getTitle())){
            case "Present":
                goToPresent();
                break;
            case "Options":
                goToOptions();
                break;
            default:
                Toast.makeText(
                        this,
                        "You Clicked : " + item.getTitle(),
                        Toast.LENGTH_SHORT
                ).show();
        }

        return true;
    }

    private void goToPresent(){
        Intent intent = new Intent(this, PresentActivity.class);
        intent.putExtra("MyGame", this.game);
        startActivity(intent);
    }

    private void goToOptions(){
        Intent intent = new Intent(this, GameOptionsActivity.class);
        intent.putExtra("MyGame", this.game);
        startActivityForResult(intent, 800);
    }

    @Override
    public void onBackPressed() {
        backFunction();
    }

    private void backFunction(){
        Intent returnIntent = new Intent();
        setResult(Activity.RESULT_OK,returnIntent);
        finish();
    }
}