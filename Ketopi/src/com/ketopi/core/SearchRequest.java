package com.ketopi.core;

public class SearchRequest {
    public String url = "";
    public String query = "";
    public String encoding = "UTF-8";

    public SearchRequest() {

    }

    public SearchRequest(final String arg0, final String arg1) {
        url = arg0;
        query = arg1;
    }

}
