package com.appchana.squashapp.graphql;

import com.appchana.squashapp.auth.AuthContext;
import com.appchana.squashapp.auth.AuthData;
import com.appchana.squashapp.auth.SigninPayload;
import com.appchana.squashapp.dao.MatchRepository;
import com.appchana.squashapp.dao.UserRepository;
import com.appchana.squashapp.dao.VoteRepository;
import com.appchana.squashapp.model.Match;
import com.appchana.squashapp.model.User;
import com.appchana.squashapp.model.Vote;
import com.coxautodev.graphql.tools.GraphQLRootResolver;
import graphql.GraphQLException;
import graphql.schema.DataFetchingEnvironment;

import java.time.Instant;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;

/**
 * Created by ivanmolera on 03/11/2017.
 */
public class Mutation implements GraphQLRootResolver {

    private final MatchRepository matchRepository;
    private final UserRepository userRepository;
    private final VoteRepository voteRepository;

    public Mutation(MatchRepository matchRepository, UserRepository userRepository, VoteRepository voteRepository) {
        this.matchRepository = matchRepository;
        this.userRepository = userRepository;
        this.voteRepository = voteRepository;
    }

    public User createUser(String name, AuthData auth) {
        User newUser = new User(name, auth.getEmail(), auth.getPassword());
        return userRepository.saveUser(newUser);
    }

    // The way to inject the context is via DataFetchingEnvironment
    public Match createMatch(String url, String description, DataFetchingEnvironment env) {
        AuthContext context = env.getContext();
        Match newMatch = new Match(url, description, context.getUser().getId());
        matchRepository.saveMatch(newMatch);
        return newMatch;
    }

    public Vote createVote(String matchId, String userId) {
        ZonedDateTime now = Instant.now().atZone(ZoneOffset.UTC);
        return voteRepository.saveVote(new Vote(now, userId, matchId));
    }

    public SigninPayload signinUser(AuthData auth) throws IllegalAccessException {
        User user = userRepository.findByEmail(auth.getEmail());
        if (user.getPassword().equals(auth.getPassword())) {
            return new SigninPayload(user.getId(), user);
        }
        throw new GraphQLException("Invalid credentials");
    }
}