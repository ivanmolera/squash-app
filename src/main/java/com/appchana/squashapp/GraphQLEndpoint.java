package com.appchana.squashapp;
import com.appchana.squashapp.auth.AuthContext;
import com.appchana.squashapp.dao.MatchRepository;
import com.appchana.squashapp.dao.UserRepository;
import com.appchana.squashapp.dao.VoteRepository;
import com.appchana.squashapp.error.SanitizedError;
import com.appchana.squashapp.graphql.Mutation;
import com.appchana.squashapp.graphql.Query;
import com.appchana.squashapp.model.User;
import com.appchana.squashapp.resolvers.MatchResolver;
import com.appchana.squashapp.resolvers.SigninResolver;
import com.appchana.squashapp.resolvers.VoteResolver;
import com.appchana.squashapp.util.Scalars;
import com.coxautodev.graphql.tools.SchemaParser;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;
import graphql.ExceptionWhileDataFetching;
import graphql.GraphQLError;
import graphql.schema.GraphQLSchema;
import graphql.servlet.GraphQLContext;
import graphql.servlet.SimpleGraphQLServlet;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Created by ivanmolera on 03/11/2017.
 */
@WebServlet(urlPatterns = "/graphql")
public class GraphQLEndpoint extends SimpleGraphQLServlet {

    private static final MatchRepository MATCH_REPOSITORY;
    private static final UserRepository userRepository;
    private static final VoteRepository voteRepository;

    static {
        //Change to `new MongoClient("mongodb://<host>:<port>/squashapp")`
        //if you don't have Mongo running locally on port 27017
        MongoDatabase mongo = new MongoClient().getDatabase("squashapp");
        MATCH_REPOSITORY = new MatchRepository(mongo.getCollection("matches"));
        userRepository = new UserRepository(mongo.getCollection("users"));
        voteRepository = new VoteRepository(mongo.getCollection("votes"));
    }

    public GraphQLEndpoint() {
        super(buildSchema());
    }

    private static GraphQLSchema buildSchema() {
        return SchemaParser.newParser()
                .file("schema.graphqls")
                .resolvers(
                        new Query(MATCH_REPOSITORY),
                        new Mutation(MATCH_REPOSITORY, userRepository, voteRepository),
                        new SigninResolver(),
                        new MatchResolver(userRepository),
                        new VoteResolver(MATCH_REPOSITORY, userRepository)) //new resolver
                .scalars(Scalars.dateTime) //register the new scalar
                .build()
                .makeExecutableSchema();
    }

    @Override
    protected GraphQLContext createContext(Optional<HttpServletRequest> request, Optional<HttpServletResponse> response) {
        User user = request
                .map(req -> req.getHeader("Authorization"))
                .filter(id -> !id.isEmpty())
                .map(id -> id.replace("Bearer ", ""))
                .map(userRepository::findById)
                .orElse(null);
        return new AuthContext(user, request, response);
    }

    @Override
    protected List<GraphQLError> filterGraphQLErrors(List<GraphQLError> errors) {
        return errors.stream()
                .filter(e -> e instanceof ExceptionWhileDataFetching || super.isClientError(e))
                .map(e -> e instanceof ExceptionWhileDataFetching ? new SanitizedError((ExceptionWhileDataFetching) e) : e)
                .collect(Collectors.toList());
    }
}
