package com.tanner.planner.models;

public class Bucket {


    private String id, panel_id, title;

    public Bucket(String id, String panel_id, String title) {
        this.id = id;
        this.panel_id = panel_id;
        this.title = title;
    }

    public String getID() {
        return this.id;
    }

    public void setID(String id) {
        this.id = id;
    }

    public String getPanel_id() {
        return panel_id;
    }

    public void setPanel_id(String panel_id) {
        this.panel_id = panel_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
