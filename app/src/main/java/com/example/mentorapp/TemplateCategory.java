package com.example.mentorapp;


import java.io.Serializable;
import java.util.ArrayList;

public class TemplateCategory implements Serializable {

    private ArrayList<TemplateTask> tasks;
    private String name;
    private Float weight;

    public TemplateCategory(String name, Float weight){
        this.tasks = new ArrayList<>();
        this.name = name;
        this.weight = weight;
    }

    public TemplateCategory(String name, ArrayList<TemplateTask> tasks, Float weight){
        this.tasks = tasks;
        this.name = name;
        this.weight = weight;
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

    public Float getWeight() {
        return weight;
    }

    public void setWeight(Float weight) {
        this.weight = weight;
    }
}
