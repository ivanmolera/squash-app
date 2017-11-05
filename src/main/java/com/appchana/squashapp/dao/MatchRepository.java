package com.appchana.squashapp.dao;

import com.appchana.squashapp.model.Match;
import com.appchana.squashapp.model.MatchFilter;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.mongodb.client.model.Filters.and;
import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Filters.regex;

/**
 * Created by ivanmolera on 03/11/2017.
 */
public class MatchRepository {

    private final MongoCollection<Document> matches;

    public MatchRepository(MongoCollection<Document> matches) {
        this.matches = matches;
    }

    private Match match(Document doc) {
        return new Match(
                doc.get("_id").toString(),
                doc.getString("url"),
                doc.getString("description"));
    }

    public Match findById(String id) {
        Document doc = matches.find(eq("_id", new ObjectId(id))).first();
        return match(doc);
    }

    public List<Match> getAllMatches(MatchFilter filter, int skip, int first) {
        Optional<Bson> mongoFilter = Optional.ofNullable(filter).map(this::buildFilter);

        List<Match> allMatches = new ArrayList<>();
        FindIterable<Document> documents = mongoFilter.map(matches::find).orElseGet(matches::find);
        for (Document doc : documents.skip(skip).limit(first)) {
            allMatches.add(match(doc));
        }
        return allMatches;
    }

    //builds a Bson from a MatchFilter
    private Bson buildFilter(MatchFilter filter) {
        String descriptionPattern = filter.getDescriptionContains();
        String urlPattern = filter.getUrlContains();
        Bson descriptionCondition = null;
        Bson urlCondition = null;
        if (descriptionPattern != null && !descriptionPattern.isEmpty()) {
            descriptionCondition = regex("description", ".*" + descriptionPattern + ".*", "i");
        }
        if (urlPattern != null && !urlPattern.isEmpty()) {
            urlCondition = regex("url", ".*" + urlPattern + ".*", "i");
        }
        if (descriptionCondition != null && urlCondition != null) {
            return and(descriptionCondition, urlCondition);
        }
        return descriptionCondition != null ? descriptionCondition : urlCondition;
    }

    public void saveMatch(Match match) {
        Document doc = new Document();
        doc.append("url", match.getUrl());
        doc.append("description", match.getDescription());
        doc.append("postedBy", match.getUserId());
        matches.insertOne(doc);
    }
}
