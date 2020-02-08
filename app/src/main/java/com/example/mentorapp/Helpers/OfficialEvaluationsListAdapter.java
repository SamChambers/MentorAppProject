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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.mentorapp.Activities.GamePresentActivity;
import com.example.mentorapp.Activities.GameViewActivity;
import com.example.mentorapp.DataBase.DBHelper;
import com.example.mentorapp.Evaluation;
import com.example.mentorapp.Game;
import com.example.mentorapp.Official.Official;
import com.example.mentorapp.R;

import java.util.ArrayList;
import java.util.List;

public class OfficialEvaluationsListAdapter extends ArrayAdapter<Game> {

    private List<Evaluation> evaluationList;
    private Context context;
    private DBHelper dbh;
    private Official official;


    public OfficialEvaluationsListAdapter(Context context, Official official){
        super(context,0);
        this.context = context;
        this.dbh = new DBHelper(context);
        this.official = official;
        this.updateEvaluationList();
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull final ViewGroup parent) {

        final Evaluation evaluation = this.evaluationList.get(position);

        // If we need to make a new view, load it
        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.official_evaluation_list_item, null);
        }

        TextView nameTextView = convertView.findViewById(R.id.official_evaluation_list_text_evaluation_id);
        nameTextView.setText(this.dbh.getGame(evaluation.getGameId()).getIdentifier());

        TextView dateTextView = convertView.findViewById(R.id.official_evaluation_list_date_id);
        dateTextView.setText(evaluation.getCreationDate().toString());

        TextView scoreTextView = convertView.findViewById(R.id.official_evaluation_list_text_score_id);
        scoreTextView.setText(evaluation.getScore().toString());


        convertView.setTag(position);

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToPresent(evaluation);
            }
        });
        return convertView;
    }

    @Override
    public int getCount() {
        return this.evaluationList.size();
    }


    private void goToPresent(Evaluation evaluation){
        ArrayList<Evaluation> evaluationArrayList = new ArrayList<>();
        evaluationArrayList.add(evaluation);
        Intent intent = new Intent(context, GamePresentActivity.class);
        intent.putExtra("MyEvaluations", evaluationArrayList);
        intent.putExtra("MyGameName", this.dbh.getGame(evaluation.getGameId()).getIdentifier());
        ((Activity)this.context).startActivity(intent);
    }

    public void updateEvaluationList(){
        ArrayList<Integer> evaluationNumbers = this.official.getEvaluationsList();
        ArrayList<Evaluation> evaluationList = new ArrayList<>();
        for (Integer evaluationNumber : evaluationNumbers){
            evaluationList.add(dbh.getEvaluation(evaluationNumber));
        }
        this.evaluationList = evaluationList;
        notifyDataSetChanged();
    }


}