package com.example.mentorapp;

import com.google.gson.Gson;

import java.io.Serializable;
import java.util.ArrayList;

public class Template implements Serializable {

    // A template will be a list of categories (Strings), that hold
    // tasks (Strings), that will have weights

    //DB Variables
    private Integer id;

    private ArrayList<TemplateCategory> categories;
    private String name;

    public Template(String name){
        this.categories = new ArrayList<>();
        this.name = name;
    }

    public Template(String name, ArrayList<TemplateCategory> categories){
        this.categories = categories;
        this.name = name;
    }

    public ArrayList<TemplateCategory> getCategories() {
        return categories;
    }

    public void setCategories(ArrayList<TemplateCategory> categories) {
        this.categories = categories;
    }

    public TemplateCategory getCategory(int position){
        return this.categories.get(position);
    }

    public void addCategory(String name, Float weight){
        this.categories.add(new TemplateCategory(name, weight));
    }

    public void addTask(int categoryPosition, String description){
        this.categories.get(categoryPosition).addTask(description);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String[] getCategoryNames(){
        String[] names = new String[this.getCategories().size()];

        for(int i=0; i<this.categories.size(); ++i){
            names[i] = this.categories.get(i).getName();
        }

        return names;
    }

    public TemplateCategory getCategory(String category){
        for(TemplateCategory tc : this.categories){
            if(tc.getName() == category){
                return tc;
            }
        }
        return null;
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
}

