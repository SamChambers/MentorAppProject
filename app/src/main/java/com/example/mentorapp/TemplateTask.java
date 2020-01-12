package com.example.mentorapp;

import java.io.Serializable;

public class TemplateTask implements Serializable {
    private String description;
    private Float weight;

    public TemplateTask(){
        this.description = "New Task";
        this.weight = Float.valueOf(1);
    }

    public TemplateTask(String description){
        this.description = description;
        this.weight = Float.valueOf(1);
    }

    public TemplateTask(String description, Float weight){
        this.description = description;
        this.weight = weight;
    }

    public String getDescription() {
        return description;
    }

    public Float getWeight() {
        return weight;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setWeight(Float weight) {
        this.weight = weight;
    }
}
