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


import com.example.mentorapp.Category;
import com.example.mentorapp.Evaluation;
import com.example.mentorapp.Game;
import com.example.mentorapp.Presentation.*;
import com.example.mentorapp.R;
import com.example.mentorapp.Task;

import java.lang.reflect.Array;
import java.util.ArrayList;

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

        //LinearLayout allList = (LinearLayout) findViewById(R.id.linear_layout_all_layout_id);
        //LinearLayout flaggedList = (LinearLayout) findViewById(R.id.linear_layout_flagged_layout_id);
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



