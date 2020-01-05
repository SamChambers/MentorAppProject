package com.example.mentorapp;

public class ExampleDataPump {
    public static Evaluation getData(int position) {
        Evaluation data = new Evaluation(position);

        data.addCategory("House");
        data.addTask("House", new Task("Clean Room"));
        data.addTask("House", new Task("Make Bed"));

        data.addCategory("Errands");
        data.addTask("Errands", new Task("Pick up dry cleaning, and do something else because we need a really long description for testing!"));
        data.addTask("Errands", new Task("Get milk"));
        data.getTaskFromCategory("Errands",1).addComment("Example Comment!");

        data.addCategory("Other");
        data.addTask("Other", new Task("Cut the grass"));
        data.addTask("Other", new Task("Get gas from the gas station"));

        return data;
    }
}