package com.example.mentorapp;

public class ExampleDataPump {
    public static Evaluation getData() {
        Evaluation data = new Evaluation();

        data.addCategory("House");
        data.addTask("House", new Task("Clean Room"));
        data.addTask("House", new Task("Make Bed"));

        data.addCategory("Errands");
        data.addTask("Errands", new Task("Pick up dry cleaning"));
        data.addTask("Errands", new Task("Get milk"));

        return data;
    }
}