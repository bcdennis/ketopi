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

    /** The Serialized results. */
    private String mResults;


    /**
     * Cache search results.
     *
     * @param results the results
     */
    public void cacheSearchResults(final Food[] results) {
        final Gson gson = new Gson();
        mResults = gson.toJson(results);
    }


    /* (non-Javadoc)
     * @see android.app.Activity#onCreate(android.os.Bundle)
     */
    @Override
    public void onCreate(final Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_search);

        mSearchText = (EditText) findViewById(R.id.searchText);
        final ListView resultsList = (ListView) findViewById(R.id.searchList);

        mSearcher = new Searcher(this, resultsList);

        // BADSMELL Optimistic Resume
        if (bundle != null) {
            final String queryString = bundle.getString("query");
            final String results = bundle.getString("results");
            final Gson gson = new Gson();
            final Food[] foods = gson.fromJson(results, Food[].class);

            mSearchText.setText(queryString);

            final SearchListAdapter adapter = new SearchListAdapter(this, foods);
            resultsList.setAdapter(adapter);
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
        outState.putString("results", mResults);
    }


    /**
     * Search button on click.
     *
     * @param arg0 the arg0
     */
    public void searchButtonOnClick(final View arg0) {
        mSearcher.query(mSearchText.getText().toString());
    }
}
