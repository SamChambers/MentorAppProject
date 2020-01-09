package com.example.mentorapp.Presentation;

import java.util.ArrayList;

public class TaskPresentation {
    String description;
    ArrayList<DetailPresentation> stats;

    public TaskPresentation(String description, ArrayList<DetailPresentation> stats){
        this.description = description;
        this.stats = stats;
    }

    public ArrayList<DetailPresentation> getStats() {
        return stats;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setStats(ArrayList<DetailPresentation> stats) {
        this.stats = stats;
    }
}
