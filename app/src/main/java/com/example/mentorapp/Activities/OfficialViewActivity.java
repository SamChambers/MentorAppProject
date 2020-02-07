package com.example.mentorapp.Activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.mentorapp.DataBase.DBHelper;
import com.example.mentorapp.Evaluation;
import com.example.mentorapp.Helpers.OfficialEvaluationsListAdapter;
import com.example.mentorapp.Official.MonthYear;
import com.example.mentorapp.Official.Official;
import com.example.mentorapp.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

//import android.app.ActionBar;
//import android.widget.Toolbar;
import java.util.ArrayList;
import java.util.Calendar;

public class OfficialViewActivity extends AppCompatActivity {

    Official official;

    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.context = getApplicationContext();
        this.official = (Official) getIntent().getSerializableExtra("Official");


        setContentView(R.layout.official_view_layout);
        updateViews();


        ListView officialGamesListView = findViewById(R.id.official_view_games_list_id);
        ArrayList<Integer> officialGamesList = official.getEvaluationsList();
        OfficialEvaluationsListAdapter oela = new OfficialEvaluationsListAdapter(OfficialViewActivity.this,this.official);
        officialGamesListView.setAdapter(oela);

        ActionBar actionBar = getSupportActionBar();
        Toolbar mToolbar = findViewById(R.id.toolbar_view_official_id);
        setSupportActionBar(mToolbar);


        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent returnIntent = new Intent();
                setResult(Activity.RESULT_OK,returnIntent);
                finish();
            }
        });

        FloatingActionButton fab_editOfficial = (FloatingActionButton) findViewById(R.id.viewOfficial_fab_editOfficial);
        fab_editOfficial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToEditOfficial();
            }
        });

        if (this.official.getId() == null){
            goToEditOfficial();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.view_official_menu, menu);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle(official.getName());

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.delete_official_menu_item_id){
            deleteOfficial();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void deleteOfficial(){
        DBHelper ODBH = new DBHelper(context);
        for (Integer evalId : official.getEvaluationsList()){
            Evaluation eval = ODBH.getEvaluation(evalId);
            eval.setOfficialId(null);
            ODBH.updateEvaluation(eval);
        }
        ODBH.deleteOfficial(official);
        Intent returnIntent = new Intent();
        setResult(Activity.RESULT_OK,returnIntent);
        finish();
    }

    private void updateViews(){


        TextView nameView = findViewById(R.id.text_official_name_id);
        TextView ageView = findViewById(R.id.text_official_age_id);
        TextView experienceView = findViewById(R.id.text_official_officiating_exp_id);
        TextView emailView = findViewById(R.id.text_official_email_id);

        nameView.setText(this.official.getName());

        MonthYear dob = this.official.getDob();

        if(dob.getYear() == null || dob.getMonth() == null){
            ageView.setText("");
        } else {
            Calendar today = Calendar.getInstance();
            Integer years = today.get(Calendar.YEAR)-dob.getYear();
            Integer months = today.get(Calendar.MONTH)-dob.getMonth();
            if(months < 0){
                years -= 1;
                months += 12;
            }

            ageView.setText(years.toString() + " years, " + months.toString() + " months");
        }

        MonthYear exp = this.official.getStartedOfficiating();
        if(exp.getYear() == null || exp.getMonth() == null){
            experienceView.setText("");
        } else {
            Calendar today = Calendar.getInstance();
            Integer years = today.get(Calendar.YEAR)-exp.getYear();
            Integer months = today.get(Calendar.MONTH)-exp.getMonth()+1;
            if(months < 0){
                years -= 1;
                months += 12;
            }

            experienceView.setText(years.toString() + " years, " + months.toString() + " months");
        }

        emailView.setText(official.getEmail());
    }

    private void goToEditOfficial(){
        Intent intent = new Intent(context, OfficialEditActivity.class);
        intent.putExtra("Official", this.official);
        int requestCode = 500;

        startActivityForResult(intent,requestCode);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode){
            case 500:
                Official returnedOfficial = (Official) data.getExtras().getSerializable("Official");
                DBHelper ODBH = new DBHelper(context);
                this.official = ODBH.getOfficial(returnedOfficial.getId());
                updateViews();
                break;
        }
    }
}


