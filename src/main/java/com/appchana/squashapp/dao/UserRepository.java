package com.appchana.squashapp.dao;

import com.appchana.squashapp.model.Match;
import com.appchana.squashapp.model.MatchFilter;
import com.appchana.squashapp.model.User;
import com.appchana.squashapp.model.UserFilter;
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
public class UserRepository {

    private final MongoCollection<Document> users;

    public UserRepository(MongoCollection<Document> users) {
        this.users = users;
    }

    public List<User> getAllUsers(UserFilter filter, int skip, int first) {
        Optional<Bson> mongoFilter = Optional.ofNullable(filter).map(this::buildFilter);

        List<User> allUsers = new ArrayList<>();
        FindIterable<Document> documents = mongoFilter.map(users::find).orElseGet(users::find);
        for (Document doc : documents.skip(skip).limit(first)) {
            allUsers.add(user(doc));
        }
        return allUsers;
    }

    // builds a Bson from a UserFilter
    private Bson buildFilter(UserFilter filter) {
        String namePattern = filter.getNameContains();
        String emailPattern = filter.getEmailContains();
        Bson nameCondition = null;
        Bson emailCondition = null;
        if (namePattern != null && !namePattern.isEmpty()) {
            nameCondition = regex("name", ".*" + namePattern + ".*", "i");
        }
        if (emailPattern != null && !emailPattern.isEmpty()) {
            emailCondition = regex("url", ".*" + emailPattern + ".*", "i");
        }
        if (nameCondition != null && emailCondition != null) {
            return and(nameCondition, emailCondition);
        }
        return nameCondition != null ? nameCondition : emailCondition;
    }

    public User findByEmail(String email) {
        Document doc = users.find(eq("email", email)).first();
        return user(doc);
    }

    public User findById(String id) {
        Document doc = users.find(eq("_id", new ObjectId(id))).first();
        return user(doc);
    }

    public User saveUser(User user) {
        Document doc = new Document();
        doc.append("name", user.getName());
        doc.append("email", user.getEmail());
        doc.append("password", user.getPassword());
        users.insertOne(doc);
        return new User(
                doc.get("_id").toString(),
                user.getName(),
                user.getEmail(),
                user.getPassword());
    }

    private User user(Document doc) {
        return new User(
                doc.get("_id").toString(),
                doc.getString("name"),
                doc.getString("email"),
                doc.getString("password"));
    }
}
