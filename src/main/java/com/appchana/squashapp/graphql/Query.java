package com.appchana.squashapp.graphql;

import com.appchana.squashapp.dao.MatchRepository;
import com.appchana.squashapp.dao.UserRepository;
import com.appchana.squashapp.model.Match;
import com.appchana.squashapp.model.MatchFilter;
import com.appchana.squashapp.model.User;
import com.appchana.squashapp.model.UserFilter;
import com.coxautodev.graphql.tools.GraphQLRootResolver;

import java.util.List;

/**
 * Created by ivanmolera on 03/11/2017.
 */
public class Query implements GraphQLRootResolver {

    private final MatchRepository matchRepository;
    private final UserRepository userRepository;

    public Query(MatchRepository matchRepository, UserRepository userRepository) {
        this.matchRepository = matchRepository;
        this.userRepository = userRepository;
    }

    public List<User> allUsers(UserFilter filter, Number skip, Number first) {
        return userRepository.getAllUsers(filter, skip.intValue(), first.intValue());
    }

    public List<Match> allMatches(MatchFilter filter, Number skip, Number first) {
        return matchRepository.getAllMatches(filter, skip.intValue(), first.intValue());
    }
}
