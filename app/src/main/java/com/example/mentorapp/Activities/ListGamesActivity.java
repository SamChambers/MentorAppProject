package com.example.mentorapp.Activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.mentorapp.DataBase.DBHelper;
import com.example.mentorapp.Game;
import com.example.mentorapp.Helpers.GamesListAdapter;
import com.example.mentorapp.Helpers.OfficialsListAdapter;
import com.example.mentorapp.Official.Official;
import com.example.mentorapp.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

//import android.app.ActionBar;
//import android.widget.Toolbar;

public class ListGamesActivity extends AppCompatActivity {

    ListView gameListView;
    Context context;
    DBHelper TDBH;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.context = getApplicationContext();

        Boolean newGame = (Boolean) getIntent().getSerializableExtra("NewGame");
        if(newGame){
            //addNewGame();
        }

        setContentView(R.layout.list_games_layout);

        this.TDBH = new DBHelper(this.context);

        this.gameListView = findViewById(R.id.list_games_id);

        gameListView.setAdapter(new GamesListAdapter(this,this.TDBH));

        ActionBar actionBar = getSupportActionBar();

        Toolbar mToolbar = findViewById(R.id.toolbar_list_games_id);
        setSupportActionBar(mToolbar);


        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent returnIntent = new Intent();
                setResult(Activity.RESULT_OK,returnIntent);
                finish();
            }
        });

        FloatingActionButton fab_addNewGame = (FloatingActionButton) findViewById(R.id.games_fab_addNew);
        fab_addNewGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addNewGame();
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.list_games_options_menu, menu);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Games");

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.add_game_menu_item_id){
                addNewGame();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode){
            case 1000:

                GamesListAdapter GLA = (GamesListAdapter) this.gameListView.getAdapter();
                GLA.updateList();
                GLA.notifyDataSetChanged();
                break;
        }
    }


    private void addNewGame(){
        Intent intent = new Intent(context, GameActivity.class);
        intent.putExtra("Game", new Game());
        int requestCode = 1000;
        startActivityForResult(intent,requestCode);
    }
}


