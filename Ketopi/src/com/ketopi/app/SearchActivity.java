package com.ketopi.app;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;

import com.google.gson.Gson;

/**
 * The Class SearchActivity.
 */
public class SearchActivity extends Activity {


    /** The Searcher. */
    private Searcher mSearcher;

    /** The Search text. */
    private EditText mSearchText;

    /** The Search results list. */
    private ListView mSearchResultsList;

    /** The Serialized results. */
    private String mSerializedResults;


    /**
     * Cache search results.
     *
     * @param results the results
     */
    public void cacheSearchResults(final Food[] results) {
        Gson gson = new Gson();
        mSerializedResults = gson.toJson(results);
    }


    /* (non-Javadoc)
     * @see android.app.Activity#onCreate(android.os.Bundle)
     */
    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        mSearchText = (EditText) findViewById(R.id.searchText);
        mSearchResultsList = (ListView) findViewById(R.id.searchList);

        mSearcher = new Searcher(this, mSearchResultsList);

        // BADSMELL Optimistic Resume
        if (savedInstanceState != null) {
            String queryString = savedInstanceState.getString("query");
            String results = savedInstanceState.getString("results");
            Gson gson = new Gson();
            Food[] foods = gson.fromJson(results, Food[].class);

            mSearchText.setText(queryString);

            SearchListAdapter adapter = new SearchListAdapter(this, foods);
            mSearchResultsList.setAdapter(adapter);
        }

    }

    /* (non-Javadoc)
     * @see android.app.Activity#onCreateOptionsMenu(android.view.Menu)
     */
    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        getMenuInflater().inflate(R.menu.activity_search, menu);
        return true;
    }

    /* (non-Javadoc)
     * @see android.app.Activity#onSaveInstanceState(android.os.Bundle)
     */
    @Override
    protected void onSaveInstanceState(final Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putString("query", mSearcher.getQuery());
        outState.putString("results", mSerializedResults);
    }


    /**
     * Search button on click.
     *
     * @param arg0 the arg0
     */
    public void searchButtonOnClick(final View arg0) {
        String queryString = mSearchText.getText().toString();

        mSearcher.query(queryString);

    }

}
