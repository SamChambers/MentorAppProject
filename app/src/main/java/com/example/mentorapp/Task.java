package com.example.mentorapp;

import java.util.List;
import java.util.ArrayList;


// This class will hold the most basic information for the tasks
public class Task {

    //Internal Variables
    private String description;
    private Integer score;
    private List<String> comments;
    private Boolean flagged;

    //Basic constructor
    public Task(){
        this.description = "No Description";
        this.score = 0;
        this.comments = new ArrayList<>();
        this.flagged = false;
    }

    //Constructor with just the description
    public Task(String description){
        this.description = description;
        this.score = 0;
        this.comments = new ArrayList<>();
        this.flagged = false;
    }

    //Full constructor
    public Task(String description, Integer score, List<String> comments, Boolean flagged){
        this.description = description;
        this.score = score;
        this.comments = comments;
        this.flagged = flagged;
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
}
