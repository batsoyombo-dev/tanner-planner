package com.tanner.planner.models;

/**
 * Task model
 *
 * #########     #      ##     ##  ##     ##  ########  #######
 *    ##        # #     ## #   ##  ## #   ##  ##        ##    ##
 *    ##       #   #    ##  #  ##  ##  #  ##  ########  #######
 *    ##      #######   ##   # ##  ##   # ##  ##        ## ##
 *    ##     #       #  ##     ##  ##     ##  ########  ##   ##
 *
 * @author Tanner Team
 * @version 1.0
 * @since 2021/05/07
 * @link https://github.com/batsoyombo-dev/tanner-planner
 */
public class Task {
    String id, bucket_id, title, description, state, date;
    public Task(String id, String bucket_id, String title, String description, String state, String date){
        this.id = id;
        this.bucket_id = bucket_id;
        this.title = title;
        this.description = description;
        this.state = state;
        this.date = date;
    }

    public String getDate() {
        return date;
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

    public void setState(String state) {
        this.state = state;
    }
}
