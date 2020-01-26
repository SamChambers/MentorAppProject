package com.example.mentorapp.Activities;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mentorapp.Game;
import com.example.mentorapp.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        //Set the content view
        setContentView(R.layout.main_layout);


        Button toGameButton = findViewById(R.id.main_button_new_game_id);
        toGameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newGame();
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
        Intent intent = new Intent(this, GameActivity.class);
        intent.putExtra("Game", new Game());
        startActivity(intent);
    }

    private void newTemplate(){
        Intent intent = new Intent(this, ListTemplatesActivity.class);
        intent.putExtra("NewTemplate", Boolean.TRUE);
        startActivity(intent);
    }

    private void listTemplate(){
        Intent intent = new Intent(this, ListTemplatesActivity.class);
        intent.putExtra("NewTemplate", Boolean.FALSE);
        startActivity(intent);
    }

    private void listOfficial(){
        Intent intent = new Intent(this, ListOfficialsActivity.class);
        intent.putExtra("NewOfficial", Boolean.FALSE);
        startActivity(intent);
    }

    private void newOfficial(){
        Intent intent = new Intent(this, ListOfficialsActivity.class);
        intent.putExtra("NewOfficial", Boolean.TRUE);
        startActivity(intent);
    }

}