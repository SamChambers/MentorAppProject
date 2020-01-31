package com.example.mentorapp.Activities;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mentorapp.DataBase.DBHelper;
import com.example.mentorapp.R;
import com.example.mentorapp.Tags.Tag;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        //Set the content view
        setContentView(R.layout.main_layout);

        //TODO: Delete this when the activity is in place
        DBHelper dbh = new DBHelper(getApplicationContext());
        List<Tag> tags = dbh.allTags();
        System.out.println("Tag List");
        System.out.println(tags);
        if (tags.size() < 2){
            Tag locationTag = new Tag("Location");
            locationTag.addOption("Court 1");
            locationTag.addOption("Court 2");
            locationTag.addOption("Court 3");
            dbh.addTag(locationTag);

            Tag ageTag = new Tag("Age Group");
            ageTag.addOption("U10");
            ageTag.addOption("U12");
            ageTag.addOption("U14");
            dbh.addTag(ageTag);
        }


        Button toGameButton = findViewById(R.id.main_button_new_game_id);
        toGameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newGame();
            }
        });

        Button listGameButton = findViewById(R.id.main_button_all_games_id);
        listGameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listGame();
            }
        });

        Button newTemplateButton = findViewById(R.id.main_button_new_template_id);
        newTemplateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newTemplate();
            }
        });

        Button listTemplatesButton = findViewById(R.id.main_button_all_templates_id);
        listTemplatesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listTemplate();
            }
        });

        Button listOfficialButton = findViewById(R.id.main_button_all_officials_id);
        listOfficialButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listOfficial();
            }
        });

        Button newOfficialButton = findViewById(R.id.main_button_new_official_id);
        newOfficialButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newOfficial();
            }
        });


    }

    private void newGame(){
        Intent intent = new Intent(this, GameListActivity.class);
        intent.putExtra("NewGame", Boolean.TRUE);
        startActivity(intent);
    }

    private void listGame(){
        Intent intent = new Intent(this, GameListActivity.class);
        intent.putExtra("NewGame", Boolean.FALSE);
        startActivity(intent);
    }

    private void newTemplate(){
        Intent intent = new Intent(this, TemplateListActivity.class);
        intent.putExtra("NewTemplate", Boolean.TRUE);
        startActivity(intent);
    }

    private void listTemplate(){
        Intent intent = new Intent(this, TemplateListActivity.class);
        intent.putExtra("NewTemplate", Boolean.FALSE);
        startActivity(intent);
    }

    private void listOfficial(){
        Intent intent = new Intent(this, OfficialListActivity.class);
        intent.putExtra("NewOfficial", Boolean.FALSE);
        startActivity(intent);
    }

    private void newOfficial(){
        Intent intent = new Intent(this, OfficialListActivity.class);
        intent.putExtra("NewOfficial", Boolean.TRUE);
        startActivity(intent);
    }

}