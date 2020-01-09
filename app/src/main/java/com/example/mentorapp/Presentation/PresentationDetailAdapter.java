package com.example.mentorapp.Presentation;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.ListAdapter;

import com.example.mentorapp.R;

import java.util.ArrayList;
import java.util.List;

public class PresentationDetailAdapter extends BaseAdapter {
    private ArrayList<DetailPresentation> details;
    private Context context;


    public PresentationDetailAdapter(Context context, ArrayList<DetailPresentation> data) {
        this.details = data;
        this.context = context;
    }

    @Override
    public int getCount() {
        return details.size();
    }

    @Override
    public Object getItem(int position) {
        return this.details.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // inflate the layout for each list row

        System.out.println("Position");
        System.out.println(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.present_stats_layout, parent, false);
        }

        TextView nameView = convertView.findViewById(R.id.text_present_name_id);
        TextView scoreView = convertView.findViewById(R.id.text_present_score_id);
        //ListView comments = convertView.findViewById(R.id.list_present_comments_id);
        DetailPresentation currentItem = (DetailPresentation) this.getItem(position);

        nameView.setText(currentItem.getOfficial());
        scoreView.setText("("+currentItem.getScore()+")");
        /*
        comments.setAdapter(new ArrayAdapter<>(this.context,R.layout.present_comment_layout,currentItem.getComments()));

        ListAdapter adapter = comments.getAdapter();

        int totalHeight = 0;
        for (int i = 0; i < adapter.getCount(); i++) {
            View listItem = adapter.getView(i, null,parent);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams par = comments.getLayoutParams();
        par.height = totalHeight + (comments.getDividerHeight() * (adapter.getCount() - 1));
        comments.setLayoutParams(par);
        comments.requestLayout();
        */
        // returns the view for the current row
        return convertView;
    }
}
