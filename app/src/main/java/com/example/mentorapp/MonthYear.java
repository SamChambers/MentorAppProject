package com.example.mentorapp;

import java.io.Serializable;

public class MonthYear implements Serializable {
    private Integer month;
    private Integer year;

    MonthYear(){
        this.month = 1;
        this.year = 1990;
    }

    MonthYear(Integer month, Integer year){
        this.month = month;
        this.year = year;
    }

    public void setMonth(Integer month) {
        this.month = month;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Integer getMonth() {
        return month;
    }

    public Integer getYear() {
        return year;
    }
}
