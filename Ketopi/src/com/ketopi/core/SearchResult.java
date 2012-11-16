package com.ketopi.core;

import java.util.ArrayList;
import java.util.List;

public class SearchResult {


    public List<Exception> exceptions = new ArrayList<Exception>();
    public List<Food> foods = new ArrayList<Food>();
    public String query;
    public String response;

    public SearchResult() {

    }
}