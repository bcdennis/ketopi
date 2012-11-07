package com.ketopi.app;

import android.widget.ListView;



public class Searcher implements SearchTaskCompleteListener<String, Food[]> {

	private ListView mResultsView;
	private SearchActivity mParentActivity;
	private String mQuery = "";

	public Searcher(final SearchActivity parent, final ListView resultsListView) {

		mParentActivity = parent;
		mResultsView = resultsListView;
	}

	public String getQuery() {
		return mQuery;
	}

	public void onTaskComplete(final String query, final Food[] results) {

		mParentActivity.cacheSearchResults(results);
		SearchListAdapter adapter = new SearchListAdapter(mParentActivity,
				results);
		mResultsView.setAdapter(adapter);
	}

	public void query(final String queryString) {
		mQuery = queryString;
		new SearchTask(mParentActivity, this).execute(mQuery);

	}

}
