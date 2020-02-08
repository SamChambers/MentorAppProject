package com.example.mentorapp.Activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.mentorapp.DataBase.DBHelper;
import com.example.mentorapp.Helpers.TemplateListAdapter;
import com.example.mentorapp.R;
import com.example.mentorapp.Template;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

//import android.app.ActionBar;
//import android.widget.Toolbar;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class TemplateEditActivity extends AppCompatActivity {

    Template template;

    ExpandableListView expandableList;
    TextView templateNameView;
    DBHelper dbh;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.context = getApplicationContext();

        this.dbh = new DBHelper(this.context);

        this.template = (Template) getIntent().getSerializableExtra("Template");

        setContentView(R.layout.template_edit_2_layout);

        this.expandableList = findViewById(R.id.editTemplate_expandableListView);
        this.templateNameView = findViewById(R.id.comment_textView_templateName);

        FloatingActionButton addCategoryFab = (FloatingActionButton) findViewById(R.id.template_edit_add_category_fab);
        addCategoryFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addCategory();
            }
        });

        this.expandableList.setAdapter(new TemplateListAdapter(TemplateEditActivity.this, this.template));

        this.templateNameView.setText(this.template.getName());
        this.templateNameView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(TemplateEditActivity.this);
                builder.setTitle("Change Title");

                // Set up the input
                final EditText input = new EditText(context);
                input.setText(template.getName());
                // Specify the type of input expected
                input.setInputType(InputType.TYPE_CLASS_TEXT);
                builder.setView(input);

                // Set up the buttons
                builder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String m_Text = input.getText().toString();
                        template.setName(m_Text);
                        templateNameView.setText(m_Text);

                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                AlertDialog mAlertDialog = builder.create();
                mAlertDialog.show();
            }
        });

        ActionBar actionBar = getSupportActionBar();
        Toolbar mToolbar = findViewById(R.id.editTemplate_toolbar);
        mToolbar.setTitle(this.template.getName());
        setSupportActionBar(mToolbar);


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
        getMenuInflater().inflate(R.menu.edit_template_menu, menu);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle(this.template.getName());

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        return super.onOptionsItemSelected(item);
    }

    private void addCategory(){
        LayoutInflater inflater = LayoutInflater.from(TemplateEditActivity.this);
        View dialogLayout = inflater.inflate(R.layout.template_add_category_alert_layout, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(TemplateEditActivity.this);
        builder.setView(dialogLayout);
        builder.setTitle("Add category");

        final EditText description = dialogLayout.findViewById(R.id.text_template_category_description_id);
        final EditText weight = dialogLayout.findViewById(R.id.text_template_category_weight_id);

        // Set up the buttons
        builder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String descriptionText = description.getText().toString();
                Float weightText = Float.parseFloat(weight.getText().toString());
                template.addCategory(descriptionText, weightText);
                TemplateListAdapter tla = (TemplateListAdapter) expandableList.getExpandableListAdapter();
                tla.notifyDataSetChanged();

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


    @Override
    public void onBackPressed() {
        backFunction();
    }

    private void backFunction(){
        saveTemplate();
        Intent returnIntent = new Intent();
        setResult(Activity.RESULT_OK,returnIntent);
        finish();
    }

    private void saveTemplate(){
        this.template.setName(this.templateNameView.getText().toString());
        if (this.template.getId() != null) {
            this.dbh.updateTemplate(this.template);
        } else {
            this.dbh.addTemplate(this.template);
        }
    }
}


