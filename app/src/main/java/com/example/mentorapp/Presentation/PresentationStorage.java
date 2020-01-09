package com.example.mentorapp.Presentation;

import com.example.mentorapp.Category;

import java.util.ArrayList;

public class PresentationStorage {
    ArrayList<CategoryPresentation> categories;

    public PresentationStorage(){
        this.categories = new ArrayList<>();
    }

    public int getCategoryPosition(String category){
        for(int i=0; i< categories.size(); ++i){
            if (categories.get(i).category == category){
                return i;
            }
        }
        return -1;
    }

    public void setCategories(ArrayList<CategoryPresentation> categories) {
        this.categories = categories;
    }

    public ArrayList<CategoryPresentation> getCategories() {
        return categories;
    }

    public PresentationStorage getFlaggedPresentationStorage(){
        ArrayList<CategoryPresentation> flaggedCategories = new ArrayList<>();
        for (CategoryPresentation category : this.categories){
            ArrayList<TaskPresentation> flaggedTasks = new ArrayList<>();
            for (TaskPresentation task : category.getTaskPresentations()){
                ArrayList<DetailPresentation> flaggedDetails = new ArrayList<>();
                for (DetailPresentation detail : task.getStats()){
                    if(detail.getFlagged()){
                        //We know this task is flagged for this person
                        flaggedDetails.add(detail);
                    }
                }
                if (flaggedDetails.size() > 0){
                    TaskPresentation tempTaskPres = new TaskPresentation(task.getDescription(), flaggedDetails);
                    flaggedTasks.add(tempTaskPres);
                }
            }
            if (flaggedTasks.size() > 0){
                CategoryPresentation tempCategoryPres = new CategoryPresentation(category.getCategory(), flaggedTasks);
                flaggedCategories.add(tempCategoryPres);
            }
        }

        PresentationStorage flaggedPresentation = new PresentationStorage();
        flaggedPresentation.setCategories(flaggedCategories);
        return flaggedPresentation;
    }

    public int getCategoriesCount(){
        return categories.size();
    }

    public int getTaskCount(){
        int tasks = 0;
        for (CategoryPresentation category :categories) {
            tasks += category.taskPresentations.size();
        }
        return tasks;
    }
}
