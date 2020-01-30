package com.example.mentorapp.Activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.NumberPicker;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.mentorapp.DataBase.DBHelper;
import com.example.mentorapp.Official.Official;
import com.example.mentorapp.R;

import java.util.ArrayList;

//import android.app.ActionBar;
//import android.widget.Toolbar;
import java.util.Calendar;

public class OfficialEditActivity extends AppCompatActivity {

    Official official;


    TextView dobView;
    TextView startedView;

    String[] months = new String[] {"JAN", "FEB", "MAR", "APR", "MAY", "JUN", "JUL",
            "AUG", "SEP", "OCT", "NOV", "DEC"};

    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.context = getApplicationContext();
        this.official = (Official) getIntent().getSerializableExtra("Official");
        setContentView(R.layout.official_edit_layout);

        EditText nameView = findViewById(R.id.text_edit_official_name_id);
        EditText emailView = findViewById(R.id.text_edit_official_email_id);
        ImageButton imageButton_dobPicker = findViewById(R.id.viewOfficial_imageButton_agePicker);
        ImageButton imageButton_expPicker = findViewById(R.id.viewOfficial_imageButton_expPicker);
        dobView = findViewById(R.id.text_editOfficial_Dob);
        startedView = findViewById(R.id.text_editOfficial_Started);

        nameView.setText(this.official.getName());

        imageButton_dobPicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickDateDOB();
            }
        });
        imageButton_expPicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickDateExp();
            }
        });

        setDob();
        setExp();

        ActionBar actionBar = getSupportActionBar();
        Toolbar mToolbar = findViewById(R.id.toolbar_edit_official_id);
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
        getMenuInflater().inflate(R.menu.edit_official_menu, menu);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Edit Official");

        return true;
    }

    private void pickDateDOB() {
        LayoutInflater inflater = getLayoutInflater();
        View dialogLayout = inflater.inflate(R.layout.datepicker_layout, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(OfficialEditActivity.this);
        builder.setView(dialogLayout);
        builder.setTitle("Pick a date");

        final NumberPicker np_dobMonth = dialogLayout.findViewById(R.id.datePicker_numberPicker_month);
        final NumberPicker np_dobYear = dialogLayout.findViewById(R.id.datePicker_numberPicker_year);

        np_dobMonth.setWrapSelectorWheel(false);
        np_dobYear.setWrapSelectorWheel(false);

        np_dobMonth.setMinValue(1);
        np_dobMonth.setMaxValue(12);
        np_dobMonth.setDisplayedValues(months);

        Calendar today = Calendar.getInstance();
        final int todayYear = today.get(Calendar.YEAR);

        ArrayList<String> years = new ArrayList<>();
        for (int i = 0; i < 100; ++i) {
            years.add(String.valueOf(todayYear - i));
        }
        np_dobYear.setDisplayedValues((String[])years.toArray(new String[100]));
        np_dobYear.setMinValue(1);
        np_dobYear.setMaxValue(100);

        if(this.official.getDob().getMonth()!=null && this.official.getDob().getMonth()!=null) {
            np_dobYear.setValue(todayYear - this.official.getDob().getYear() + 1);
            np_dobMonth.setValue(this.official.getDob().getMonth());
        }

        builder.setPositiveButton("Set", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                official.getDob().setYear(todayYear-(np_dobYear.getValue()-1));
                official.getDob().setMonth(np_dobMonth.getValue());
                setDob();
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

    private void pickDateExp() {
        LayoutInflater inflater = getLayoutInflater();
        View dialogLayout = inflater.inflate(R.layout.datepicker_layout, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(OfficialEditActivity.this);
        builder.setView(dialogLayout);
        builder.setTitle("Pick a date");

        final NumberPicker np_expMonth = dialogLayout.findViewById(R.id.datePicker_numberPicker_month);
        final NumberPicker np_expYear = dialogLayout.findViewById(R.id.datePicker_numberPicker_year);

        np_expMonth.setWrapSelectorWheel(false);
        np_expYear.setWrapSelectorWheel(false);

        np_expMonth.setMinValue(1);
        np_expMonth.setMaxValue(12);
        np_expMonth.setDisplayedValues(months);

        final Calendar today = Calendar.getInstance();
        final int todayYear = today.get(Calendar.YEAR);

        final ArrayList<String> years = new ArrayList<>();
        for (int i = 0; i < 100; ++i) {
            years.add(String.valueOf(todayYear - i));
        }
        np_expYear.setDisplayedValues((String[])years.toArray(new String[100]));
        np_expYear.setMinValue(1);
        np_expYear.setMaxValue(100);

        if(this.official.getStartedOfficiating().getMonth()!=null && this.official.getStartedOfficiating().getMonth()!=null) {
            np_expYear.setValue(todayYear - this.official.getStartedOfficiating().getYear());
            np_expMonth.setValue(this.official.getStartedOfficiating().getMonth() - 1);
        }

        builder.setPositiveButton("Set", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                official.getStartedOfficiating().setYear(todayYear-(np_expYear.getValue()-1));
                official.getStartedOfficiating().setMonth(np_expMonth.getValue());
                setExp();
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

        official.setEmail(String.valueOf(emailView.getText()));
        official.setName(String.valueOf(nameView.getText()));

        System.out.println("Started officiating");
        System.out.println(this.official.getStartedOfficiating().getYear());

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


    @Override
    public void onBackPressed() {

        backFunction();

    }

    private void backFunction(){
        saveOfficial();
        Intent returnIntent = new Intent();
        returnIntent.putExtra("Official",official);
        setResult(Activity.RESULT_OK,returnIntent);
        finish();
    }

    private void setDob(){
        if(this.official.getDob().getMonth() == null || this.official.getDob().getYear() == null){
            this.dobView.setText("");
        } else {
            this.dobView.setText(months[this.official.getDob().getMonth() - 1] + ", " + this.official.getDob().getYear().toString());
        }
    }

    private void setExp(){
        if(this.official.getStartedOfficiating().getMonth() == null || this.official.getStartedOfficiating().getYear() == null){
            this.startedView.setText("");
        } else {
            this.startedView.setText(months[this.official.getStartedOfficiating().getMonth() - 1] + ", " + this.official.getStartedOfficiating().getYear().toString());
        }
    }
}