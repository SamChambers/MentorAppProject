package com.example.mentorapp.Tags;

import com.google.gson.Gson;

import java.util.ArrayList;

public class Tag {

    private Integer id;
    private String name;
    private ArrayList<String> optionsList;
    private Boolean mandatory;

    public Tag(String name){
        this.id = null;
        this.name = name;
        this.optionsList = new ArrayList<>();
        this.mandatory = Boolean.FALSE;
    }

    public void addOption(String option){
        this.optionsList.add(option);
    }

    public void deleteOption(String option){
        this.optionsList.remove(option);
    }

    public ArrayList<String> getOptionsList(){
        return this.optionsList;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setOptionsList(ArrayList<String> optionsList) {
        this.optionsList = optionsList;
    }

    public String toJson(){
        Gson gson = new Gson();
        return gson.toJson(this);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void makeManditory(){
        this.mandatory = Boolean.TRUE;
    }

    public void notManditory(){
        this.mandatory = Boolean.FALSE;
    }

    public Boolean getMandatory() {
        return mandatory;
    }
}
