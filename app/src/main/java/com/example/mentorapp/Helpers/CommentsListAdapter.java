package com.example.mentorapp.Helpers;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Button;
import android.app.AlertDialog;
import android.content.DialogInterface;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.mentorapp.R;

import java.util.ArrayList;

public class CommentsListAdapter extends ArrayAdapter<String> {

    private ArrayList<String> comments;
    private Context context;

    private TextView noCommentsView;

    public CommentsListAdapter(Context context, ArrayList<String> comments, TextView noCommentsView){
        super(context,0);
        this.context = context;
        this.comments = comments;
        this.noCommentsView = noCommentsView;
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull final ViewGroup parent) {
        final String commentToShow = comments.get(position);
        // If we need to make a new view, load it
        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.comment_layout, null);
        }

        TextView commentTextView = (TextView) convertView.findViewById(R.id.template_textView_templateName);
        Button deleteButtonView = (Button) convertView.findViewById(R.id.comment_button_deleteComment);

        commentTextView.setText(commentToShow);

        deleteButtonView.setTag(position);

        deleteButtonView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final int position = (Integer) v.getTag();

                System.out.println("Delete Button Pressed");
                System.out.println(position);

                DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which){
                            case DialogInterface.BUTTON_POSITIVE:
                                //User pressed yes

                                // Remove comment from list
                                //comments.remove(commentToShow);
                                comments.remove(position);
                                notifyDataSetChanged();
                                ListView commentsListView = (ListView) parent.findViewById(R.id.list_templates_id);
                                if(comments.size() > 0) {
                                    commentsListView.setVisibility(View.VISIBLE);
                                    noCommentsView.setVisibility(View.INVISIBLE);
                                } else {
                                    commentsListView.setVisibility(View.INVISIBLE);
                                    noCommentsView.setVisibility(View.VISIBLE);
                                }
                                break;

                            case DialogInterface.BUTTON_NEGATIVE:
                                //User pressed no
                                System.out.println("No");
                                break;
                        }
                    }
                };

                AlertDialog.Builder builder = new AlertDialog.Builder(v.getRootView().getContext());
                builder.setMessage("Delete Comment: '"+commentToShow+"'?").setPositiveButton("Yes", dialogClickListener)
                        .setNegativeButton("No", dialogClickListener).show();


            }
        });

        return convertView;
    }

    @Override
    public int getCount() {
        return this.comments.size();
    }
}