package com.example.mentorapp.Presentation;

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

import com.example.mentorapp.Evaluation;
import com.example.mentorapp.Presentation.PresentationAdapter;
import com.example.mentorapp.Presentation.PresentationStorage;
import com.example.mentorapp.R;
import com.example.mentorapp.Task;

import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.view.ViewGroup.LayoutParams;

public class PresentationFragment extends Fragment {

    private LinearLayout allLinearView;
    private LinearLayout flaggedLinearView;
    private PresentationAdapter listAdapter;

    private PresentationStorage data;


    //Constructor with the data being passed in
    public PresentationFragment(PresentationStorage presentationStorage){
        this.data = presentationStorage;

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //Inflate the view
        View convertView = inflater.inflate(R.layout.present_game_layout,container,false);

        //Set up the view and adapter
        this.allLinearView = (LinearLayout) convertView.findViewById(R.id.linear_layout_all_layout_id);
        this.flaggedLinearView = (LinearLayout) convertView.findViewById(R.id.linear_layout_flagged_layout_id);
        this.listAdapter = new PresentationAdapter(getContext(), data);

        fillLinearView(data, this.allLinearView);
        PresentationStorage flaggedPresentation = data.getFlaggedPresentationStorage();
        fillLinearView(data.getFlaggedPresentationStorage(), this.flaggedLinearView);



        TextView flaggedHeaderTextView = (TextView) convertView.findViewById(R.id.text_present_flagged_header_id);

        if(flaggedPresentation.getCategories().size() == 0){
            flaggedHeaderTextView.setVisibility(View.INVISIBLE);
            LayoutParams params = (LayoutParams) flaggedHeaderTextView.getLayoutParams();
            params.height = 0;
            flaggedHeaderTextView.setLayoutParams(params);
        } else {
            flaggedHeaderTextView.setVisibility(View.VISIBLE);
        }

        return convertView;
    }

    public PresentationAdapter getAdapter(){
        return this.listAdapter;
    }

    public String getDataTitle(){
        return data.getTitle();
    }

    public PresentationAdapter getListAdapter() {
        return this.listAdapter;
    }

    private void fillLinearView(PresentationStorage presentation, LinearLayout linearLayout){

        LayoutInflater inflater = LayoutInflater.from(getActivity().getApplicationContext());
        ViewGroup vg = (ViewGroup)linearLayout;

        for (CategoryPresentation currentCategory:presentation.getCategories()){
            // Add the category sections
            View categoryView = getLayoutInflater().inflate(R.layout.present_category_layout,vg,false);
            TextView categoryNameView = (TextView)categoryView.findViewById(R.id.text_present_category_name_id);
            categoryNameView.setText(currentCategory.getCategory());
            linearLayout.addView(categoryView);

            // Add the task sections
            for(TaskPresentation currentTask:currentCategory.getTaskPresentations()){
                View taskView = getLayoutInflater().inflate(R.layout.present_detail_layout,vg,false);
                TextView taskNameView = (TextView)taskView.findViewById(R.id.text_present_detail_id);
                LinearLayout statsLinearLayoutView = (LinearLayout)taskView.findViewById(R.id.linear_layout_stats_id);
                taskNameView.setText(currentTask.getDescription());
                linearLayout.addView(taskView);

                //Add the officials stats
                for (DetailPresentation currentDetail:currentTask.getStats()){
                    View detailView = getLayoutInflater().inflate(R.layout.present_stats_layout,vg,false);
                    TextView officialNameView = (TextView)detailView.findViewById(R.id.text_present_name_id);
                    TextView officialScoreView = (TextView)detailView.findViewById(R.id.text_present_score_id);
                    LinearLayout commentsLinearLayoutView = (LinearLayout)detailView.findViewById(R.id.linear_layout_comments_id);
                    officialNameView.setText(currentDetail.getOfficial());
                    officialScoreView.setText("(" + currentDetail.getScore()+")");
                    if(currentDetail.getFlagged()){
                        detailView.setBackgroundColor(Color.GRAY);
                    }
                    statsLinearLayoutView.addView(detailView);

                    //Add the comments
                    for(String currentComment:currentDetail.getComments()){
                        View commentView = getLayoutInflater().inflate(R.layout.present_comment_layout,vg,false);
                        TextView commentTextView = (TextView)commentView.findViewById(R.id.text_present_comment_id);
                        commentTextView.setText(currentComment);
                        commentsLinearLayoutView.addView(commentView);
                    }
                }
            }
        }

    }
}
