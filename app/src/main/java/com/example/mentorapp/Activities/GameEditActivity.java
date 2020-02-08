package com.example.mentorapp.Activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.view.LayoutInflater;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.view.View;
//import android.app.ActionBar;
//import android.widget.Toolbar;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;


import com.example.mentorapp.DataBase.DBHelper;
import com.example.mentorapp.Evaluation;
import com.example.mentorapp.Game;
import com.example.mentorapp.Helpers.OptionsEvaluationsListAdapter;
import com.example.mentorapp.Official.Official;
import com.example.mentorapp.R;
import com.example.mentorapp.Tags.Tag;
import com.example.mentorapp.Tags.TagOptions;
import com.example.mentorapp.Template;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class GameEditActivity extends AppCompatActivity {

    Game game;
    ArrayList<Evaluation> evaluationArrayList;
    Context context;
    OptionsEvaluationsListAdapter optionsListAdapter;
    DBHelper dbh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.context = getApplicationContext();

        this.game = (Game) getIntent().getSerializableExtra("MyGame");

        setContentView(R.layout.game_edit_layout);

        ActionBar actionBar = getSupportActionBar();
        Toolbar mToolbar = findViewById(R.id.toolbar3);
        setSupportActionBar(mToolbar);

        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        this.dbh = new DBHelper(getApplicationContext());

        LinearLayout gameOptionsLL = findViewById(R.id.game_options_linear_layout_id);
        setupTags(gameOptionsLL);
        TextView gameOptionsName = findViewById(R.id.game_options_editText_name_id);
        TextView gameOptionsDate = findViewById(R.id.game_options_editText_date_id);
        gameOptionsDate.setText(this.game.getDate());
        gameOptionsName.setText(this.game.getIdentifier());



        final ListView evaluationList = findViewById(R.id.game_options_listView_evaluationslist_id);

        this.evaluationArrayList = new ArrayList<>();
        for(Integer id : game.getEvaluationsList()){
            this.evaluationArrayList.add(dbh.getEvaluation(id));
        }
        this.optionsListAdapter = new OptionsEvaluationsListAdapter(this.context, dbh, this.evaluationArrayList);
        evaluationList.setAdapter(this.optionsListAdapter);

        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backFunction();
            }
        });

        FloatingActionButton fab_addNewEvaluation = findViewById(R.id.game_evaluation_fab_addNew);
        fab_addNewEvaluation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addEvaluation();
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
        ArrayList<Integer> evaluationIds = new ArrayList<>();
        for (Evaluation e : this.evaluationArrayList){
            Integer id;
            if(e.getId() == null){
                id = this.dbh.addEvaluation(e).intValue();
            } else {
                id = e.getId();
            }
            evaluationIds.add(id);
        }
        this.game.setEvaluationsList(evaluationIds);

        EditText nameView = findViewById(R.id.game_options_editText_name_id);
        this.game.setIdentifier(nameView.getText().toString());
        //TODO: Make this into a date picker scenario
        EditText dateView = findViewById(R.id.game_options_editText_date_id);
        this.game.setDate(dateView.getText().toString());
        LinearLayout gameOptionsLL = findViewById(R.id.game_options_linear_layout_id);
        int tagCount = gameOptionsLL.getChildCount();
        ConstraintLayout tagCL;
        ArrayList<TagOptions> tags = new ArrayList<>();
        for(int i=2; i< tagCount; ++i){
            tagCL = (ConstraintLayout) gameOptionsLL.getChildAt(i);
            View v;
            int count = tagCL.getChildCount();
            for (int j=0; j<count; j++){
                v = tagCL.getChildAt(j);
                System.out.println(v.getId());
            }
            Spinner spinner = tagCL.findViewById(R.id.game_options_tag_spinner_tagOption_id);
            TextView name = tagCL.findViewById(R.id.game_options_tag_textView_tagName_id);
            String tagName = name.getText().toString();
            String tagValue = spinner.getSelectedItem().toString();
            tags.add(new TagOptions(tagName,tagValue));
        }
        this.game.setTags(tags);

        this.dbh.updateGame(game);

        Intent returnIntent = new Intent();
        returnIntent.putExtra("Game",game);
        setResult(Activity.RESULT_OK,returnIntent);
        finish();
    }

    private void addEvaluation(){
        final DBHelper dbh = new DBHelper(context);
        LayoutInflater inflater = getLayoutInflater();
        View dialogLayout = inflater.inflate(R.layout.evaluation_new_options_layout, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(GameEditActivity.this);
        builder.setView(dialogLayout);
        builder.setTitle("Add Evaluation");

        final Spinner templateSpinner = dialogLayout.findViewById(R.id.options_spinner_template_id);
        final Spinner officialSpinner = dialogLayout.findViewById(R.id.options_spinner_official_id);

        final List<Official> officialList = dbh.allOfficials();
        final ArrayList<String> officialNames = new ArrayList<>();
        for(Official official : officialList){
            officialNames.add(official.getName());
        }

        ArrayAdapter<String> officialAdapter = new ArrayAdapter<>(context,
                android.R.layout.simple_spinner_item, officialNames);

        officialSpinner.setAdapter(officialAdapter);

        final List<Template> templateList = dbh.allTemplates();
        final ArrayList<String> templateNames = new ArrayList<>();
        for(Template template : templateList){
            templateNames.add(template.getName());
        }

        ArrayAdapter<String> templateAdapter = new ArrayAdapter<>(context,
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

                Evaluation evaluation = new Evaluation(selectedTemplate, game.getId());
                evaluation.setOfficialId(selectedOfficial.getId());
                Long evaluationId = dbh.addEvaluation(evaluation);
                evaluation.setId(evaluationId.intValue());
                selectedOfficial.getEvaluationsList().add(evaluationId.intValue());
                dbh.updateOfficial(selectedOfficial);

                game.addEvaluation(evaluation);
                evaluationArrayList.add(evaluation);

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

    private void setupTags(LinearLayout ll){
        List<Tag> tagList = this.dbh.allTags();
        for (Tag tag : tagList){
            View view = getLayoutInflater().inflate(R.layout.game_options_tag_layout,ll,false);
            TextView tagNameView = view.findViewById(R.id.game_options_tag_textView_tagName_id);
            Spinner tagOptionsView = view.findViewById(R.id.game_options_tag_spinner_tagOption_id);
            tagNameView.setText(tag.getName());
            ArrayList<String> tagOptions = tag.getOptionsList();
            if(tag.getManditory() == Boolean.FALSE){
                tagOptions.add(0,"");
            }
            ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, tagOptions);
            tagOptionsView.setAdapter(adapter);
            ArrayList<TagOptions> tags = this.game.getTags();
            System.out.println(tag.getName());
            for(TagOptions gameTag : tags){
                System.out.println(gameTag.getTag());

                if (gameTag.getTag().equals(tag.getName())){
                    System.out.println("Found Tag");
                    int selection = tagOptions.indexOf(gameTag.getOption());

                    if (selection == 0){ selection = 0;}
                    tagOptionsView.setSelection(selection);
                }
            }
            ll.addView(view);
        }
    }
}



