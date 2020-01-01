package com.example.mentorapp;


import android.os.Bundle;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;

import androidx.appcompat.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity {

    ExpandableListView expandableListView;
    ExpandableListAdapter expandableListAdapter;

    Evaluation data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        expandableListView = (ExpandableListView) findViewById(R.id.expandableListView);
        data = ExampleDataPump.getData();
        expandableListAdapter = new EvaluationListAdapter(this, data);
        expandableListView.setAdapter(expandableListAdapter);

    }

}