package com.example.mentorapp.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;
import android.view.View;
//import android.app.ActionBar;
//import android.widget.Toolbar;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;


import com.example.mentorapp.DataBase.DBHelper;
import com.example.mentorapp.Evaluation;
import com.example.mentorapp.ExampleDataPump;
import com.example.mentorapp.Game;
import com.example.mentorapp.Helpers.OptionsEvaluationsListAdapter;
import com.example.mentorapp.R;

public class GameOptionsActivity extends AppCompatActivity {

    Game game;
    //LinearLayout textLinearView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.game = (Game) getIntent().getSerializableExtra("MyGame");

        setContentView(R.layout.options_layout);

        ActionBar actionBar = getSupportActionBar();
        Toolbar mToolbar = findViewById(R.id.toolbar3);
        setSupportActionBar(mToolbar);

        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        final DBHelper dbh = new DBHelper(getApplicationContext());

        final ListView evaluationList = (ListView) findViewById(R.id.game_options_listView_evaluationslist_id);
        evaluationList.setAdapter(new OptionsEvaluationsListAdapter(getApplicationContext(), dbh, this.game.getEvaluationsList()));

        Button addEvaluationButton = (Button) findViewById(R.id.game_options_button_addEvaluation_id);
        addEvaluationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                game.addEvaluation(new Evaluation(ExampleDataPump.getTemplate(), game.getEvaluationCount()));
                game.updateEvaluationPositions();
                OptionsEvaluationsListAdapter adapter = (OptionsEvaluationsListAdapter)evaluationList.getAdapter();
                adapter.notifyDataSetChanged();
            }
        });

        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent returnIntent = new Intent();
                returnIntent.putExtra("Game",game);
                setResult(Activity.RESULT_OK,returnIntent);
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
}



