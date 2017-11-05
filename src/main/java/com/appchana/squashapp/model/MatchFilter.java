package com.appchana.squashapp.model;

/**
 * Created by ivanmolera on 05/11/2017.
 */
import com.fasterxml.jackson.annotation.JsonProperty;

public class MatchFilter {

    private String descriptionContains;
    private String urlContains;

    @JsonProperty("description_contains") //the name must match the schema
    public String getDescriptionContains() {
        return descriptionContains;
    }

    public void setDescriptionContains(String descriptionContains) {
        this.descriptionContains = descriptionContains;
    }

    @JsonProperty("url_contains")
    public String getUrlContains() {
        return urlContains;
    }

    public void setUrlContains(String urlContains) {
        this.urlContains = urlContains;
    }
}
