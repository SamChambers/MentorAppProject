package com.example.mentorapp;

import java.util.ArrayList;
import java.util.List;

//This class will hold information on a category of tasks
public class Category {

    //Internal Variables
    private List<Task> taskList;
    private String categoryTitle;
    private float calculatedScore;

    //Basic constructor
    public Category(String groupName){
        this.categoryTitle = groupName;
        this.taskList = new ArrayList<>();
        updateScore();
    }

    //Full constructor
    public Category(List<Task> tasks, String groupName){
        this.taskList = tasks;
        this.categoryTitle = groupName;
        updateScore();
    }

    //Update the interval score variable
    public void updateScore(){
        Task task;
        Float totalValue = Float.valueOf(0);
        Integer numberOfTasks = this.taskList.size();

        //Loop through all the tasks
        for(int i = 0; i < numberOfTasks; ++i){
            totalValue += this.taskList.get(i).getScore();
        }
        this.calculatedScore = totalValue/numberOfTasks;
    }

    //Get score
    public Float getScore(){
        return this.calculatedScore;
    }

    //Get title
    public String getTitle(){
        return this.categoryTitle;
    }

    // Get all the tasks
    public List<Task> getAllTasks(){
        return this.taskList;
    }

    // Get a specific task in a position
    public Task getTask(Integer position){
        return this.taskList.get(position);
    }

    // Get the total number of tasks in the category
    public Integer getNumberOfTasks(){
        return this.taskList.size();
    }

    // Set all of the tasks
    public void setTasks(List<Task> newTaskList){
        this.taskList = newTaskList;
    }

    // Set a specific task
    public void setTask(Integer position, Task newTask){
        this.taskList.set(position, newTask);
    }

    // Add a new task to the end of the list
    public void addTask(Task newTask){
        this.taskList.add(newTask);
    }

    // Add a new task to a specific position in the list
    public void addTaskPosition(Task newTask, Integer position){
        this.taskList.add(position, newTask);
    }

    // Delete the task in a specific position
    public void deleteTaskPosition(Integer position){
        this.taskList.remove(position);
    }

    // Set the category name
    public void setCategoryTitle(String categoryName) {
        this.categoryTitle = categoryName;
    }
}
