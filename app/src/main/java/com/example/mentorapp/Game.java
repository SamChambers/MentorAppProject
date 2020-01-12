package com.example.mentorapp;

import java.io.Serializable;
import java.util.ArrayList;

public class Game implements Serializable {

    //Internal variables
    private String identifier; //TODO: Hook this up somehow
    private ArrayList<Evaluation> evaluationsList;

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
    }

    private void updateEvaluationPositions(){
        for (int i=0; i<this.evaluationsList.size(); ++i){
            //this.evaluationsList.get(i).setTitle(String.valueOf(i));
            this.evaluationsList.get(i).setEvaluationPosition(i);
        }
    }

    public void addEvaluation(Evaluation newEval){
        this.evaluationsList.add(newEval);
    }
}
