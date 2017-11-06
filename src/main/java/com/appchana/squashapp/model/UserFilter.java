package com.appchana.squashapp.model;

/**
 * Created by ivanmolera on 05/11/2017.
 */
import com.fasterxml.jackson.annotation.JsonProperty;

public class UserFilter {

    private String nameContains;
    private String emailContains;

    @JsonProperty("name_contains") //the name must match the schema
    public String getNameContains() {
        return nameContains;
    }

    public void setNameContains(String nameContains) {
        this.nameContains = nameContains;
    }

    @JsonProperty("email_contains")
    public String getEmailContains() {
        return emailContains;
    }

    public void setEmailContains(String emailContains) {
        this.emailContains = emailContains;
    }
}
