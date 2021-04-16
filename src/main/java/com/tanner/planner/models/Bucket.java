package com.tanner.planner.models;

public class Bucket {


    private String id, panelId, title;

    public Bucket(String id, String panelId, String title) {
        this.id = id;
        this.panelId = panelId;
        this.title = title;
    }

    public String getID() {
        return this.id;
    }

    public void setID(String id) {
        this.id = id;
    }

    public String getPanel_id() {
        return panelId;
    }

    public void setPanel_id(String panelId) {
        this.panelId = panelId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
