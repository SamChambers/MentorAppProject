package com.example.mentorapp.Activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.mentorapp.Helpers.CommentsListAdapter;
import com.example.mentorapp.Helpers.TemplateListAdapter;
import com.example.mentorapp.R;
import com.example.mentorapp.Template;

//import android.app.ActionBar;
//import android.widget.Toolbar;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.FrameLayout;
import android.widget.LinearLayout.LayoutParams;

public class EditTemplateActivity extends AppCompatActivity {

    Template template;

    ExpandableListView expandableList;
    TextView templateNameView;

    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.context = getApplicationContext();

        this.template = (Template) getIntent().getSerializableExtra("Template");

        setContentView(R.layout.create_template_layout);

        this.expandableList = findViewById(R.id.template_expandableListView_id);
        this.templateNameView = findViewById(R.id.text_template_name_id);
        Button buttonAddCategory = findViewById(R.id.button_template_add_category_id);
        Button buttonAddTask = findViewById(R.id.button_template_add_task_id);

        this.expandableList.setAdapter(new TemplateListAdapter(this.context, this.template));

        buttonAddCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(EditTemplateActivity.this);

                builder.setTitle("Add Category");

                // Set up the input
                final EditText input = new EditText(context);
                // Specify the type of input expected
                input.setInputType(InputType.TYPE_CLASS_TEXT);
                builder.setView(input);

                // Set up the buttons
                builder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String m_Text = input.getText().toString();
                        template.addCategory(m_Text);
                        TemplateListAdapter tla = (TemplateListAdapter) expandableList.getExpandableListAdapter();
                        tla.notifyDataSetChanged();

                        System.out.println(template.getCategories().size());
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
        });

        buttonAddTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                LayoutInflater inflater = getLayoutInflater();
                View dialogLayout = inflater.inflate(R.layout.template_add_task_alert_layout, null);
                AlertDialog.Builder builder = new AlertDialog.Builder(EditTemplateActivity.this);
                builder.setView(dialogLayout);
                builder.setTitle("Add Task");

                final Spinner spinner = (Spinner) dialogLayout.findViewById(R.id.spinner_template_category_id);
                final EditText description = (EditText) dialogLayout.findViewById(R.id.text_template_description_id);
                final EditText weight = (EditText) dialogLayout.findViewById(R.id.text_template_weight_id);

                ArrayAdapter<String> adapter = new ArrayAdapter<String>(context,
                        android.R.layout.simple_spinner_item, template.getCategoryNames());

                spinner.setAdapter(adapter);

                // Set up the buttons
                builder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String descriptionText = description.getText().toString();
                        Float weightText = Float.parseFloat(weight.getText().toString());
                        String categorySelection = spinner.getSelectedItem().toString();
                        template.getCategory(categorySelection).addTask(descriptionText,weightText);
                        TemplateListAdapter tla = (TemplateListAdapter) expandableList.getExpandableListAdapter();
                        tla.notifyDataSetChanged();

                        System.out.println(template.getCategories().size());
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
        });


        ActionBar actionBar = getSupportActionBar();
        Toolbar mToolbar = findViewById(R.id.toolbar_edit_template_id);
        setSupportActionBar(mToolbar);


        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent returnIntent = new Intent();
                returnIntent.putExtra("Template",template);
                setResult(Activity.RESULT_OK,returnIntent);
                finish();
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

        //noinspection SimplifiableIfStatement
        if (id == R.id.add_category_menu_item_id) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);

            builder.setTitle("Add Category");

            // Set up the input
            final EditText input = new EditText(getApplicationContext());
            // Specify the type of input expected
            input.setInputType(InputType.TYPE_CLASS_TEXT);
            builder.setView(input);

            // Set up the buttons
            builder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    String m_Text = input.getText().toString();
                    template.addCategory(m_Text);
                    CommentsListAdapter tempAdapter =(CommentsListAdapter) expandableList.getAdapter();
                    tempAdapter.notifyDataSetChanged();
                }
            });
            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });

            builder.show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}


