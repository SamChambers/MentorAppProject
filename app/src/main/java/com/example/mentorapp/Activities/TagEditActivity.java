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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.mentorapp.DataBase.DBHelper;
import com.example.mentorapp.Evaluation;
import com.example.mentorapp.Game;
import com.example.mentorapp.Helpers.OptionsEvaluationsListAdapter;
import com.example.mentorapp.Helpers.TagListAdapter;
import com.example.mentorapp.Official.Official;
import com.example.mentorapp.R;
import com.example.mentorapp.Tags.Tag;
import com.example.mentorapp.Tags.TagOptions;
import com.example.mentorapp.Template;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

//import android.app.ActionBar;
//import android.widget.Toolbar;

public class TagEditActivity extends AppCompatActivity {

    private DBHelper dbh;
    private Context context;
    private List<Tag> tagList;
    private TagListAdapter tla;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.context = getApplicationContext();

        setContentView(R.layout.edit_tags_layout);

        ActionBar actionBar = getSupportActionBar();
        Toolbar mToolbar = findViewById(R.id.edit_tags_toolbar);
        setSupportActionBar(mToolbar);

        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backFunction();
            }
        });

        this.dbh = new DBHelper(getApplicationContext());

        final Spinner tagSpinner = findViewById(R.id.edit_tags_select_tag_spinner_id);
        Button newTagButton = findViewById(R.id.edit_tags_add_new_tag_button_id);
        Button deleteTagButton = findViewById(R.id.edit_tags_delete_tag_button_id);
        ListView optionsList = findViewById(R.id.edit_tags_current_tag_list_id);

        this.tagList = this.dbh.allTags();
        final ArrayList<String> tagNames = new ArrayList<>();
        for (Tag t : tagList){
            tagNames.add(t.getName());
        }

        ArrayAdapter<String> templateAdapter = new ArrayAdapter<>(context,
                R.layout.spinner_item_layout, tagNames);
        tagSpinner.setAdapter(templateAdapter);
        tagSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                tla.newTag(tagList.get(position));
                tla.notifyDataSetChanged();
                return;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                return;
            }
        });

        Tag startingTag = null;
        if (tagList.size() != 0){
            startingTag = tagList.get(0);
        }
        this.tla = new TagListAdapter(TagEditActivity.this, startingTag);
        optionsList.setAdapter(tla);

        newTagButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(TagEditActivity.this);
                builder.setTitle("Add Tag");
                // Set up the input
                final EditText input = new EditText(context);
                input.setText("");
                // Specify the type of input expected
                input.setInputType(InputType.TYPE_CLASS_TEXT);
                builder.setView(input);

                // Set up the buttons
                builder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String m_Text = input.getText().toString();
                        Tag newTag = new Tag(m_Text);
                        tagNames.add(m_Text);
                        if (tagNames.size()==1){
                            ArrayAdapter<String> templateAdapter = new ArrayAdapter<>(context,
                                    R.layout.spinner_item_layout, tagNames);
                            tagSpinner.setAdapter(templateAdapter);
                        }
                        tagSpinner.setSelection(tagNames.size()-1);
                        tagList.add(newTag);
                        tla.newTag(newTag);
                        tla.notifyDataSetChanged();
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

        deleteTagButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (tagNames.size() == 0){
                    return;
                }
                AlertDialog.Builder builder = new AlertDialog.Builder(TagEditActivity.this);

                final String description = "Delete '"+ tagSpinner.getSelectedItem().toString() +"'?";
                builder.setTitle(description);

                // Set up the buttons
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        int position = tagSpinner.getSelectedItemPosition();
                        dbh.deleteTag(tagList.get(position));
                        tagList.remove(position);
                        tagNames.remove(position);

                        tagSpinner.setAdapter(new ArrayAdapter<>(context,
                                R.layout.spinner_item_layout, tagNames));
                        tagSpinner.setSelection(0);
                        if(tagList.size() > 0) {
                            tla.newTag(tagList.get(0));
                        } else {
                            tla.newTag(null);

                        }
                        tla.notifyDataSetChanged();
                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        return;
                    }
                });

                builder.show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.game_options_menu, menu);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Game Tags");

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

        List<Tag> existingTagList = this.dbh.allTags();
        ArrayList<String> tagNames = new ArrayList<>();
        for(Tag tag : this.tagList){
            tagNames.add(tag.getName());
        }
        for(Tag ta : existingTagList){
            if(tagNames.indexOf(ta.getName()) == -1){
                System.out.println("Deleting Tag");
                System.out.println(ta.getId());
                this.dbh.deleteTag(ta);
            }
        }
        for (Tag t : this.tagList){
            if (t.getId() == null){
                this.dbh.addTag(t);
            } else {
                this.dbh.updateTag(t);
            }
        }

        Intent returnIntent = new Intent();
        setResult(Activity.RESULT_OK,returnIntent);
        finish();
    }

}



