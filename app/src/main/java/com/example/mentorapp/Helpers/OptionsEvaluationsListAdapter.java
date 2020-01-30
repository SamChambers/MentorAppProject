package com.example.mentorapp.Helpers;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.mentorapp.DataBase.DBHelper;
import com.example.mentorapp.Evaluation;
import com.example.mentorapp.Official.Official;
import com.example.mentorapp.R;

import java.util.ArrayList;
import java.util.List;

public class OptionsEvaluationsListAdapter extends ArrayAdapter<Evaluation> {

    private List<Evaluation> evaluationList;
    private Context context;
    private DBHelper TDBH;


    public OptionsEvaluationsListAdapter(Context context, DBHelper tdbh, ArrayList<Evaluation> evaluationList){
        super(context,0);
        this.context = context;
        this.TDBH = tdbh;
        this.evaluationList = evaluationList;
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull final ViewGroup parent) {

        Evaluation evaluation = this.evaluationList.get(position);

        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.evaluation_options_layout, null);
        }

        Button deleteButton = (Button) convertView.findViewById(R.id.options_button_delete_evaluation_id);
        TextView officialNameTextView = (TextView) convertView.findViewById(R.id.options_textView_officialName_id);
        TextView creationDateTextView = (TextView) convertView.findViewById(R.id.options_textView_creationDate_id);
        TextView templateNameTextView = (TextView) convertView.findViewById(R.id.options_textView_evaluationName_id);

        List<Official> officialList = this.TDBH.allOfficials();
        for (Official official:officialList){
            System.out.println(official.getId());
        }

        officialNameTextView.setText(evaluation.getOfficialName(this.context));
        creationDateTextView.setText(evaluation.getCreationDate().toString());
        templateNameTextView.setText(evaluation.getTemplateName());

        deleteButton.setTag(position);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = (int)v.getTag();
                evaluationList.remove(position);
                notifyDataSetChanged();
            }
        });

        return convertView;
    }

    @Override
    public int getCount() {
        return this.evaluationList.size();
    }

}