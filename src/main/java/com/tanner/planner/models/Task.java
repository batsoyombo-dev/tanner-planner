package com.tanner.planner.models;

public class Task {
    String id, bucket_id, title, description, state;
    public Task(String id, String bucket_id, String title, String description, String state){
        this.id = id;
        this.bucket_id = bucket_id;
        this.title = title;
        this.description = description;
        this.state = state;
    }

    public String getTitle() {
        return title;
    }

    public String getBucket_id() {
        return bucket_id;
    }

    public String getDescription() {
        return description;
    }

    public String getId() {
        return id;
    }

    public String getState() {
        return state;
    }
}
