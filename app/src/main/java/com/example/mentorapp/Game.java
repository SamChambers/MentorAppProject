package com.example.mentorapp;

import android.content.Context;

import com.example.mentorapp.DataBase.DBHelper;
import com.google.gson.Gson;

import java.io.Serializable;
import java.util.ArrayList;

public class Game implements Serializable {

    //Internal variables
    private String identifier;
    private ArrayList<Integer> evaluationsList;

    private Integer id;

    //Basic constructor
    public Game(){
        this.identifier = "Game";
        this.evaluationsList = new ArrayList<Integer>();
    }

    //Full constructor
    public Game(String id, ArrayList<Integer> evaluationsList){
        this.identifier = id;
        this.evaluationsList = evaluationsList;
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
}
