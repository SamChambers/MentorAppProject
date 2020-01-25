package com.example.mentorapp;

import android.content.Context;
import android.util.Pair;

import com.example.mentorapp.DataBase.DBHelper;
import com.example.mentorapp.Official.Official;
import com.google.gson.Gson;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Evaluation implements Serializable {

    // DB Variables
    private Integer id;
    private Integer OfficialId;
    private String templateName;
    private Date creationDate;

    // Internal Variables
    private List<Category> data;
    private List<String> categories;
    private Float calculatedScore;

    private int evaluationPosition;

    //Basic constructor
    public Evaluation(int evaluationPosition){
        this.data = new ArrayList<Category>();
        this.evaluationPosition = evaluationPosition;
        this.calculatedScore = Float.valueOf(0);
        this.categories = new ArrayList<String>();
        setCreationDate(new Date());
    }

    //Full constructor
    public Evaluation(List<Category> data, int evaluationPosition){
        this.data = data;
        this.evaluationPosition = evaluationPosition;
        this.calculateScore();
        this.updateCategories();
        setCreationDate(new Date());
    }

    public Evaluation(Template template, int evaluationPosition){
        this.data = new ArrayList<>();
        for(TemplateCategory tc:template.getCategories()){
            Category cat = new Category(tc.getName());
            for(TemplateTask details:tc.getTasks()){
                Task task = new Task(details.getDescription(),details.getWeight());
                cat.addTask(task);
            }
            this.data.add(cat);

        }
        this.templateName = template.getName();
        this.evaluationPosition = evaluationPosition;
        this.calculateScore();
        this.updateCategories();
        setCreationDate(new Date());
    }

    // Calculate the score for the game
    public void calculateScore(){
        Float totalValue = Float.valueOf(0);
        Integer numberOfCategories = this.data.size();
        System.out.println("Number of Categories");
        System.out.println(numberOfCategories);
        if (numberOfCategories == 0){
            this.calculatedScore = totalValue;
            return;
        }

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


    public String getOfficialName(Context context){
        String officialName;
        System.out.println("Official Name");
        System.out.println(this.OfficialId);
        if(this.OfficialId != null) {
            DBHelper DBH = new DBHelper(context);
            Official official = DBH.getOfficial(this.OfficialId);
            officialName = official.getName();
        } else {
            officialName = "Unknown";
        }
        return officialName;
    }

    public void setEvaluationPosition(int position){
        this.evaluationPosition=position;
    }

    public int getEvaluationPosition() {
        return evaluationPosition;
    }

    public Integer getId() {
        return id;
    }

    public Integer getOfficialId() {
        return OfficialId;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setOfficialId(Integer officialId) {
        OfficialId = officialId;
    }

    public String toJson(){
        // DB Variables
        System.out.println(id);
        System.out.println(OfficialId);
        System.out.println(templateName);
        System.out.println(creationDate);

        // Internal Variables
        System.out.println(data);
        System.out.println(categories);
        System.out.println(calculatedScore);

        System.out.println(evaluationPosition);
        Gson gson = new Gson();
        return gson.toJson(this);
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public void setTemplateName(String templateName) {
        this.templateName = templateName;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public String getTemplateName() {
        return templateName;
    }
}
