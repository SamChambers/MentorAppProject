package com.example.mentorapp;

import com.google.gson.Gson;

import java.io.Serializable;
import java.util.ArrayList;

public class Game implements Serializable {

    //Internal variables
    private String identifier;
    private ArrayList<Evaluation> evaluationsList;

    private Integer id;

    //Basic constructor
    public Game(){
        this.identifier = "";
        this.evaluationsList = new ArrayList<Evaluation>();
    }

    //Full constructor
    public Game(String id, ArrayList<Evaluation> evaluationsList){
        this.identifier = id;
        this.evaluationsList = evaluationsList;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public ArrayList<Evaluation> getEvaluationsList() {
        return evaluationsList;
    }

    public void setEvaluationsList(ArrayList<Evaluation> evaluationsList) {
        this.evaluationsList = evaluationsList;
    }

    public Evaluation getEvaluationFromPosition(Integer position){
        return evaluationsList.get(position);
    }

    public Integer getEvaluationCount(){
        return evaluationsList.size();
    }

    public void removeEvaluation(int position){
        this.evaluationsList.remove(position);
        //this.updateEvaluationPositions();
    }

    public void updateEvaluationPositions(){
        for (int i=0; i<this.evaluationsList.size(); ++i){
            //this.evaluationsList.get(i).setTitle(String.valueOf(i));
            this.evaluationsList.get(i).setEvaluationPosition(i);
        }
    }

    public void addEvaluation(Evaluation newEval){
        this.evaluationsList.add(newEval);
        //this.updateEvaluationPositions();
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
