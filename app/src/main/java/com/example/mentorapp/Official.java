package com.example.mentorapp;

import android.provider.ContactsContract;

import com.google.gson.Gson;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.sql.Date;


public class Official implements Serializable {

    private Integer id;

    private String name;
    private MonthYear dob;
    private MonthYear startedOfficiating;
    //private ContactsContract.Contacts.Photo photo;
    private String email;

    ArrayList<Integer> evaluationsList;

    public Official(){
        this.id = null;
        this.name = "New Official";
        this.dob = new MonthYear();
        this.startedOfficiating = new MonthYear();
        this.email = "";
        this.evaluationsList = new ArrayList<>();
    }

    public Official(String name){
        this.id = null;
        this.name = name;
        this.dob = new MonthYear();
        this.startedOfficiating = new MonthYear();
        this.email = "";
        this.evaluationsList = new ArrayList<>();
    }

    public Official(Integer id, String name, MonthYear dob, MonthYear startedOfficiating, String email, ArrayList<Integer> evaluationsList){
        this.id = id;
        this.name = name;
        this.dob = dob;
        this.startedOfficiating = startedOfficiating;
        this.email = email;
        this.evaluationsList = evaluationsList;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public ArrayList<Integer> getEvaluationsList() {
        return evaluationsList;
    }

    public MonthYear getDob() {
        return dob;
    }

    public MonthYear getStartedOfficiating() {
        return startedOfficiating;
    }

    public String getEmail() {
        return email;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String toJson(){
        Gson gson = new Gson();
        return gson.toJson(this);
    }


}
