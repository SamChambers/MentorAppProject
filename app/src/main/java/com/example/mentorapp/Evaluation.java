package com.example.mentorapp;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Evaluation implements Serializable {

    // Internal Variables
    private String official; // This should eventually be changed potentially???
    private List<Category> data;
    private List<String> categories;
    private Float calculatedScore;

    private int evaluationPosition;

    //Basic constructor
    public Evaluation(int evaluationPosition){
        this.data = new ArrayList<Category>();
        this.official = "Unknown Official";
        this.evaluationPosition = evaluationPosition;
        this.calculatedScore = Float.valueOf(0);
        this.categories = new ArrayList<String>();
    }

    //Full constructor
    public Evaluation(List<Category> data, String official, int evaluationPosition){
        this.data = data;
        this.official = official;
        this.evaluationPosition = evaluationPosition;
        this.calculateScore();
        this.updateCategories();
    }

    // Calculate the score for the game
    public void calculateScore(){
        Float totalValue = Float.valueOf(0);
        Integer numberOfCategories = this.data.size();

        //Loop through all the categories
        for(int i = 0; i < numberOfCategories; ++i){
            totalValue += this.data.get(i).getScore();
        }
        this.calculatedScore = totalValue/numberOfCategories;
    }

    // Get score
    public Float getScore(){
        return this.calculatedScore;
    }

    //Update the internal categories variable
    public void updateCategories(){
        Category category;
        List<String> categoryList = new ArrayList<String>();
        Integer numberOfCategories = this.data.size();

        //Loop through all the categories
        for(int i = 0; i < numberOfCategories; ++i){
            categoryList.add(data.get(i).getTitle());
        }
        this.categories = categoryList;
    }

    // Get categories
    public List<String> getCategoriesList(){
        return this.categories;
    }

    public List<Category> getCategories(){
        return this.data;
    }

    // Get the category from the position
    public Category getCategoryFromPosition(Integer position){
        return this.data.get(position);
    }

    //Get the category from the title
    public Category getCategoryFromTitle(String categoryTitle){
        Integer index = this.categories.indexOf(categoryTitle);
        return data.get(index);
    }

    //Get the tasks from the category
    public Task getTaskFromCategory(String category, Integer position){
        return getCategoryFromTitle(category).getTask(position);
    }

    //Get the tasks from the category
    public Task getTaskFromCategory(Integer category, Integer position){
        return getCategoryFromPosition(category).getTask(position);
    }

    // Add a new category
    public void addCategory(String category){
        if (this.categories.contains(category)){
            //The category already
            return;
        }

        Category newCategory = new Category(category);

        this.data.add(newCategory);
        updateCategories();
        return;
    }

    // Add a new task into the category
    public void addTask(String category, Task task){
        getCategoryFromTitle(category).addTask(task);
        return;
    }

    // Set the task in the category
    public void setTask(String category, Integer position, Task task){
        getCategoryFromTitle(category).setTask(position, task);
    }

    // Set the task in the category
    public void setTask(Integer categoryPosition, Integer position, Task task){
        getCategoryFromPosition(categoryPosition).setTask(position, task);
    }

    public void setOfficial(String official){
        this.official = official;
    }

    public String getOfficial(){
        return this.official;
    }

    public void setEvaluationPosition(int position){
        this.evaluationPosition=position;
    }

    public int getEvaluationPosition() {
        return evaluationPosition;
    }
}
