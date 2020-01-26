package com.example.mentorapp.Activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.view.View;
//import android.app.ActionBar;
//import android.widget.Toolbar;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


import com.example.mentorapp.DataBase.DBHelper;
import com.example.mentorapp.Evaluation;
import com.example.mentorapp.ExampleDataPump;
import com.example.mentorapp.Game;
import com.example.mentorapp.Helpers.OptionsEvaluationsListAdapter;
import com.example.mentorapp.Helpers.TemplateListAdapter;
import com.example.mentorapp.Official.Official;
import com.example.mentorapp.R;
import com.example.mentorapp.Template;

import java.util.ArrayList;
import java.util.List;

public class GameOptionsActivity extends AppCompatActivity {

    Game game;
    Context context;
    OptionsEvaluationsListAdapter optionsListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.context = getApplicationContext();

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
        this.optionsListAdapter = new OptionsEvaluationsListAdapter(this.context, dbh, this.game.getEvaluationsList());
        evaluationList.setAdapter(this.optionsListAdapter);

        Button addEvaluationButton = (Button) findViewById(R.id.game_options_button_addEvaluation_id);
        addEvaluationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addEvaluation();
            }
        });

        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backFunction();
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.game_options_menu, menu);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Game Options");

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {

        backFunction();

    }

    private void backFunction(){
        Intent returnIntent = new Intent();
        returnIntent.putExtra("Game",game);
        setResult(Activity.RESULT_OK,returnIntent);
        finish();
    }

    private void addEvaluation(){
        final DBHelper dbh = new DBHelper(context);
        LayoutInflater inflater = getLayoutInflater();
        View dialogLayout = inflater.inflate(R.layout.options_new_evaluation_layout, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(GameOptionsActivity.this);
        builder.setView(dialogLayout);
        builder.setTitle("Add Evaluation");

        final Spinner templateSpinner = (Spinner) dialogLayout.findViewById(R.id.options_spinner_template_id);
        final Spinner officialSpinner = (Spinner) dialogLayout.findViewById(R.id.options_spinner_official_id);

        final List<Official> officialList = dbh.allOfficials();
        final ArrayList<String> officialNames = new ArrayList<>();
        for(Official official : officialList){
            officialNames.add(official.getName());
        }

        ArrayAdapter<String> officialAdapter = new ArrayAdapter<String>(context,
                android.R.layout.simple_spinner_item, officialNames);

        officialSpinner.setAdapter(officialAdapter);

        final List<Template> templateList = dbh.allTemplates();
        final ArrayList<String> templateNames = new ArrayList<>();
        for(Template template : templateList){
            templateNames.add(template.getName());
        }

        ArrayAdapter<String> templateAdapter = new ArrayAdapter<String>(context,
                android.R.layout.simple_spinner_item, templateNames);

        templateSpinner.setAdapter(templateAdapter);

        //TODO: Make this a better process
        if(officialList.size() < 1){
            Toast.makeText(
                    this,
                    "You must create an official before you add a new evaluation",
                    Toast.LENGTH_SHORT
            ).show();
            return;
        }

        if(templateList.size() < 1){
            Toast.makeText(
                    this,
                    "You must create a template before you add a new evaluation",
                    Toast.LENGTH_SHORT
            ).show();
            return;
        }

        // Set up the buttons
        builder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String officialName = officialSpinner.getSelectedItem().toString();
                int officialPosition = officialNames.indexOf(officialName);
                Official selectedOfficial = officialList.get(officialPosition);

                String templateName = templateSpinner.getSelectedItem().toString();
                int templatePosition = templateNames.indexOf(templateName);
                Template selectedTemplate = templateList.get(templatePosition);

                Evaluation evaluation = new Evaluation(selectedTemplate, game.getEvaluationCount());
                evaluation.setOfficialId(selectedOfficial.getId());
                Long evaluationId = dbh.addEvaluation(evaluation);
                selectedOfficial.getEvaluationsList().add(evaluationId.intValue());
                dbh.updateOfficial(selectedOfficial);

                game.addEvaluation(evaluation);
                game.updateEvaluationPositions();

                optionsListAdapter.notifyDataSetChanged();


            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
    }
}



