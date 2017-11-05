package com.appchana.squashapp.resolvers;

import com.appchana.squashapp.dao.UserRepository;
import com.appchana.squashapp.model.Match;
import com.appchana.squashapp.model.User;
import com.coxautodev.graphql.tools.GraphQLResolver;

/**
 * Created by ivanmolera on 03/11/2017.
 */
public class MatchResolver implements GraphQLResolver<Match> {

    private final UserRepository userRepository;

    public MatchResolver(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User postedBy(Match match) {
        if (match.getUserId() == null) {
            return null;
        }
        return userRepository.findById(match.getUserId());
    }
}
