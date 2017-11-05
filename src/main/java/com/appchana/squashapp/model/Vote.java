package com.appchana.squashapp.model;

import java.time.ZonedDateTime;

/**
 * Created by ivanmolera on 05/11/2017.
 */
public class Vote {
    private final String id;
    private final ZonedDateTime createdAt;
    private final String userId;
    private final String matchId;

    public Vote(ZonedDateTime createdAt, String userId, String matchId) {
        this(null, createdAt, userId, matchId);
    }

    public Vote(String id, ZonedDateTime createdAt, String userId, String matchId) {
        this.id = id;
        this.createdAt = createdAt;
        this.userId = userId;
        this.matchId = matchId;
    }

    public String getId() {
        return id;
    }

    public ZonedDateTime getCreatedAt() {
        return createdAt;
    }

    public String getUserId() {
        return userId;
    }

    public String getMatchId() {
        return matchId;
    }
}
