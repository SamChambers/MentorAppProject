package com.example.mentorapp;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.view.MenuItem;
import android.content.Intent;
import android.app.Activity;

import android.app.AlertDialog;
import android.widget.EditText;
import android.text.InputType;
import android.content.DialogInterface;

import java.util.ArrayList;
import java.util.List;


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
        final Task expandedListTask = (Task) getChild(listPosition, expandedListPosition);
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
        Button commentButtonView = (Button) convertView.findViewById(R.id.button_comment_id);
        final Button optionsButtonView = (Button) convertView.findViewById(R.id.button_options_id);

        // Set the description and the score
        descriptionView.setText(expandedListTask.getDescription());
        scoreView.setText(String.valueOf(expandedListTask.getScore()));

        // Set the color of the task if it is flagged
        //TODO: Make this link up with the EvaluationFragment long click listener
        // Probably in the res->values->colors.xml file
        if(expandedListTask.getFlagged()) {
            convertView.setBackgroundColor(Color.GRAY);
        } else {
            convertView.setBackgroundColor(Color.WHITE);
        }

        plusButtonView.setBackgroundResource(android.R.drawable.btn_default);
        minusButtonView.setBackgroundResource(android.R.drawable.btn_default);
        optionsButtonView.setBackgroundResource(android.R.drawable.btn_default);

        if(expandedListTask.numberOfComments() > 0){
            commentButtonView.setBackgroundColor(Color.BLUE);
        } else {
            commentButtonView.setBackgroundResource(android.R.drawable.btn_default);
        }

        // Tag the location to the buttons so they know which task they are associated with
        Pair<Integer, Integer> locationPair = new Pair<Integer, Integer>(listPosition, expandedListPosition);

        scoreView.setTag(locationPair);
        plusButtonView.setTag(locationPair);
        minusButtonView.setTag(locationPair);
        commentButtonView.setTag(locationPair);
        optionsButtonView.setTag(locationPair);

        scoreView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the task location
                Pair<Integer,Integer> locationPair = (Pair<Integer, Integer>) v.getTag();
                final Integer listPosition = locationPair.first;
                final Integer expandedListPosition = locationPair.second;

                AlertDialog.Builder builder = new AlertDialog.Builder(context);

                final String description = "Score: " + data.getTaskFromCategory(listPosition,expandedListPosition).getDescription();
                builder.setTitle(description);

                // Set up the input
                final EditText input = new EditText(context);
                // Specify the type of input expected
                input.setInputType(InputType.TYPE_CLASS_NUMBER);
                builder.setView(input);

                // Set up the buttons
                builder.setPositiveButton("Set", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Integer m_Text = Integer.parseInt(input.getText().toString());
                        data.getTaskFromCategory(listPosition,expandedListPosition).setScore(m_Text);
                        notifyDataSetChanged();
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
        });

        // Set the plus button listener
        plusButtonView.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Get the task location
                Pair<Integer,Integer> locationPair = (Pair<Integer, Integer>) v.getTag();
                Integer listPosition = locationPair.first;
                Integer expandedListPosition = locationPair.second;
                // Edit the score
                data.getTaskFromCategory(listPosition, expandedListPosition).increaseScore(1);
                //Tell system it has changed
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
                // Edit the task
                data.getTaskFromCategory(listPosition, expandedListPosition).increaseScore(-1);
                //Update the view
                notifyDataSetChanged();
            }
        });

        commentButtonView.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Get the task location
                Pair<Integer,Integer> locationPair = (Pair<Integer, Integer>) v.getTag();
                final Integer listPosition = locationPair.first;
                final Integer expandedListPosition = locationPair.second;

                AlertDialog.Builder builder = new AlertDialog.Builder(context);

                final String description = data.getTaskFromCategory(listPosition,expandedListPosition).getDescription();
                builder.setTitle(description);

                // Set up the input
                final EditText input = new EditText(context);
                // Specify the type of input expected
                input.setInputType(InputType.TYPE_CLASS_TEXT);
                builder.setView(input);

                // Set up the buttons
                builder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String m_Text = input.getText().toString();
                        data.getTaskFromCategory(listPosition,expandedListPosition).addComment(m_Text);
                        notifyDataSetChanged();
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
        });

        optionsButtonView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the task location
                Pair<Integer,Integer> locationPair = (Pair<Integer, Integer>) v.getTag();
                final Integer listPosition = locationPair.first;
                final Integer expandedListPosition = locationPair.second;

                //Creating the instance of PopupMenu
                PopupMenu popup = new PopupMenu(context, optionsButtonView);
                //Inflating the Popup using xml file
                //popup.getMenuInflater()
                //        .inflate(R.menu.task_options_menu_layout, popup.getMenu());

                final String options[] = {"Flag","See Comments","Edit Description"};
                for(int i=0; i < options.length; ++i) {
                    popup.getMenu().add(options[i]);
                }


                //registering popup with OnMenuItemClickListener
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (String.valueOf(item.getTitle())) {
                            //TODO: Find out if we want users editing the task description. And
                            // if we do, then figure out if we want it to change for all of the
                            // evaluations or just one.
                            case "Edit Description":
                                AlertDialog.Builder builder = new AlertDialog.Builder(context);

                                final String description = data.getTaskFromCategory(listPosition,expandedListPosition).getDescription();
                                builder.setTitle("Edit Description");

                                // Set up the input
                                final EditText input = new EditText(context);
                                input.setText(description);
                                // Specify the type of input expected
                                input.setInputType(InputType.TYPE_CLASS_TEXT);
                                builder.setView(input);

                                // Set up the buttons
                                builder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        String m_Text = input.getText().toString();
                                        data.getTaskFromCategory(listPosition,expandedListPosition).setDescription(m_Text);
                                        notifyDataSetChanged();
                                    }
                                });
                                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.cancel();
                                    }
                                });

                                builder.show();
                                /*
                                Toast.makeText(
                                        context,
                                        "You Clicked : " + item.getTitle(),
                                        Toast.LENGTH_SHORT
                                ).show();
                                 */
                                break;
                            case "Flag":
                                data.getTaskFromCategory(listPosition,expandedListPosition).switchFlagged();
                                notifyDataSetChanged();
                                break;
                            case "See Comments":
                                goToCommentsPage(listPosition,expandedListPosition);
                                break;
                        }
                        return true;
                    }
                });

                popup.show(); //showing popup menu
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
        TextView listTitleTextView = (TextView) convertView.findViewById(R.id.listTitle);
        TextView scoreTextView = (TextView) convertView.findViewById(R.id.text_category_score_id);
        //Set the title
        listTitleTextView.setTypeface(null, Typeface.BOLD);
        listTitleTextView.setText(listTitle);
        scoreTextView.setText(String.valueOf(taskGroup.getScore()));
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



    private void goToCommentsPage(Integer listPosition, Integer expandedListPosition){
        Intent intent = new Intent(this.context,ViewTaskCommentsActivity.class);
        ArrayList<String> myComments = (ArrayList<String>)data.getTaskFromCategory(listPosition,expandedListPosition).getComments();
        intent.putExtra("MyComments", myComments);
        intent.putExtra("MyDescription", data.getTaskFromCategory(listPosition,expandedListPosition).getDescription());
        intent.putExtra("Category", listPosition);
        intent.putExtra("Position", expandedListPosition);
        intent.putExtra("Evaluation", data.getEvaluationPosition());

        int requestCode = 100;
        ((Activity) context).startActivityForResult(intent,requestCode);

    }
}