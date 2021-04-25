package com.tanner.planner.models;

public class Activity {

    private int id;
    private String action;
    private Panel panel;

    public Activity(int id, String action, Panel panel) {
        this.id = id;
        this.action = action;
        this.panel = panel;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public Panel getPanel() {
        return panel;
    }

    public void setPanel(Panel panel) {
        this.panel = panel;
    }

}
