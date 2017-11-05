package com.appchana.squashapp.dao;

import com.appchana.squashapp.util.Scalars;
import com.appchana.squashapp.model.Vote;
import com.mongodb.client.MongoCollection;

import org.bson.Document;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

import static com.mongodb.client.model.Filters.eq;

/**
 * Created by ivanmolera on 05/11/2017.
 */
public class VoteRepository {

    private final MongoCollection<Document> votes;

    public VoteRepository(MongoCollection<Document> votes) {
        this.votes = votes;
    }

    public List<Vote> findByUserId(String userId) {
        List<Vote> list = new ArrayList<>();
        for (Document doc : votes.find(eq("userId", userId))) {
            list.add(vote(doc));
        }
        return list;
    }

    public List<Vote> findByMatchId(String matchId) {
        List<Vote> list = new ArrayList<>();
        for (Document doc : votes.find(eq("matchId", matchId))) {
            list.add(vote(doc));
        }
        return list;
    }

    public Vote saveVote(Vote vote) {
        Document doc = new Document();
        doc.append("userId", vote.getUserId());
        doc.append("matchId", vote.getMatchId());
        doc.append("createdAt", Scalars.dateTime.getCoercing().serialize(vote.getCreatedAt()));
        votes.insertOne(doc);
        return new Vote(
                doc.get("_id").toString(),
                vote.getCreatedAt(),
                vote.getUserId(),
                vote.getMatchId());
    }

    private Vote vote(Document doc) {
        return new Vote(
                doc.get("_id").toString(),
                ZonedDateTime.parse(doc.getString("createdAt")),
                doc.getString("userId"),
                doc.getString("matchId")
        );
    }
}
