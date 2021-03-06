package com.example.mentorapp.Activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.mentorapp.DataBase.DBHelper;
import com.example.mentorapp.Helpers.TemplatesListAdapter;
import com.example.mentorapp.R;
import com.example.mentorapp.Template;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

//import android.app.ActionBar;
//import android.widget.Toolbar;
import android.view.View;
import android.widget.ListView;

public class TemplateListActivity extends AppCompatActivity {

    ListView templateListView;
    Context context;
    DBHelper DBH;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.context = getApplicationContext();

        Boolean newTemplate = (Boolean) getIntent().getSerializableExtra("NewTemplate");
        if(newTemplate){
            addNewTemplate();
        }

        setContentView(R.layout.template_list_layout);

        this.DBH = new DBHelper(this);

        this.templateListView = findViewById(R.id.list_templates_id);

        templateListView.setAdapter(new TemplatesListAdapter(this,this.DBH));


        ActionBar actionBar = getSupportActionBar();

        Toolbar mToolbar = findViewById(R.id.toolbar_list_templates_id);
        setSupportActionBar(mToolbar);


        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent returnIntent = new Intent();
                setResult(Activity.RESULT_OK,returnIntent);
                finish();
            }
        });


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.floatingActionButton);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addNewTemplate();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.list_templates_options_menu, menu);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Templates");

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
    /*
        if (id == R.id.sort_templates_menu_item_id){
            // Handle sorting
            return true;
        }

     */

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode){
            case 200:
                TemplatesListAdapter TLA = (TemplatesListAdapter) this.templateListView.getAdapter();
                TLA.updateList();
                TLA.notifyDataSetChanged();
                break;
        }
    }


    private void addNewTemplate(){
        Intent intent = new Intent(context, TemplateEditActivity.class);
        intent.putExtra("Template", new Template("New Template"));
        int requestCode = 200;

        startActivityForResult(intent,requestCode);
    }
}


