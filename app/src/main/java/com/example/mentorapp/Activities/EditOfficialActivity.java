package com.example.mentorapp.Activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.mentorapp.DataBase.DBHelper;
import com.example.mentorapp.Official.MonthYear;
import com.example.mentorapp.Official.Official;
import com.example.mentorapp.R;

import java.util.ArrayList;

//import android.app.ActionBar;
//import android.widget.Toolbar;
import java.util.Arrays;
import java.util.Calendar;

public class EditOfficialActivity extends AppCompatActivity {

    Official official;


    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.context = getApplicationContext();
        this.official = (Official) getIntent().getSerializableExtra("Official");
        setContentView(R.layout.create_official_layout);



        Spinner spinnerDOBMonth = findViewById(R.id.spinner_dob_month);
        Spinner spinnerDOBYear = findViewById(R.id.spinner_dob_year);
        Spinner spinnerExpMonth = findViewById(R.id.spinner_exp_month);
        Spinner spinnerExpYear = findViewById(R.id.spinner_exp_year);

        ArrayList<String> months = new ArrayList<String>(
                Arrays.asList("", "JAN", "FEB", "MAR", "APR", "MAY", "JUN", "JUL",
                        "AUG", "SEP", "OCT", "NOV", "DEC"));

        ArrayList<String> years = new ArrayList<>();
        years.add("");
        Calendar today = Calendar.getInstance();
        int todayYear = today.get(Calendar.YEAR);
        for(int i=0; i<100; ++i){
            years.add(String.valueOf(todayYear-i));
        }

        ArrayAdapter<String> monthsAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, months);
        monthsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerDOBMonth.setAdapter(monthsAdapter);
        spinnerExpMonth.setAdapter(monthsAdapter);

        ArrayAdapter<String> yearsAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, years);
        yearsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerDOBYear.setAdapter(yearsAdapter);
        spinnerExpYear.setAdapter(yearsAdapter);


        EditText nameView = findViewById(R.id.text_edit_official_name_id);
        EditText emailView = findViewById(R.id.text_edit_official_email_id);

        nameView.setText(this.official.getName());
        emailView.setText(this.official.getEmail());
        if (this.official.getDob().getYear() == null || this.official.getDob().getMonth() == null){
            spinnerDOBMonth.setSelection(0);
            spinnerDOBYear.setSelection(0);
        } else {
            spinnerDOBMonth.setSelection(this.official.getDob().getMonth());
            spinnerDOBYear.setSelection(this.official.getDob().getYear());
        }
        if (this.official.getStartedOfficiating().getYear() == null || this.official.getStartedOfficiating().getMonth() == null){
            spinnerExpMonth.setSelection(0);
            spinnerExpYear.setSelection(0);
        } else {
            spinnerExpMonth.setSelection(this.official.getStartedOfficiating().getMonth());
            spinnerExpYear.setSelection(this.official.getStartedOfficiating().getYear());
        }

        ActionBar actionBar = getSupportActionBar();
        Toolbar mToolbar = findViewById(R.id.toolbar_edit_official_id);
        setSupportActionBar(mToolbar);


        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                saveOfficial();
                Intent returnIntent = new Intent();
                returnIntent.putExtra("Official",official);
                setResult(Activity.RESULT_OK,returnIntent);
                finish();
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.edit_official_menu, menu);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Edit Official");

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

    private void saveOfficial(){

        EditText nameView = findViewById(R.id.text_edit_official_name_id);
        EditText emailView = findViewById(R.id.text_edit_official_email_id);
        Spinner spinnerDOBMonth = findViewById(R.id.spinner_dob_month);
        Spinner spinnerDOBYear = findViewById(R.id.spinner_dob_year);
        Spinner spinnerExpMonth = findViewById(R.id.spinner_exp_month);
        Spinner spinnerExpYear = findViewById(R.id.spinner_exp_year);

        official.setEmail(String.valueOf(emailView.getText()));
        official.setName(String.valueOf(nameView.getText()));

        MonthYear dob;
        MonthYear exp;
        if(spinnerDOBMonth.getSelectedItemPosition() == 0 || spinnerDOBYear.getSelectedItemPosition() == 0){
            dob = new MonthYear();
        } else {
            dob = new MonthYear(spinnerDOBMonth.getSelectedItemPosition(), Integer.valueOf(spinnerDOBYear.getSelectedItem().toString()));
        }
        if(spinnerExpMonth.getSelectedItemPosition() == 0 || spinnerExpYear.getSelectedItemPosition() == 0){
            exp = new MonthYear();
        } else {
            exp = new MonthYear(spinnerExpMonth.getSelectedItemPosition(), Integer.valueOf(spinnerExpYear.getSelectedItem().toString()));
        }

        System.out.println();
        official.setDob(dob);
        official.setStartedOfficiating(exp);

        DBHelper ODBH = new DBHelper(context);
        if(this.official.getId() == null){
            Long newId = ODBH.addOfficial(this.official);
            this.official.setId(newId.intValue());
            System.out.println("The New ID");
            System.out.println(newId);
        } else {
            ODBH.updateOfficial(this.official);
        }
    }
}


