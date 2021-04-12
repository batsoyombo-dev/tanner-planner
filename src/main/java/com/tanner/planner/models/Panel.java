package com.tanner.planner.models;

public class Panel {

    private int id, userId;
    private String title, description, category, colorConfig;

    public Panel(int id, int userId, String title, String description, String category, String colorConfig) {
        this.id = id;
        this.userId = userId;
        this.title = title;
        this.description = description;
        this.category = category;
        this.colorConfig = colorConfig;
    }

    public String getColorConfig() {
        return colorConfig;
    }

    public void setColorConfig(String colorConfig) {
        this.colorConfig = colorConfig;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

}
