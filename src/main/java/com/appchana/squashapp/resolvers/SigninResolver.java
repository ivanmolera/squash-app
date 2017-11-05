package com.appchana.squashapp.resolvers;

import com.appchana.squashapp.auth.SigninPayload;
import com.appchana.squashapp.model.User;
import com.coxautodev.graphql.tools.GraphQLResolver;

/**
 * Created by ivanmolera on 03/11/2017.
 */
public class SigninResolver implements GraphQLResolver<SigninPayload> {

    public User user(SigninPayload payload) {
        return payload.getUser();
    }
}
