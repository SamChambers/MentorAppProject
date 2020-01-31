package com.example.mentorapp;

import android.content.Context;
import android.util.Pair;

import com.example.mentorapp.DataBase.DBHelper;
import com.example.mentorapp.Tags.TagOptions;
import com.google.gson.Gson;

import java.io.Serializable;
import java.util.ArrayList;

public class Game implements Serializable {

    //Internal variables
    private String identifier;
    //TODO: Make this into a date picker
    private String date;
    private ArrayList<TagOptions> tags;
    private ArrayList<Integer> evaluationsList;

    private Integer id;

    //Basic constructor
    public Game(){
        this.identifier = "Game";
        this.date = "";
        this.evaluationsList = new ArrayList<Integer>();
        this.tags = new ArrayList<>();
    }

    //Full constructor
    public Game(String id, ArrayList<Integer> evaluationsList){
        this.identifier = id;
        this.date = "";
        this.evaluationsList = evaluationsList;
        this.tags = new ArrayList<>();
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public ArrayList<Integer> getEvaluationsList() {
        return evaluationsList;
    }

    public void setEvaluationsList(ArrayList<Integer> evaluationsList) {
        this.evaluationsList = evaluationsList;
    }

    public Evaluation getEvaluationFromPosition(Integer position, Context context){
        DBHelper dbh = new DBHelper(context);
        return dbh.getEvaluation(this.evaluationsList.get(position));
    }

    public Integer getEvaluationCount(){
        return evaluationsList.size();
    }

    public void removeEvaluation(int position){
        this.evaluationsList.remove(position);
        //this.updateEvaluationPositions();
    }

    public void addEvaluation(Evaluation newEval){
        this.evaluationsList.add(newEval.getId());
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String toJson(){
        Gson gson = new Gson();
        return gson.toJson(this);
    }

    public ArrayList<TagOptions> getTags() {
        return tags;
    }

    public void setTags(ArrayList<TagOptions> tags) {
        this.tags = tags;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDate() {
        return date;
    }
}
