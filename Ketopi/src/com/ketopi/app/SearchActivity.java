package com.ketopi.app;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.google.gson.Gson;

public class SearchActivity extends Activity {


	private Searcher mSearcher;
	private Button mSearchButton;
	private EditText mSearchText;
	private ListView mSearchResultsList;
	private String mSerializedResults;


	public void cacheSearchResults(final Food[] results) {
		Gson gson = new Gson();
		mSerializedResults = gson.toJson(results);
	}


	@Override
	public void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search);

		mSearchButton = (Button)findViewById(R.id.searchButton);
		mSearchText = (EditText)findViewById(R.id.searchText);
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

	@Override
	public boolean onCreateOptionsMenu(final Menu menu) {
		getMenuInflater().inflate(R.menu.activity_search, menu);
		return true;
	}

	@Override
	protected void onSaveInstanceState(final Bundle outState) {
		super.onSaveInstanceState(outState);

		outState.putString("query", mSearcher.getQuery());
		outState.putString("results", mSerializedResults);
	}


	public void searchButtonOnClick(final View arg0) {
		String queryString = mSearchText.getText().toString();

		mSearcher.query(queryString);

	}

}
