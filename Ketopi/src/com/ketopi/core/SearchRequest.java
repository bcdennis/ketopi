package com.ketopi.core;
/**
 * The Class SearchRequest.
 */
public class SearchRequest {

    /** The URL. */
    private String mUrl = "";

    /** The query. */
    private String mQuery = "";

    /** The encoding. */
    private String mEncoding = "UTF-8";

    /**
     * Instantiates a new search request.
     */
    public SearchRequest() {

    }

    /**
     * Instantiates a new search request.
     *
     * @param arg0 the URL to access.
     * @param arg1 the Query parameter.
     */
    public SearchRequest(final String arg0, final String arg1) {
        mUrl = arg0;
        mQuery = arg1;
    }

    /**
     * Gets the encoding.
     *
     * @return the encoding
     */
    public String getEncoding() {
        return mEncoding;
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
     * Gets the URL.
     *
     * @return the URL
     */
    public String getUrl() {
        return mUrl;
    }

    /**
     * Sets the encoding.
     *
     * @param encoding the encoding to set
     */
    public void setEncoding(final String encoding) {
        this.mEncoding = encoding;
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
     * Sets the url.
     *
     * @param url the url to set
     */
    public void setUrl(final String url) {
        this.mUrl = url;
    }

}
