package com.example.mentorapp.Activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.InputType;
import android.widget.EditText;
import android.widget.ListView;
import android.content.Context;
import android.view.View;
//import android.app.ActionBar;
import android.content.Intent;
//import android.widget.Toolbar;
import android.app.Activity;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mentorapp.Helpers.CommentsListAdapter;
import com.example.mentorapp.R;

import java.util.ArrayList;

public class ViewTaskCommentsActivity extends AppCompatActivity {

    ArrayList<String> comments;
    String description;
    Integer category;
    Integer position;
    Integer evaluationId;

    ListView commentsListView;
    TextView noCommentsTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.comments = (ArrayList<String>) getIntent().getSerializableExtra("MyComments");
        this.description = (String) getIntent().getSerializableExtra("MyDescription");
        this.category = (Integer) getIntent().getSerializableExtra("Category");
        this.position = (Integer) getIntent().getSerializableExtra("Position");
        this.evaluationId = (Integer) getIntent().getSerializableExtra("EvaluationID");



        setContentView(R.layout.view_task_comments_layout);

        this.commentsListView = (ListView) findViewById(R.id.list_templates_id);
        this.noCommentsTextView = (TextView) findViewById(R.id.text_comments_view_no_comments_id);

        Context context = getApplicationContext();
        commentsListView.setAdapter(new CommentsListAdapter(context, this.comments, noCommentsTextView));

        setVisibility();

        ActionBar actionBar = getSupportActionBar();

        Toolbar mToolbar = findViewById(R.id.toolbar_list_templates_id);
        setSupportActionBar(mToolbar);


        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent returnIntent = new Intent();
                returnIntent.putExtra("UpdatedComments",comments);
                returnIntent.putExtra("Category",category);
                returnIntent.putExtra("Position",position);
                returnIntent.putExtra("EvaluationID",evaluationId);
                setResult(Activity.RESULT_OK,returnIntent);
                finish();
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.view_comments_options_menu, menu);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle(this.description);

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

            builder.setTitle("Add Comment");

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
                    comments.add(m_Text);
                    setVisibility();
                    CommentsListAdapter tempAdapter =(CommentsListAdapter) commentsListView.getAdapter();
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

    void setVisibility(){
        if(this.comments.size() > 0) {
            commentsListView.setVisibility(View.VISIBLE);
            noCommentsTextView.setVisibility(View.INVISIBLE);
        } else {
            commentsListView.setVisibility(View.INVISIBLE);
            noCommentsTextView.setVisibility(View.VISIBLE);
        }
    }
}


