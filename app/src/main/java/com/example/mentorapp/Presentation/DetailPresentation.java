package com.example.mentorapp.Presentation;

import java.util.ArrayList;

public class DetailPresentation {
    private String official;
    private ArrayList<String> comments;
    private Integer score;
    private Boolean flagged;

    public DetailPresentation(String official, Integer score, ArrayList<String> comments, Boolean flagged){
        this.official = official;
        this.score = score;
        this.comments = comments;
        this.flagged = flagged;
    }

    public ArrayList<String> getComments() {
        return comments;
    }

    public Boolean getFlagged() {
        return flagged;
    }

    public Integer getScore() {
        return score;
    }

    public String getOfficial() {
        return official;
    }

    public void setComments(ArrayList<String> comments) {
        this.comments = comments;
    }

    public void setFlagged(Boolean flagged) {
        this.flagged = flagged;
    }

    public void setOfficial(String official) {
        this.official = official;
    }

    public void setScore(Integer score) {
        this.score = score;
    }
}
