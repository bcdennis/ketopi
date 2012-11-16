/**
 * 
 */
package com.ketopi.core;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.apache.http.auth.AuthenticationException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import com.ketopi.rest.RestClient;
import com.ketopi.rest.RestException;
import com.ketopi.rest.RestMethod;

/**
 * @author brad
 *
 */
public class SearchTask {
    /** The LogCat Tag. */
    private static final String TAG = "Ketopi";

    /** The Constant HTTP_OK. */
    private static final int HTTP_OK = 200;

    private String mAPI = "http://www.ketopi.com/api/search.json?query=";


    /**
     * Executes the search query.
     *
     * @param query the search to execute.
     * @return the raw search response.
     */
    public SearchResult execute(final String query) {
        SearchResult results = new SearchResult();
        results.query = query;
        results.response = "";

        final RestClient client = new RestClient(mAPI
                + URLEncoder.encode(results.query));


        try {

            client.execute(RestMethod.GET);

        } catch (AuthenticationException e) {
            Log.e(TAG, e.getMessage());
            results.exceptions.add(new RestException(client.getErrorMessage()));

        } catch (UnsupportedEncodingException e) {
            Log.e(TAG, e.getMessage());
            results.exceptions.add(new RestException(client.getErrorMessage()));
        }

        if (client.getResponseCode() != HTTP_OK) {
            Log.e(TAG, client.getErrorMessage());
            results.exceptions.add(new RestException(client.getErrorMessage()));
        }
        // return valid data
        results.response = client.getResponse();


        return process(results);
    }

    /**
     * Processes the response string and converts it to an object.
     *
     * @param result the unprocessed search result.
     * @return the processed search result.
     */
    private SearchResult process(final SearchResult result) {

        result.response = result.response.trim() + "}";

        try {
            final JSONObject response = new JSONObject(result.response);
            final JSONArray json = response.getJSONArray("results");

            for (int i = 0; i < json.length(); ++i) {
                result.foods.add(Food.fromJSON(json.getJSONObject(i)));
            }

        } catch (JSONException e) {
            Log.e(TAG, e.getMessage());
        }

        return result;
    }
}
