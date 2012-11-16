package com.ketopi.app;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;

import com.google.gson.Gson;
import com.ketopi.core.AsyncSearchTask;
import com.ketopi.core.Food;
import com.ketopi.core.SearchResult;

/**
 * The Class SearchActivity.
 */
public class SearchActivity extends Activity {


    /** The Search text. */
    private EditText mSearchText;

    /** The last query executed. */
    private String mLastQuery;

    /** The last search results list. */
    private Food[] mLastResults;

    /** The listview container for the search results. */
    private ListView mResultsList;

    /** The progress dialog. */
    private ProgressDialog mProgress;

    /**
     * Dismisses the ProgressDialog.
     */
    public void endProgress() {
        mProgress.dismiss();

    } /**
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

    /* (non-Javadoc)
     * @see android.app.Activity#onCreate(android.os.Bundle)
     */
    @Override
    public void onCreate(final Bundle bundle) {
        super.onCreate(bundle);
        //Crittercism.init(getApplicationContext(), "509ea479d5f9b91c6c000004");
        setContentView(R.layout.activity_search);

        mSearchText = (EditText) findViewById(R.id.searchText);
        mSearchText.setText("DIGIORNO");
        mResultsList = (ListView) findViewById(R.id.searchList);

        mProgress = new ProgressDialog(this, R.style.BusyWaitDialog);
        mProgress.setMessage(getString(R.string.searchDialog_text));
        mProgress.setIndeterminate(true);
        mProgress.setCancelable(false);

        // BADSMELL Optimistic Resume
        if (bundle != null) {
            setLastQuery(bundle.getString("query"));
            setLastResults(unserialize(bundle.getString("results")));

            mSearchText.setText(getLastQuery());
            final SearchListAdapter adapter = new SearchListAdapter(this, getLastResults());
            mResultsList.setAdapter(adapter);

        }

    }

    /* (non-Javadoc)
     * @see android.app.Activity#onSaveInstanceState(android.os.Bundle)
     */
    @Override
    protected void onSaveInstanceState(final Bundle outState) {

        outState.putString("query", getLastQuery());
        outState.putString("results", serialize(getLastResults()));

        super.onSaveInstanceState(outState);
    }


    /**
     * Processes the SearcResult.
     *
     * @param result the result from the SearchTask
     */
    public void processResult(final SearchResult result) {

        setLastQuery(result.query);
        setLastResults(result.foods.toArray(new Food[]{}));

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
        new AsyncSearchTask(this).execute(mSearchText.getText().toString());
    }



    /**
     * Convert the results to a JSON string.
     *
     * @param results the results.
     * @return string the results serialized.
     */
    private String serialize(final Food[] results) {
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
    }

    /**
     * Shows the busy-wait progress dialog..
     */
    public void startProgress() {
        mProgress.show();
    }

    /**
     * Unserialize the results.
     *
     * @param serialized the serialized
     * @return the list
     */
    private Food[] unserialize(final String serialized) {
        final Gson gson = new Gson();
        return gson.fromJson(serialized, Food[].class);

    }
}


