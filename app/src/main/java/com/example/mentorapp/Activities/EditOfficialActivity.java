package com.example.mentorapp.Activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.TextView;

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

    int exp_year = 1;
    int exp_month = 1;
    int dob_year = 1;
    int dob_month = 1;

    TextView dobView;
    TextView startedView;

    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.context = getApplicationContext();
        this.official = (Official) getIntent().getSerializableExtra("Official");
        setContentView(R.layout.create_official_layout);

        EditText nameView = findViewById(R.id.text_edit_official_name_id);
        EditText emailView = findViewById(R.id.text_edit_official_email_id);
        ImageButton imageButton_dobPicker = findViewById(R.id.viewOfficial_imageButton_agePicker);
        ImageButton imageButton_expPicker = findViewById(R.id.viewOfficial_imageButton_expPicker);

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
        AlertDialog.Builder builder = new AlertDialog.Builder(EditOfficialActivity.this);
        builder.setView(dialogLayout);
        builder.setTitle("Pick a date");

        NumberPicker np_dobMonth = dialogLayout.findViewById(R.id.datePicker_numberPicker_month);
        NumberPicker np_dobYear = dialogLayout.findViewById(R.id.datePicker_numberPicker_year);

        np_dobMonth.setWrapSelectorWheel(false);
        np_dobYear.setWrapSelectorWheel(false);

        np_dobMonth.setMinValue(1);
        np_dobMonth.setMaxValue(12);
        np_dobMonth.setDisplayedValues(new String[] {"JAN", "FEB", "MAR", "APR", "MAY", "JUN", "JUL",
                "AUG", "SEP", "OCT", "NOV", "DEC"});

        Calendar today = Calendar.getInstance();
        int todayYear = today.get(Calendar.YEAR);

        ArrayList<String> years = new ArrayList<>();
        for (int i = 0; i < 100; ++i) {
            years.add(String.valueOf(todayYear - i));
        }
        np_dobYear.setDisplayedValues((String[])years.toArray(new String[100]));
        np_dobYear.setMinValue(1);
        np_dobYear.setMaxValue(100);

        np_dobMonth.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                System.out.println(newVal);
                official.getStartedOfficiating().setMonth(newVal);
                dob_month = newVal;
                updateViews();
            }
        });
        np_dobYear.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                System.out.println(newVal);
                official.getStartedOfficiating().setYear(newVal);
                dob_year = newVal;
                updateViews();
            }
        });

        builder.show();
    }

    private void pickDateExp() {
        LayoutInflater inflater = getLayoutInflater();
        View dialogLayout = inflater.inflate(R.layout.datepicker_layout, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(EditOfficialActivity.this);
        builder.setView(dialogLayout);
        builder.setTitle("Pick a date");

        final NumberPicker np_expMonth = dialogLayout.findViewById(R.id.datePicker_numberPicker_month);
        NumberPicker np_expYear = dialogLayout.findViewById(R.id.datePicker_numberPicker_year);

        np_expMonth.setWrapSelectorWheel(false);
        np_expYear.setWrapSelectorWheel(false);

        np_expMonth.setMinValue(1);
        np_expMonth.setMaxValue(12);
        np_expMonth.setDisplayedValues(new String[] {"JAN", "FEB", "MAR", "APR", "MAY", "JUN", "JUL",
                "AUG", "SEP", "OCT", "NOV", "DEC"});

        final Calendar today = Calendar.getInstance();
        final int todayYear = today.get(Calendar.YEAR);

        final ArrayList<String> years = new ArrayList<>();
        for (int i = 0; i < 100; ++i) {
            years.add(String.valueOf(todayYear - i));
        }
        np_expYear.setDisplayedValues((String[])years.toArray(new String[100]));
        np_expYear.setMinValue(1);
        np_expYear.setMaxValue(100);

        np_expMonth.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                System.out.println(newVal);
                official.getStartedOfficiating().setMonth(Integer.getInteger(years.get(newVal)));
                exp_month = newVal;
                updateViews();
            }
        });
        np_expYear.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                System.out.println(Integer.getInteger(years.get(newVal)));
/*                Integer year = Integer.getInteger(years.get(newVal));
                if(year == Integer.valueOf(todayYear)) {

                }*/
                official.getStartedOfficiating().setYear(Integer.getInteger(years.get(newVal)));
                exp_year = newVal;
                updateViews();
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

    private void updateViews() {

/*        Integer month = th
is.official.getDob().getMonth();
        Integer year = this.official.getDob().getYear();
        String dob = this.official.getDob().getMonth() + " " + String.valueOf(this.official.getDob().getYear());
        dobView.setText(dob);*/
    }

    private void saveOfficial(){

        EditText nameView = findViewById(R.id.text_edit_official_name_id);
        EditText emailView = findViewById(R.id.text_edit_official_email_id);

        official.setEmail(String.valueOf(emailView.getText()));
        official.setName(String.valueOf(nameView.getText()));


        MonthYear exp = new MonthYear(exp_month,exp_year);
        MonthYear dob = new MonthYear(dob_month,dob_year);
        this.official.setDob(dob);
        this.official.setStartedOfficiating(exp);

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

/*    private void setMonths() {
        np_expMonth.setMaxValue(today.get(Calendar.MONTH));
        switch(today.get(Calendar.MONTH)) {
            case 1:
                np_expMonth.setDisplayedValues(new String[] {"JAN"});
            case 2:
                np_expMonth.setDisplayedValues(new String[] {"JAN", "FEB"});
            case 3:
                np_expMonth.setDisplayedValues(new String[] {"JAN", "FEB", "MAR"});
            case 4:
                np_expMonth.setDisplayedValues(new String[] {"JAN", "FEB", "MAR", "APR"});
            case 5:
                np_expMonth.setDisplayedValues(new String[] {"JAN", "FEB", "MAR", "APR", "MAY"});
            case 6:
                np_expMonth.setDisplayedValues(new String[] {"JAN", "FEB", "MAR", "APR", "MAY", "JUN"});
            case 7:
                np_expMonth.setDisplayedValues(new String[] {"JAN", "FEB", "MAR", "APR", "MAY", "JUN", "JUL"});
            case 8:
                np_expMonth.setDisplayedValues(new String[] {"JAN", "FEB", "MAR", "APR", "MAY", "JUN", "JUL",
                        "AUG"});
            case 9:
                np_expMonth.setDisplayedValues(new String[] {"JAN", "FEB", "MAR", "APR", "MAY", "JUN", "JUL",
                        "AUG", "SEP");
            case 10:
                np_expMonth.setDisplayedValues(new String[] {"JAN", "FEB", "MAR", "APR", "MAY", "JUN", "JUL",
                        "AUG", "SEP", "OCT"});
            case 11:
                np_expMonth.setDisplayedValues(new String[] {"JAN", "FEB", "MAR", "APR", "MAY", "JUN", "JUL",
                        "AUG", "SEP", "OCT", "NOV"});
            case 12:
                np_expMonth.setDisplayedValues(new String[] {"JAN", "FEB", "MAR", "APR", "MAY", "JUN", "JUL",
                        "AUG", "SEP", "OCT", "NOV", "DEC"});
                    }
    }*/

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
}