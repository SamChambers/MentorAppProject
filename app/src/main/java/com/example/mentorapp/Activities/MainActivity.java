package com.example.mentorapp.Activities;


import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.example.mentorapp.Evaluation;
import com.example.mentorapp.Helpers.EvaluationFragmentAdapter;
import com.example.mentorapp.ExampleDataPump;
import com.example.mentorapp.Game;
import com.example.mentorapp.R;
import com.google.android.material.tabs.TabLayout;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    //The tabs that can change the evaluee tab
    TabLayout tabLayout;
    //Holds the evaluation fragments
    ViewPager viewPager;

    //Holds the game info along with the evaluations
    Game game;




    //placeholder - hardcoded referee names
    //to be replaced with officials in database once implemented
    private String fragments[] = {"Referee 1", "Referee 2", "Referee 3", "Referee 4"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        //Set the content view
        setContentView(R.layout.activity_main);

        ArrayList<Evaluation> evaluations = new ArrayList<Evaluation>();
        for (int i = 0; i < fragments.length; ++i) {
            Evaluation eval = new Evaluation(ExampleDataPump.getTemplate(),i);
            evaluations.add(eval);
        }

        this.game = new Game("Example Game", evaluations);

        //Set the pager and the adapter
        viewPager = (ViewPager) findViewById(R.id.main_viewPager);
        viewPager.setAdapter(new EvaluationFragmentAdapter(getSupportFragmentManager(), getApplicationContext(), this.game));

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
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //PopupMenu menu = new PopupMenu(this, );

        String menuOptions[] = {"Present","Options", "Template Demo", "Official Demo"};

        for (int i=0; i<menuOptions.length; ++i) {
            menu.add(menuOptions[i]);
        }

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
            case "Template Demo":
                goToCreateTemplate();
                break;
            case "Official Demo":
                goToOfficials();
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
        startActivity(intent);
    }

    //TODO: Delete these once we create the main thread
    private void goToCreateTemplate(){
        Intent intent = new Intent(this, ListTemplatesActivity.class);
        startActivity(intent);
    }

    private void goToOfficials(){
        Intent intent = new Intent(this, ListOfficialsActivity.class);
        startActivity(intent);
    }
}