package com.ketopi.app;

import android.widget.ListView;

/**
 * The Class Searcher.
 */
public class Searcher implements ISearchTaskCompleteListener<String, Food[]> {

    /** The Results view. */
    private final ListView mResultsView;

    /** The Parent activity. */
    private final SearchActivity mParentActivity;

    /** The Query. */
    private String mQuery = "";

    /**
     * Instantiates a new searcher.
     *
     * @param parent the parent
     * @param resultsListView the results list view
     */
    public Searcher(final SearchActivity parent, final ListView resultsListView) {

        mParentActivity = parent;
        mResultsView = resultsListView;
    }

    /**
     * Gets the query.
     *
     * @return the query
     */
    public String getQuery() {
        return mQuery;
    }

    /* (non-Javadoc)
     * @see com.ketopi.app.ISearchTaskCompleteListener#onTaskComplete(java.lang.Object, java.lang.Object)
     */
    /**
     * Callback for the completion of the search task.
     *
     * @param query - the original query string.
     * @param results - the results array.
     */
    public void onTaskComplete(final String query, final Food[] results) {

        mParentActivity.cacheSearchResults(results);
        final SearchListAdapter adapter = new SearchListAdapter(mParentActivity,
                results);
        mResultsView.setAdapter(adapter);
    }

    /**
     * Query.
     *
     * @param queryString the query string
     */
    public void query(final String queryString) {
        mQuery = queryString;
        new SearchTask(mParentActivity, this).execute(mQuery);

    }

}
