package com.example.mentorapp.Helpers;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.mentorapp.Activities.TemplateEditActivity;
import com.example.mentorapp.R;
import com.example.mentorapp.Tags.Tag;

import java.util.ArrayList;

public class TagListAdapter extends ArrayAdapter<String> {

    private Tag tag;
    private Context context;

    public TagListAdapter(Context context, Tag tag){
        super(context,0);
        this.context = context;
        this.tag = tag;
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull final ViewGroup parent) {
        LayoutInflater layoutInflater = (LayoutInflater) this.context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (position == this.tag.getOptionsList().size()){

            convertView = layoutInflater.inflate(R.layout.tag_cell_new_tag, null);

            Button addButtonView = (Button) convertView.findViewById(R.id.tags_button_add_option_id);
            addButtonView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setTitle("Add Option");
                    // Set up the input
                    final EditText input = new EditText(context);
                    input.setText("");
                    // Specify the type of input expected
                    input.setInputType(InputType.TYPE_CLASS_TEXT);
                    builder.setView(input);

                    // Set up the buttons
                    builder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            String m_Text = input.getText().toString();
                            tag.getOptionsList().add(m_Text);
                            notifyDataSetChanged();
                        }
                    });
                    builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });

                    AlertDialog mAlertDialog = builder.create();
                    mAlertDialog.show();
                }
                });

            return convertView;
        }


        final String option = this.tag.getOptionsList().get(position);

        convertView = layoutInflater.inflate(R.layout.tag_cell_layout, null);
        TextView optionTextView = (TextView) convertView.findViewById(R.id.tags_tag_option_name_id);
        Button deleteButtonView = (Button) convertView.findViewById(R.id.tags_button_delete_tag_id);

        optionTextView.setText(option);

        deleteButtonView.setTag(position);

        deleteButtonView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final int position = (Integer) v.getTag();

                DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which){
                            case DialogInterface.BUTTON_POSITIVE:
                                tag.getOptionsList().remove(position);
                                notifyDataSetChanged();
                                break;

                            case DialogInterface.BUTTON_NEGATIVE:
                                break;
                        }
                    }
                };

                AlertDialog.Builder builder = new AlertDialog.Builder(v.getRootView().getContext());
                builder.setMessage("Delete '"+option+"'?").setPositiveButton("Yes", dialogClickListener)
                        .setNegativeButton("No", dialogClickListener).show();


            }
        });

        return convertView;
    }

    @Override
    public int getCount() {
        if (this.tag == null){
            return 0;
        }
        return this.tag.getOptionsList().size()+1;
    }

    public void newTag(Tag tag){
        this.tag = tag;
    }
}