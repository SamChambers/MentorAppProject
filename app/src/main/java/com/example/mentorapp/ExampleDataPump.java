package com.example.mentorapp;

import android.content.Context;

public class ExampleDataPump {
    public static Evaluation getEvaluation(int position) {
        Evaluation data = new Evaluation(position);

        data.addCategory("House");
        //data.addTask("House", new Task("Clean Room"));
        //data.addTask("House", new Task("Make Bed"));

        data.addCategory("Errands");
        data.addTask("Errands", new Task("Pick up dry cleaning, and do something else because we need a really long description for testing!"));
        data.addTask("Errands", new Task("Get milk"));
        data.getTaskFromCategory("Errands",1).addComment("Example Comment!");

        data.addCategory("Errands2");
        data.addTask("Errands2", new Task("Pick up dry cleaning, and do something else because we need a really long description for testing!"));
        data.addTask("Errands2", new Task("Get milk"));
        data.getTaskFromCategory("Errands2",1).addComment("Example Comment!");

        data.addCategory("Other");
        data.addTask("Other", new Task("Cut the grass"));
        data.addTask("Other", new Task("Get gas from the gas station"));

        return data;
    }

    public static Template getTemplate(){
        Template template = new Template("DataPump Template");

        template.addCategory("Positioning");
        template.getCategory(0).addTask("Dynamic Plays");
        template.getCategory(0).addTask("Set Pieces");

        template.addCategory("Game management");
        template.getCategory(1).addTask("First 18 min");
        template.getCategory(1).addTask("Last 2 min");

        template.addCategory("Conditioning");
        template.getCategory(2).addTask("Sprints");
        template.getCategory(2).addTask("Endurance");

        return template;
    }
}