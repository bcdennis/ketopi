package com.ketopi.app;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;

import com.crittercism.app.Crittercism;
import com.google.gson.Gson;

/**
 * The Class SearchActivity.
 */
public class SearchActivity extends Activity {

    /** The Searcher. */
    private Searcher mSearcher;

    /** The Search text. */
    private EditText mSearchText;

    /** The last query executed. */
    private String mLastQuery;

    /** The last search results list. */
    private Food[] mLastResults;

    /** The Serialized results. */
    private String mSerializedResults;





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
        final ListView resultsList = (ListView) findViewById(R.id.searchList);

        mSearcher = new Searcher(this, resultsList);

        // BADSMELL Optimistic Resume
        if (bundle != null) {
            setLastQuery(bundle.getString("query"));
            setLastResults(unserializeResults(bundle.getString("results")));

            mSearchText.setText(getLastQuery());

            final SearchListAdapter adapter = new SearchListAdapter(this, getLastResults());
            resultsList.setAdapter(adapter);
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
     * Search button on click.
     *
     * @param arg0 the arg0
     */
    public void searchButtonOnClick(final View arg0) {
        mSearcher.query(mSearchText.getText().toString());
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


