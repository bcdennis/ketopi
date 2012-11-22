package com.ketopi.app;

import java.util.List;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ketopi.core.Food;
import com.ketopi.core.ISearchListener;
import com.ketopi.core.NoOp;
import com.ketopi.core.SearchRequest;
import com.ketopi.core.SearchResult;
import com.ketopi.core.SearchTask;

/**
 * The Class SearchActivity.
 */
public class SearchActivity extends Activity implements
ISearchListener<SearchResult> {

    /** The Constant API. */
    private static final String API = "http://www.ketopi.com/api/search.json";
    //    private static final String API = "http://www.ketopi.com/api_test/search_longrunning.json";
    //    private static final String API = "http://www.ketopi.com/api_test/search_largeresult.json";

    /** The Search text. */
    private EditText mSearchText;

    /** The last query executed. */
    private String mLastQuery;

    /** The last search results list. */
    private List<Food> mLastResults;

    /** The listview container for the search results. */
    private ListView mResultsList;

    /** The progress dialog. */
    private ProgressDialog mProgress;

    /** The Search task. */
    private SearchTask mSearchTask;

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
    public List<Food> getLastResults() {
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
        mSearchTask = new SearchTask(this);

        mProgress = new ProgressDialog(this, R.style.BusyWaitDialog);
        mProgress.setMessage(getString(R.string.searchDialog_text));
        mProgress.setIndeterminate(true);
        mProgress.setCancelable(false);

        // BADSMELL Optimistic Resume
        if (bundle != null) {
            setLastQuery(bundle.getString("query"));
            setLastResults(unserialize(bundle.getString("results")));

            mSearchText.setText(getLastQuery());
            final SearchListAdapter adapter = new SearchListAdapter(this,
                    getLastResults());
            mResultsList.setAdapter(adapter);

        } else {
            NoOp.getInstance().run();
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
     * Called when the search task is finished.
     * @param result - the returned search result.
     */
    public void onSearchFinish(final SearchResult result) {
        setLastQuery(result.getQuery());
        setLastResults(result.getFoods());

        mSearchText.setText(getLastQuery());
        final SearchListAdapter adapter = new SearchListAdapter(this,
                getLastResults());
        mResultsList.setAdapter(adapter);

        mProgress.dismiss();

    }

    /**
     * Called when a search task is about to be executed.
     */
    public void onSearchStart() {
        mProgress.show();
    }

    /**
     * Search button on click.
     *
     * @param arg0 the arg0
     */
    public void searchButtonOnClick(final View arg0) {
        mSearchTask.execute(new SearchRequest(API, mSearchText.getText()
                .toString()));

    }

    /**
     * Convert the results to a JSON string.
     *
     * @param results the results.
     * @return string the results serialized.
     */
    private String serialize(final List<Food> results) {
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
    public void setLastResults(final List<Food> lastResults) {
        mLastResults = lastResults;
    }

    /**
     * Sets the search task.
     *
     * @param task the new search task
     */
    public void setSearchTask(final SearchTask task) {
        mSearchTask = task;
    }

    /**
     * Unserialize the results.
     *
     * @param serialized the serialized
     * @return the list
     */
    private List<Food> unserialize(final String serialized) {
        final Gson gson = new Gson();
        return gson.fromJson(serialized, new TypeToken<List<Food>>() { }
        .getType());

    }
}
