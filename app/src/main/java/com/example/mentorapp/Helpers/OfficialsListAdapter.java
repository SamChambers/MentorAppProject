package com.example.mentorapp.Helpers;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.mentorapp.Activities.EditOfficialActivity;
import com.example.mentorapp.Activities.EditTemplateActivity;
import com.example.mentorapp.Activities.ViewOfficialActivity;
import com.example.mentorapp.Official.Official;
import com.example.mentorapp.R;
import com.example.mentorapp.Template;
import com.example.mentorapp.DataBase.OfficialDBHelper;

import java.util.List;

public class OfficialsListAdapter extends ArrayAdapter<Template> {

    private List<Official> officialsList;
    private Context context;
    private OfficialDBHelper TDBH;


    public OfficialsListAdapter(Context context, OfficialDBHelper tdbh){
        super(context,0);
        this.context = context;
        this.TDBH = tdbh;
        updateList();
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull final ViewGroup parent) {

        System.out.println(position);
        // Get the template we are showing
        Official official = this.officialsList.get(position);

        // If we need to make a new view, load it
        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.official_layout, null);
        }

        TextView nameTextView = (TextView) convertView.findViewById(R.id.text_official_name);
        nameTextView.setText(official.getName());
        convertView.setTag(position);

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToViewOfficial((int)v.getTag());
                //goToEditOfficial((int)v.getTag());
            }
        });


        return convertView;
    }

    @Override
    public int getCount() {
        System.out.println("Get Count");
        System.out.println(this.officialsList.size());
        return this.officialsList.size();
    }


    public void updateList(){
        this.officialsList = TDBH.allOfficials();
    }

    private void goToViewOfficial(int position){
        Intent intent = new Intent(context, ViewOfficialActivity.class);
        intent.putExtra("Official", this.officialsList.get(position));
        ((Activity) this.context).startActivityForResult(intent, 400);
    }


}