package com.appchana.squashapp.auth;

import com.appchana.squashapp.model.User;
import graphql.servlet.GraphQLContext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

/**
 * Created by ivanmolera on 03/11/2017.
 */
public class AuthContext extends GraphQLContext {

    private final User user;

    public AuthContext(User user, Optional<HttpServletRequest> request, Optional<HttpServletResponse> response) {
        super(request, response);
        this.user = user;
    }

    public User getUser() {
        return user;
    }
}
