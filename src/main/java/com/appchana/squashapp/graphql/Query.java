package com.appchana.squashapp.graphql;

import com.appchana.squashapp.dao.MatchRepository;
import com.appchana.squashapp.model.Match;
import com.appchana.squashapp.model.MatchFilter;
import com.coxautodev.graphql.tools.GraphQLRootResolver;

import java.util.List;

/**
 * Created by ivanmolera on 03/11/2017.
 */
public class Query implements GraphQLRootResolver {

    private final MatchRepository matchRepository;

    public Query(MatchRepository matchRepository) {
        this.matchRepository = matchRepository;
    }

    public List<Match> allMatches(MatchFilter filter, Number skip, Number first) {
        return matchRepository.getAllMatches(filter, skip.intValue(), first.intValue());
    }
}
