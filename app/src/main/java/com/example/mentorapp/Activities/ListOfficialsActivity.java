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
import com.example.mentorapp.Official.Official;
import com.example.mentorapp.R;
import com.example.mentorapp.Helpers.OfficialsListAdapter;

//import android.app.ActionBar;
//import android.widget.Toolbar;

public class ListOfficialsActivity extends AppCompatActivity {

    ListView officialListView;
    Context context;
    DBHelper TDBH;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.context = getApplicationContext();

        Boolean newOfficial = (Boolean) getIntent().getSerializableExtra("NewOfficial");
        if(newOfficial){
            addNewOfficial();
        }

        setContentView(R.layout.list_officals_layout);

        this.TDBH = new DBHelper(this.context);

        this.officialListView = findViewById(R.id.list_officials_id);

        officialListView.setAdapter(new OfficialsListAdapter(this,this.TDBH));

        ActionBar actionBar = getSupportActionBar();

        Toolbar mToolbar = findViewById(R.id.toolbar_list_officals_id);
        setSupportActionBar(mToolbar);


        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent returnIntent = new Intent();
                setResult(Activity.RESULT_OK,returnIntent);
                finish();
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.list_officials_options_menu, menu);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Officials");

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.add_official_menu_item_id){
                addNewOfficial();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode){
            case 400:

                OfficialsListAdapter OLA = (OfficialsListAdapter) this.officialListView.getAdapter();
                OLA.updateList();
                OLA.notifyDataSetChanged();
                break;
        }
    }


    private void addNewOfficial(){
        Intent intent = new Intent(context, ViewOfficialActivity.class);
        intent.putExtra("Official", new Official("New Official"));
        int requestCode = 400;

        startActivityForResult(intent,requestCode);
    }
}


