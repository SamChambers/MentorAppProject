package com.example.mentorapp.Helpers;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.InputType;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.mentorapp.Activities.TemplateEditActivity;
import com.example.mentorapp.R;
import com.example.mentorapp.Template;
import com.example.mentorapp.TemplateCategory;
import com.example.mentorapp.TemplateTask;


public class TemplateListAdapter extends BaseExpandableListAdapter {

    // Private variables
    private Template template;
    private Context context;

    // Standard constructor
    public TemplateListAdapter(Context context, Template template) {
        this.context = context;
        this.template = template;
    }

    // Get the specific task from a category
    @Override
    public Object getChild(int listPosition, int expandedListPosition) {
        return this.template.getCategory(listPosition).getTasks().get(expandedListPosition);
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
        // Get the category title
        TemplateCategory tc = (TemplateCategory) getGroup(listPosition);
        TemplateTask task = (TemplateTask) getChild(listPosition,expandedListPosition);

        //Load the view if we need to create one
        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.context.
                    getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.template_task_layout, null);
        }
        //Connect to the title text box
        TextView taskNameTextView = (TextView) convertView.findViewById(R.id.template_text_task_description_id);
        Button deleteTaskButton = (Button) convertView.findViewById(R.id.button_delete_template_task_id);
        //Set the title
        taskNameTextView.setText(task.getDescription());

        // Tag the location to the buttons so they know which task they are associated with
        Pair<Integer, Integer> locationPair = new Pair<Integer, Integer>(listPosition, expandedListPosition);

        deleteTaskButton.setTag(locationPair);

        // Set the plus button listener
        deleteTaskButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Get the task location
                Pair<Integer,Integer> locationPair = (Pair<Integer, Integer>) v.getTag();
                int listPosition = locationPair.first;
                int expandedListPosition = locationPair.second;
                // Edit the score
                template.getCategories().get(listPosition).getTasks().remove(expandedListPosition);
                //Tell system it has changed
                notifyDataSetChanged();
            }
        });

        return convertView;
    }

    // Get the total number of tasks in the category
    @Override
    public int getChildrenCount(int listPosition) {
        return this.template.getCategory(listPosition).getTasks().size();
    }

    // Get the category based on its location
    @Override
    public Object getGroup(int listPosition) {
        return this.template.getCategory(listPosition);
    }

    // Get the total number of categories
    @Override
    public int getGroupCount() {
        return this.template.getCategories().size();
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
        TemplateCategory tc = (TemplateCategory) getGroup(listPosition);
        //Load the view if we need to create one
        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.context.
                    getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.template_category_layout, null);
        }
        //Connect to the title text box
        TextView categoryNameTextView = (TextView) convertView.findViewById(R.id.category_textView_templateTextCategoryName);
        Button deleteCategoryButton = (Button) convertView.findViewById(R.id.button_delete_template_category_id);
        //Set the title
        categoryNameTextView.setText(tc.getName());

        // Tag the location to the buttons so they know which task they are associated with
        int location = listPosition;

        deleteCategoryButton.setTag(location);

        // Set the plus button listener
        deleteCategoryButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Get the task location
                int listPosition = (int) v.getTag();
                // Edit the score
                template.getCategories().remove(listPosition);
                //Tell system it has changed
                notifyDataSetChanged();
            }
        });

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