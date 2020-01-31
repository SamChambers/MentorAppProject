package com.example.mentorapp.Tags;

import java.io.Serializable;

public class TagOptions implements Serializable {

    private String tag;
    private String option;

    public TagOptions(String tag, String option){
        this.tag = tag;
        this.option = option;
    }

    public void setOption(String option) {
        this.option = option;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getOption() {
        return option;
    }

    public String getTag() {
        return tag;
    }
}
