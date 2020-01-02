package com.example.mentorapp;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class EvaluationFragment extends Fragment {

    //Variables to hold information on the expandable list view
    // These will be set in the create view
    ExpandableListView expandableListView;
    ExpandableListAdapter expandableListAdapter;

    //Variable to hold the evaluation information
    // This will be set in the constructor
    Evaluation data;

    //Basic constructor for testing (Pull from sample data pump)
    public EvaluationFragment(){
        this.data = ExampleDataPump.getData();
    }

    //Constructor with the data being passed in
    public EvaluationFragment(Evaluation evaluation){
        this.data = evaluation;
    }

    //Set the adapter for the expandable list view
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //Inflate the view
        View convertView = inflater.inflate(R.layout.evaluation_fragment_layout,container,false);

        expandableListView = (ExpandableListView) convertView.findViewById(R.id.expandableListView_id);
        expandableListAdapter = new EvaluationListAdapter(getContext(), data);
        expandableListView.setAdapter(expandableListAdapter);

        expandableListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                if (ExpandableListView.getPackedPositionType(id) == ExpandableListView.PACKED_POSITION_TYPE_CHILD) {
                    int groupPosition = ExpandableListView.getPackedPositionGroup(id);
                    int childPosition = ExpandableListView.getPackedPositionChild(id);

                    // You now have everything that you would as if this was an OnChildClickListener()
                    // Add your logic here.
                    System.out.println("Long Click!!!");
                    Task tempTask = data.getTaskFromCategory(groupPosition,childPosition);
                    Boolean flagged = tempTask.getFlagged();
                    if(flagged == Boolean.TRUE){
                        tempTask.setFlagged(Boolean.FALSE);
                        view.setBackgroundColor(Color.WHITE);
                    } else {
                        tempTask.setFlagged(Boolean.TRUE);
                        view.setBackgroundColor(Color.GRAY);
                    }

                    // Return true as we are handling the event.
                    return true;
                }
                return false;
            }
        });

        return convertView;
    }
}
