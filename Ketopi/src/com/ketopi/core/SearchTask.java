package com.ketopi.core;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.apache.http.auth.AuthenticationException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.AsyncTask;
import android.util.Log;

import com.ketopi.rest.RestClient;
import com.ketopi.rest.RestException;
import com.ketopi.rest.RestMethod;

// TODO: Auto-generated Javadoc
/**
 * The Class SearchTask.
 *
 * @author brad
 */
public class SearchTask {

    /**
     * The Class AsyncSearch.
     */
    private class AsyncSearch extends
    AsyncTask<SearchRequest, Void, SearchResult> {

        /* (non-Javadoc)
         * @see android.os.AsyncTask#doInBackground(Params[])
         */
        @Override
        protected SearchResult doInBackground(final SearchRequest... arg0) {
            return fetch(arg0[0]);
        }

        /* (non-Javadoc)
         * @see android.os.AsyncTask#onPostExecute(java.lang.Object)
         */
        @Override
        protected void onPostExecute(final SearchResult result) {
            mListener.onSearchFinish(result);
        }

        /* (non-Javadoc)
         * @see android.os.AsyncTask#onPreExecute()
         */
        @Override
        protected void onPreExecute() {
            mListener.onSearchStart();
        }
    }

    /** The Listener. */
    protected ISearchListener<SearchResult> mListener;

    /** The LogCat Tag. */
    private static final String TAG = "Ketopi";

    /** The Constant HTTP_OK. */
    private static final int HTTP_OK = 200;

    /**
     * Instantiates a new search task.
     *
     * @param listener the listener
     */
    public SearchTask(final ISearchListener<SearchResult> listener) {
        mListener = listener;
    }

    /**
     * Executes the search query.
     *
     * @param request the search to execute.
     */
    public void execute(final SearchRequest request) {
        new AsyncSearch().execute(request);
    }

    /**
     * Fetch.
     *
     * @param request the request
     * @return the search result
     */
    protected SearchResult fetch(final SearchRequest request) {
        SearchResult results = new SearchResult();
        results.query = "";
        results.response = "";

        final RestClient client = new RestClient(request.url
                + URLEncoder.encode(request.query));

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
    protected SearchResult process(final SearchResult result) {

        result.response = result.response.trim() + "}";

        try {
            final JSONObject response = new JSONObject(result.response);
            result.query = response.getString("query");
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