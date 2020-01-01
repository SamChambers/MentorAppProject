package com.example.mentorapp;

import android.content.Context;
import android.graphics.Typeface;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.TextView;

public class EvaluationListAdapter extends BaseExpandableListAdapter {

    // Private variables
    private Context context;
    private Evaluation data;

    // Standard constructor
    public EvaluationListAdapter(Context context, Evaluation data) {
        this.context = context;
        this.data = data;
    }

    // Get the specific task from a category
    @Override
    public Object getChild(int listPosition, int expandedListPosition) {
        return this.data.getTaskFromCategory(listPosition,expandedListPosition);
    }

    // Get child ID (Standard I guess)
    @Override
    public long getChildId(int listPosition, int expandedListPosition) {
        return expandedListPosition;
    }

    // Create a new view and update the text fields and set the button callbacks
    @Override
    public View getChildView(int listPosition, final int expandedListPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {
        // Get the task to be placed on the view
        final Task expandedListText = (Task) getChild(listPosition, expandedListPosition);
        // If we need to make a new view, load it
        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.task_layout, null);
        }

        // Connect to the objects in the view
        TextView descriptionView = (TextView) convertView.findViewById(R.id.text_description_id);
        TextView scoreView = (TextView) convertView.findViewById(R.id.text_score_id);
        Button plusButtonView = (Button) convertView.findViewById(R.id.button_plus_id);
        Button minusButtonView = (Button) convertView.findViewById(R.id.button_minus_id);

        // Set the description and the score
        descriptionView.setText(expandedListText.getDescription());
        scoreView.setText(String.valueOf(expandedListText.getScore()));

        // Tag the location to the buttons so they know which task they are associated with
        Pair<Integer, Integer> locationPair = new Pair<Integer, Integer>(listPosition, expandedListPosition);
        plusButtonView.setTag(locationPair);
        minusButtonView.setTag(locationPair);

        // Set the plus button listener
        plusButtonView.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Get the task location
                Pair<Integer,Integer> locationPair = (Pair<Integer, Integer>) v.getTag();
                Integer listPosition = locationPair.first;
                Integer expandedListPosition = locationPair.second;
                // Grab the task
                Task tempTask = data.getTaskFromCategory(listPosition, expandedListPosition);
                // Edit the score
                Integer currentScore = tempTask.getScore();
                tempTask.setScore(currentScore+1);
                //Save the change
                data.setTask(listPosition,expandedListPosition,tempTask);
                //Update the view
                notifyDataSetChanged();
            }
        });

        //Set the minus button listener
        minusButtonView.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Get the task location
                Pair<Integer,Integer> locationPair = (Pair<Integer, Integer>) v.getTag();
                Integer listPosition = locationPair.first;
                Integer expandedListPosition = locationPair.second;
                // Grab the task
                Task tempTask = data.getTaskFromCategory(listPosition, expandedListPosition);
                // Edit the score
                Integer currentScore = tempTask.getScore();
                tempTask.setScore(currentScore-1);
                //Save the change
                data.setTask(listPosition,expandedListPosition,tempTask);
                //Update the view
                notifyDataSetChanged();
            }
        });

        return convertView;
    }

    // Get the total number of tasks in the category
    @Override
    public int getChildrenCount(int listPosition) {
        return this.data.getCategoryFromPosition(listPosition).getNumberOfTasks();
    }

    // Get the category based on its location
    @Override
    public Object getGroup(int listPosition) {
        return this.data.getCategoryFromPosition(listPosition);
    }

    // Get the total number of categories
    @Override
    public int getGroupCount() {
        return this.data.getCategoriesList().size();
    }

    // Get the category id (Standard)
    @Override
    public long getGroupId(int listPosition) {
        return listPosition;
    }

    // Make the category view
    @Override
    public View getGroupView(int listPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        // Get the category title
        Category taskGroup = (Category) getGroup(listPosition);
        String listTitle = taskGroup.getTitle();
        //Load the view if we need to create one
        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.context.
                    getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.category_layout, null);
        }
        //Connect to the title text box
        TextView listTitleTextView = (TextView) convertView
                .findViewById(R.id.listTitle);
        //Set the title
        listTitleTextView.setTypeface(null, Typeface.BOLD);
        listTitleTextView.setText(listTitle);
        return convertView;
    }

    //Has stable ids (Standard)
    @Override
    public boolean hasStableIds() {
        return false;
    }

    //Is child selectable (Standard)
    @Override
    public boolean isChildSelectable(int listPosition, int expandedListPosition) {
        return true;
    }
}