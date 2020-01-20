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

import com.example.mentorapp.DataBase.TemplateDBHelper;
import com.example.mentorapp.Helpers.TemplatesListAdapter;
import com.example.mentorapp.R;
import com.example.mentorapp.Template;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

//import android.app.ActionBar;
//import android.widget.Toolbar;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class ListTemplatesActivity extends AppCompatActivity {

    ListView templateListView;
    Context context;
    TemplateDBHelper TDBH;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.context = getApplicationContext();

        setContentView(R.layout.list_templates_layout);

        this.TDBH = new TemplateDBHelper(this);

        this.templateListView = findViewById(R.id.list_templates_id);

        templateListView.setAdapter(new TemplatesListAdapter(this,this.TDBH));


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

        if (id == R.id.add_template_menu_item_id){
                addNewTemplate();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode){
            case 200:
                Template tempTemplate  = (Template) data.getSerializableExtra("Template");

                System.out.println("Template");
                System.out.println(tempTemplate.toJson());
                if(tempTemplate.getId() == null){
                    System.out.println("New template");
                    this.TDBH.addTemplate(tempTemplate);
                } else {
                    System.out.println("Old template");
                    this.TDBH.updateTemplate(tempTemplate);
                }

                TemplatesListAdapter TLA = (TemplatesListAdapter) this.templateListView.getAdapter();
                TLA.updateList();
                TLA.notifyDataSetChanged();
                break;
        }
    }


    private void addNewTemplate(){
        Intent intent = new Intent(context, EditTemplateActivity.class);
        intent.putExtra("Template", new Template("New Template"));
        int requestCode = 200;


        startActivityForResult(intent,requestCode);
    }
}


