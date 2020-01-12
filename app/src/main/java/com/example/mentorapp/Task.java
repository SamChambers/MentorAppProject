package com.example.mentorapp;

import java.io.Serializable;
import java.util.List;
import java.util.ArrayList;


// This class will hold the most basic information for the tasks
public class Task implements Serializable {

    //Internal Variables
    private String description;
    private Integer score;
    private List<String> comments;
    private Boolean flagged;
    private Float weight;

    //Basic constructor
    public Task(){
        this.description = "No Description";
        this.score = 0;
        this.comments = new ArrayList<>();
        this.flagged = false;
        this.weight = Float.valueOf(1);
    }

    //Constructor with just the description
    public Task(String description){
        this.description = description;
        this.score = 0;
        this.comments = new ArrayList<>();
        this.flagged = false;
        this.weight = Float.valueOf(1);
    }

    public Task(String description, Float weight){
        this.description = description;
        this.score = 0;
        this.comments = new ArrayList<>();
        this.flagged = false;
        this.weight = weight;
    }

    //Full constructor
    public Task(String description, Integer score, List<String> comments, Boolean flagged, Float weight){
        this.description = description;
        this.score = score;
        this.comments = comments;
        this.flagged = flagged;
        this.weight = weight;
    }

    // Set score
    public void setScore(Integer newScore){
        this.score = newScore;
    }

    // Set description
    public void setDescription(String newDescription){
        this.description = newDescription;
    }

    // Set comments
    public void setComments(List<String> comments){
        this.comments = comments;
    }

    // Set flagged
    public void setFlagged(Boolean flagged){
        this.flagged = flagged;
    }

    // Get score
    public Integer getScore(){
        return this.score;
    }

    // Get description
    public String getDescription(){
        return this.description;
    }

    //Get Comments
    public List<String> getComments(){
        return this.comments;
    }

    //Get flagged
    public Boolean getFlagged(){
        return this.flagged;
    }

    public void addComment(String comment){
        this.comments.add(comment);
    }

    public void switchFlagged(){
        if(this.flagged){
            this.flagged = Boolean.FALSE;
        } else {
            this.flagged = Boolean.TRUE;
        }
    }

    public void increaseScore(Integer amount){
        this.score += amount;
    }

    public Integer numberOfComments(){
        return this.comments.size();
    }

    public void removeComment(Integer position){
        this.comments.remove(position);
    }

    public Float getAggregateScore(){
        return this.score*this.weight;
    }
}
