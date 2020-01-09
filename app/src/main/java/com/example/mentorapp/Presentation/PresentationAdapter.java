package com.example.mentorapp.Presentation;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.ListView;

import com.example.mentorapp.R;

public class PresentationAdapter extends BaseAdapter {

    PresentationStorage presentation;
    Context context;


    public PresentationAdapter(Context context, PresentationStorage presentation) {
        this.presentation = presentation;
        this.context = context;
    }

    @Override
    public int getCount() {
        return presentation.getCategoriesCount() + presentation.getTaskCount();
    }

    @Override
    public Object getItem(int position) {
        //Figure out if it is category or task
        int currentPosition = 0;
        boolean isCategory = false;
        for (CategoryPresentation category:this.presentation.categories) {
            if (currentPosition == position){
                return category;
            } else {
                if(position > currentPosition+category.getTaskPresentations().size()){
                    currentPosition+=category.getTaskPresentations().size()+1;
                    continue;
                }
                return category.getTaskPresentations().get(position-(currentPosition+1));
            }
        }

        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // inflate the layout for each list row

        boolean isCategory;
        if(this.getItem(position) instanceof CategoryPresentation){
            isCategory=true;
        } else {
            isCategory=false;
        }



        if (convertView == null) {
            if(isCategory) {
                convertView = LayoutInflater.from(context).inflate(R.layout.present_category_layout, parent, false);
            } else {
                convertView = LayoutInflater.from(context).inflate(R.layout.present_detail_layout, parent, false);
            }
        }

        // get current item to be displayed
        if(isCategory) {
            TextView textView = convertView.findViewById(R.id.text_present_category_name_id);
            CategoryPresentation currentItem = (CategoryPresentation) getItem(position);


            System.out.println(textView);

            textView.setText(currentItem.getCategory());
        } else {
            TextView textView = convertView.findViewById(R.id.text_present_detail_id);
            TaskPresentation currentItem = (TaskPresentation) getItem(position);
            textView.setText(currentItem.getDescription());
            LinearLayout list = convertView.findViewById(R.id.linear_layout_stats_id);

            textView.setText("Task: " + currentItem.getDescription());

            LayoutInflater inflater = LayoutInflater.from(context);

            for(int i=0;i<currentItem.getStats().size();++i){
                View child = inflater.inflate(R.layout.present_stats_layout,parent);
                TextView official= child.findViewById(R.id.text_present_detail_id);
                official.setText(currentItem.getStats().get(i).getOfficial());
                list.addView(child);
            }




        }



        // returns the view for the current row
        return convertView;
    }
}
