package com.example.mentorapp.Presentation;


import java.util.ArrayList;

public class CategoryPresentation {
    String category;
    ArrayList<TaskPresentation> taskPresentations;

    public CategoryPresentation(String name, ArrayList<TaskPresentation> tasks){
        this.category = name;
        this.taskPresentations = tasks;
    }

    public int getTaskPosition(String description){
        for(int i=0; i< taskPresentations.size(); ++i){
            if (taskPresentations.get(i).description == description){
                return i;
            }
        }
        return -1;
    }

    public ArrayList<TaskPresentation> getTaskPresentations() {
        return taskPresentations;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setTaskPresentations(ArrayList<TaskPresentation> taskPresentations) {
        this.taskPresentations = taskPresentations;
    }
}
