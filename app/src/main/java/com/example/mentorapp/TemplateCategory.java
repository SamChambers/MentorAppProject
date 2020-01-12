package com.example.mentorapp;


import java.io.Serializable;
import java.util.ArrayList;

public class TemplateCategory implements Serializable {

    private ArrayList<TemplateTask> tasks;
    private String name;

    public TemplateCategory(String name){
        this.tasks = new ArrayList<>();
        this.name = name;
    }

    public TemplateCategory(String name, ArrayList<TemplateTask> tasks){
        this.tasks = tasks;
        this.name = name;
    }

    public void addTask(String description, Float weight){
        this.tasks.add(new TemplateTask(description,weight));
    }

    public void addTask(String description){
        this.tasks.add(new TemplateTask(description,Float.valueOf(0)));
    }

    public ArrayList<TemplateTask> getTasks() {
        return tasks;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setTasks(ArrayList<TemplateTask> tasks) {
        this.tasks = tasks;
    }
}
