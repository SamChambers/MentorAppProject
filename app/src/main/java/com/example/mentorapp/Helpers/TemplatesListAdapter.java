package com.example.mentorapp.Helpers;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.mentorapp.Activities.TemplateEditActivity;
import com.example.mentorapp.DataBase.DBHelper;
import com.example.mentorapp.R;
import com.example.mentorapp.Template;

public class TemplatesListAdapter extends ArrayAdapter<Template> {

    private List<Template> templatesList;
    private Context context;
    private DBHelper TDBH;


    public TemplatesListAdapter(Context context, DBHelper tdbh){
        super(context,0);
        this.context = context;
        this.TDBH = tdbh;
        updateList();
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull final ViewGroup parent) {

        // Get the template we are showing
        Template template = this.templatesList.get(position);

        // If we need to make a new view, load it
        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.template_list_item_layout, null);
        }

        ImageButton deleteButton = convertView.findViewById(R.id.button_delete_template);
        TextView nameTextView = convertView.findViewById(R.id.template_textView_templateName);
        nameTextView.setText(template.getName());
        convertView.setTag(position);
        deleteButton.setTag(position);

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToEditTemplate((int)v.getTag());
            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = (int)v.getTag();
                TDBH.deleteTemplate(templatesList.get(position));
                updateList();
                notifyDataSetChanged();
            }
        });

        return convertView;
    }

    @Override
    public int getCount() {
        return this.templatesList.size();
    }

    private void goToEditTemplate(int position){
        Intent intent = new Intent(context, TemplateEditActivity.class);
        intent.putExtra("Template", templatesList.get(position));
        int requestCode = 200;

        ((Activity) context).startActivityForResult(intent,requestCode);
    }

    public void updateList(){
        templatesList = TDBH.allTemplates();
    }

}