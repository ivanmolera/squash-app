package com.appchana.squashapp.model;

/**
 * Created by ivanmolera on 03/11/2017.
 */
public class Match {

    private final String id;
    private final String url;
    private final String description;
    private final String firstUserId;
    private final String secondUserId;

    public Match(String url, String description, String firstUserId, String secondUserId) {
        this(null, url, description, firstUserId, secondUserId);
    }

    public Match(String id, String url, String description, String firstUserId, String secondUserId) {
        this.id = id;
        this.url = url;
        this.description = description;
        this.firstUserId = firstUserId;
        this.secondUserId = secondUserId;
    }

    public String getId() {
        return id;
    }

    public String getUrl() {
        return url;
    }

    public String getDescription() {
        return description;
    }

    public String getFirstUserId() {
        return firstUserId;
    }

    public String getSecondUserId() {
        return secondUserId;
    }
}
