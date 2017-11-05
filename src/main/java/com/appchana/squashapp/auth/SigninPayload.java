package com.appchana.squashapp.auth;

import com.appchana.squashapp.model.User;

/**
 * Created by ivanmolera on 03/11/2017.
 */
public class SigninPayload {

    private final String token;
    private final User user;

    public SigninPayload(String token, User user) {
        this.token = token;
        this.user = user;
    }

    public String getToken() {
        return token;
    }

    public User getUser() {
        return user;
    }
}
