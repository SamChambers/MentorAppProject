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

import com.example.mentorapp.Activities.GameViewActivity;
import com.example.mentorapp.DataBase.DBHelper;
import com.example.mentorapp.Game;
import com.example.mentorapp.R;

import java.util.List;

public class GamesListAdapter extends ArrayAdapter<Game> {

    private List<Game> gamesList;
    private Context context;
    private DBHelper dbh;


    public GamesListAdapter(Context context, DBHelper tdbh){
        super(context,0);
        this.context = context;
        this.dbh = tdbh;
        updateList();
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull final ViewGroup parent) {

        Game game = this.gamesList.get(position);

        // If we need to make a new view, load it
        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.game_list_item_layout, null);
        }

        TextView nameTextView = (TextView) convertView.findViewById(R.id.text_game_name_id);
        nameTextView.setText(game.getIdentifier());

        ImageButton deleteButtonView = (ImageButton) convertView.findViewById(R.id.game_list_button_delete_id);
        deleteButtonView.setTag(position);
        deleteButtonView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = (int)v.getTag();
                dbh.deleteGame(gamesList.get(position));
                updateList();
                notifyDataSetChanged();
            }
        });

        convertView.setTag(position);

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToViewGame((int)v.getTag());
            }
        });


        return convertView;
    }

    @Override
    public int getCount() {
        return this.gamesList.size();
    }


    public void updateList(){
        this.gamesList = dbh.allGames();
    }

    private void goToViewGame(int position){
        Intent intent = new Intent(context, GameViewActivity.class);
        intent.putExtra("Game", this.gamesList.get(position));
        ((Activity) this.context).startActivityForResult(intent,1000);
    }


}