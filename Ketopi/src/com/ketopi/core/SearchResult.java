package com.ketopi.core;

import java.util.ArrayList;
import java.util.List;

/**
 * The Class SearchResult.
 */
public class SearchResult {


    /** The Exceptions. */
    private List<Exception> mExceptions = new ArrayList<Exception>();

    /** The Foods. */
    private List<Food> mFoods = new ArrayList<Food>();

    /** The Query. */
    private String mQuery;

    /** The Response. */
    private String mResponse;

    /**
     * Instantiates a new search result.
     */
    public SearchResult() {

    }

    /**
     * Gets the exceptions.
     *
     * @return the exceptions
     */
    public List<Exception> getExceptions() {
        return mExceptions;
    }

    /**
     * Gets the foods.
     *
     * @return the foods
     */
    public List<Food> getFoods() {
        return mFoods;
    }

    /**
     * Gets the query.
     *
     * @return the query
     */
    public String getQuery() {
        return mQuery;
    }

    /**
     * Gets the response.
     *
     * @return the response
     */
    public String getResponse() {
        return mResponse;
    }

    /**
     * Sets the query.
     *
     * @param query the query to set
     */
    public void setQuery(final String query) {
        this.mQuery = query;
    }

    /**
     * Sets the response.
     *
     * @param response the response to set
     */
    public void setResponse(final String response) {
        this.mResponse = response;
    }
}