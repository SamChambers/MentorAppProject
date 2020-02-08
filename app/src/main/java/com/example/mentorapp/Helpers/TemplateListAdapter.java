package com.example.mentorapp.Helpers;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.InputType;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageButton;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupMenu;
import android.widget.Spinner;
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
        final TemplateTask task = (TemplateTask) getChild(listPosition,expandedListPosition);

        //Load the view if we need to create one
        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.context.
                    getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.template_task_layout, null);
        }
        //Connect to the title text box
        TextView taskNameTextView = convertView.findViewById(R.id.template_text_task_description_id);
        final ImageButton optionsButton = convertView.findViewById(R.id.template_edit_task_menu);
        TextView taskWeightView = convertView.findViewById(R.id.template_edit_task_weight_id);

        //Set the title
        taskNameTextView.setText(task.getDescription());

        taskWeightView.setText(task.getWeight().toString());

        // Tag the location to the buttons so they know which task they are associated with
        Pair<Integer, Integer> locationPair = new Pair<Integer, Integer>(listPosition, expandedListPosition);

        optionsButton.setTag(locationPair);
        optionsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                //handle add task
                PopupMenu popup = new PopupMenu(context, optionsButton);
                final String options[] = {"Edit","Delete"};
                for(int i=0; i < options.length; ++i) {
                    popup.getMenu().add(options[i]);
                }

                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {

                        switch (String.valueOf(item.getTitle())) {
                            case "Edit":
                                AlertDialog.Builder builder = new AlertDialog.Builder(context);

                                final String description = task.getDescription();
                                builder.setTitle("Edit Description");

                                // Set up the input
                                final EditText input = new EditText(context);
                                input.setText(description);
                                // Specify the type of input expected
                                input.setInputType(InputType.TYPE_CLASS_TEXT);
                                builder.setView(input);

                                // Set up the buttons
                                builder.setPositiveButton("Okay", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        String m_Text = input.getText().toString();
                                        task.setDescription(m_Text);
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
                            case "Delete":
                                // Get the task location
                                Pair<Integer,Integer> locationPair = (Pair<Integer, Integer>) v.getTag();
                                int listPosition = locationPair.first;
                                int expandedListPosition = locationPair.second;
                                // Edit the score
                                template.getCategories().get(listPosition).getTasks().remove(expandedListPosition);
                                //Tell system it has changed
                                notifyDataSetChanged();
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
        System.out.println("Getting Child Count");
        System.out.println(this.template.getCategory(listPosition).getTasks().size());
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
        final TemplateCategory tc = (TemplateCategory) getGroup(listPosition);
        //Load the view if we need to create one
        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.context.
                    getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.template_category_layout, null);
        }
        //Connect to the title text box
        TextView categoryNameTextView = convertView.findViewById(R.id.category_textView_templateTextCategoryName);
        TextView categoryWeightTextView = convertView.findViewById(R.id.template_edit_category_weight_id);
        ImageButton addTaskCategoryButton = convertView.findViewById(R.id.template_edit_category_add_task);
        addTaskCategoryButton.setFocusable(false);
        final ImageButton menuCategoryButton = convertView.findViewById(R.id.template_edit_category_menu);
        menuCategoryButton.setFocusable(false);
        //Set the title
        categoryNameTextView.setText(tc.getName());
        categoryWeightTextView.setText(tc.getWeight().toString());

        addTaskCategoryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addTask(tc);
            }
        });

        // Tag the location to the buttons so they know which task they are associated with
        int location = listPosition;
        menuCategoryButton.setTag(location);
        menuCategoryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                //handle add task
                PopupMenu popup = new PopupMenu(context, menuCategoryButton);
                final String options[] = {"Edit","Delete"};
                for(int i=0; i < options.length; ++i) {
                    popup.getMenu().add(options[i]);
                }

                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {

                        switch (String.valueOf(item.getTitle())) {
                            case "Edit":
                                AlertDialog.Builder builder = new AlertDialog.Builder(context);

                                final String description = tc.getName();
                                builder.setTitle("Edit Description");

                                // Set up the input
                                final EditText input = new EditText(context);
                                input.setText(description);
                                // Specify the type of input expected
                                input.setInputType(InputType.TYPE_CLASS_TEXT);
                                builder.setView(input);

                                // Set up the buttons
                                builder.setPositiveButton("Okay", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        String m_Text = input.getText().toString();
                                        tc.setName(m_Text);
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
                            case "Delete":
                                // Get the task location
                                int listPosition = (int) v.getTag();
                                // Edit the score
                                template.getCategories().remove(listPosition);
                                //Tell system it has changed
                                notifyDataSetChanged();
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


    private void addTask(final TemplateCategory tc){
        LayoutInflater inflater = LayoutInflater.from(context);
        View dialogLayout = inflater.inflate(R.layout.template_add_task_alert_layout, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setView(dialogLayout);
        builder.setTitle(tc.getName());

        final EditText description = dialogLayout.findViewById(R.id.text_template_description_id);
        final EditText weight = dialogLayout.findViewById(R.id.text_template_weight_id);

        // Set up the buttons
        builder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String descriptionText = description.getText().toString();
                Float weightText = Float.parseFloat(weight.getText().toString());
                tc.addTask(descriptionText,weightText);
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

}