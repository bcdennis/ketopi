package com.ketopi.core;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.AsyncTask;
import android.util.Log;

import com.ketopi.rest.RestClient;
import com.ketopi.rest.RestException;

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
            getListener().onSearchFinish(result);
        }

        /* (non-Javadoc)
         * @see android.os.AsyncTask#onPreExecute()
         */
        @Override
        protected void onPreExecute() {
            getListener().onSearchStart();
        }
    }

    /** The Listener. */
    private ISearchListener<SearchResult> mListener;

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
        setListener(listener);
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

        RestClient client = new RestClient(request.url);

        try {
            client.addHeader("Accept", "application/json");
            client.addParam("query",
                    URLEncoder.encode(request.query, request.encoding));

            client.execute(new DefaultHttpClient());
            results.response = client.getResponse();

            if (client.getResponseCode() != HTTP_OK) {

                Log.e(TAG,
                        "HTTP RESP: " + Integer.toString(client.getResponseCode())
                        + " - " + client.getErrorMessage());
                results.exceptions.add(new RestException(client.getErrorMessage()));
            }

        } catch (UnsupportedEncodingException e) {
            Log.e(TAG, e.getMessage());
            results.exceptions.add(new RestException("Unsupported Encoding"));
        }

        return process(results);
    }

    /**
     * @return the listener
     */
    protected ISearchListener<SearchResult> getListener() {
        return mListener;
    }

    /**
     * Processes the response string and converts it to an object.
     *
     * @param result the unprocessed search result.
     * @return the processed search result.
     */
    protected SearchResult process(final SearchResult result) {


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

    /**
     * @param listener the listener to set
     */
    protected void setListener(final ISearchListener<SearchResult> listener) {
        mListener = listener;
    }
}
