package com.ketopi.app;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;

import com.crittercism.app.Crittercism;
import com.google.gson.Gson;
import com.ketopi.rest.RestClient;
import com.ketopi.rest.RestException;
import com.ketopi.rest.RestMethod;

/**
 * The Class SearchActivity.
 */
public class SearchActivity extends Activity {

    /**
     * Inner class used to hold search results.
     * @author Brad Dennis
     */
    private class SearchResult {
        // CHECKSTYLE.OFF: Visibility - Explicit use of the Holder Pattern
        public final List<Exception> exceptions = new ArrayList<Exception>();
        public String query;
        public String response;
        // CHECKSTYLE.ON: Visibility
    }

    /**
     * Inner AsyncTask class to execute the search results.
     *
     * @author Brad Dennis
     */
    private class SearchTask extends AsyncTask<String, Void, SearchResult> {
        /** The LogCat Tag. */
        private static final String TAG = "Ketopi";

        /** The Constant HTTP_OK. */
        private static final int HTTP_OK = 200;

        private String mAPI = "http://www.ketopi.com/api/search.json?query=";

        @Override
        protected SearchResult doInBackground(final String... arg0) {
            SearchResult results = new SearchResult();
            results.query = arg0[0];
            results.response = "";

            final RestClient client = new RestClient(mAPI
                    + URLEncoder.encode(results.query));


            try {
                client.execute(RestMethod.GET);
                if (client.getResponseCode() != HTTP_OK) {
                    Log.e(TAG, client.getErrorMessage());
                    results.exceptions.add(new RestException(client.getErrorMessage()));
                }
                // return valid data
                results.response = client.getResponse();

            } catch (Exception e) {
                Log.e(TAG, client.getErrorMessage());
                results.exceptions.add(new RestException(client.getErrorMessage()));
            }

            return results;
        }

        /* (non-Javadoc)
         * @see android.os.AsyncTask#onPostExecute(java.lang.Object)
         */
        @Override
        protected void onPostExecute(final SearchResult result) {

            result.response = result.response.trim() + "}";

            try {
                final JSONObject response = new JSONObject(result.response);
                final JSONArray json = response.getJSONArray("results");
                final List<Food> items = new ArrayList<Food>();

                for (int i = 0; i < json.length(); ++i) {
                    items.add(Food.fromJSON(json.getJSONObject(i)));
                }

                setLastQuery(result.query);
                setLastResults(items.toArray(new Food[]{}));

            } catch (JSONException e) {
                Log.e(TAG, e.getMessage());
            } finally {
                refreshList();
                mProgress.dismiss();
            }


            super.onPostExecute(result);
        }

        /* (non-Javadoc)
         * @see android.os.AsyncTask#onPreExecute()
         */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgress.show();
        }

    }


    /** The Search text. */
    private EditText mSearchText;

    /** The last query executed. */
    private String mLastQuery;

    /** The last search results list. */
    private Food[] mLastResults;

    /** The Serialized results. */
    private String mSerializedResults;

    private ListView mResultsList;

    private ProgressDialog mProgress;


    /**
     * Gets the last query.
     *
     * @return the lastQuery
     */
    public String getLastQuery() {
        return mLastQuery;
    }

    /**
     * Gets the last results.
     *
     * @return the lastResults
     */
    public Food[] getLastResults() {
        return mLastResults;
    }

    /**
     * Gets the serialized results.
     *
     * @return the serializedResults
     */
    public String getSerializedResults() {
        return mSerializedResults;
    }

    /* (non-Javadoc)
     * @see android.app.Activity#onCreate(android.os.Bundle)
     */
    @Override
    public void onCreate(final Bundle bundle) {
        super.onCreate(bundle);
        Crittercism.init(getApplicationContext(), "509ea479d5f9b91c6c000004");
        setContentView(R.layout.activity_search);

        mSearchText = (EditText) findViewById(R.id.searchText);
        mResultsList = (ListView) findViewById(R.id.searchList);

        mProgress = new ProgressDialog(this, R.style.BusyWaitDialog);
        mProgress.setMessage(getString(R.string.searchDialog_text));
        mProgress.setIndeterminate(true);
        mProgress.setCancelable(false);

        // BADSMELL Optimistic Resume
        if (bundle != null) {
            setLastQuery(bundle.getString("query"));
            setLastResults(unserializeResults(bundle.getString("results")));

            refreshList();
        }

    }


    /* (non-Javadoc)
     * @see android.app.Activity#onSaveInstanceState(android.os.Bundle)
     */
    @Override
    protected void onSaveInstanceState(final Bundle outState) {

        outState.putString("query", getLastQuery());
        outState.putString("results", getSerializedResults());

        super.onSaveInstanceState(outState);
    }

    /**
     * Updates the listview with new results.
     */
    private void refreshList() {
        mSearchText.setText(getLastQuery());

        final SearchListAdapter adapter = new SearchListAdapter(this, getLastResults());
        mResultsList.setAdapter(adapter);
    }

    /**
     * Search button on click.
     *
     * @param arg0 the arg0
     */
    public void searchButtonOnClick(final View arg0) {
        SearchTask task = new SearchTask();
        task.execute(mSearchText.getText().toString());
    }

    /**
     * Convert the results to a JSON string.
     *
     * @param results the results.
     * @return string the results serialized.
     */
    private String serializeResults(final Food[] results) {
        final Gson gson = new Gson();
        return gson.toJson(results);
    }

    /**
     * Sets the last query.
     *
     * @param lastQuery the lastQuery to set
     */
    public void setLastQuery(final String lastQuery) {
        mLastQuery = lastQuery;
    }

    /**
     * Sets the last results.
     *
     * @param lastResults the lastResults to set
     */
    public void setLastResults(final Food[] lastResults) {
        mLastResults = lastResults;
        mSerializedResults = serializeResults(lastResults);
    }



    /**
     * Unserialize the results.
     *
     * @param serialized the serialized
     * @return the list
     */
    private Food[] unserializeResults(final String serialized) {
        final Gson gson = new Gson();
        //        return gson.fromJson(serialized,
        //                new TypeToken<List<Food>>() {
        //        } .getType());

        return gson.fromJson(serialized, Food[].class);

    }
}


