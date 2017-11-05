package com.appchana.squashapp.resolvers;

import com.appchana.squashapp.dao.MatchRepository;
import com.appchana.squashapp.dao.UserRepository;
import com.appchana.squashapp.model.Match;
import com.appchana.squashapp.model.User;
import com.appchana.squashapp.model.Vote;
import com.coxautodev.graphql.tools.GraphQLResolver;

/**
 * Created by ivanmolera on 05/11/2017.
 */
public class VoteResolver implements GraphQLResolver<Vote> {

    private final MatchRepository matchRepository;
    private final UserRepository userRepository;

    public VoteResolver(MatchRepository matchRepository, UserRepository userRepository) {
        this.matchRepository = matchRepository;
        this.userRepository = userRepository;
    }

    public User user(Vote vote) {
        return userRepository.findById(vote.getUserId());
    }

    public Match match(Vote vote) {
        return matchRepository.findById(vote.getMatchId());
    }
}
