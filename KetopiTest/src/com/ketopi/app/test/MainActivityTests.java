package com.ketopi.app.test;

import android.test.ActivityInstrumentationTestCase2;
import android.test.ViewAsserts;
import android.test.suitebuilder.annotation.SmallTest;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;

import com.ketopi.app.SearchActivity;

public class MainActivityTests extends
ActivityInstrumentationTestCase2<SearchActivity> {

	private SearchActivity mActivity;
	private EditText mSearchText;
	private ListView     mSearchResultsList;

	public MainActivityTests(final String name) {
		super(SearchActivity.class);
		setName(name);
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		mActivity = getActivity();

		mSearchText = (EditText) mActivity
				.findViewById(com.ketopi.app.R.id.searchText);

		mSearchResultsList = (ListView) mActivity
				.findViewById(com.ketopi.app.R.id.searchList);

	}

	@Override
	protected void tearDown() throws Exception {
		super.tearDown();
	}

	@SmallTest
	public void testPreconditions() {
		assertNotNull("Main Activity is not null.", mActivity);
	}

	/*
	 * android:id="@+id/searchResultsList"
	 */
	@SmallTest
	public void testSearchResultslistProperties() {

	}

	/*
	 * android:id="@+id/foodSearchView"
	 */
	@SmallTest
	public void testSearchViewProperties() {
		// android:hint="@string/foodSearchView_hint"
		assertEquals("Search our database here\u2026", mSearchText
.getHint()
				.toString());
	}

	@SmallTest
	public void testUIHasElements() {
		assertNotNull("Food Search View exists.", mSearchText);
		assertNotNull("Food Search View exists.", mSearchResultsList);

	}

	@SmallTest
	public void testViewsOnScreen() {
		final View origin = mActivity.getWindow().getDecorView();

		ViewAsserts.assertOnScreen(origin, mSearchText);
		ViewAsserts.assertOnScreen(origin, mSearchResultsList);
	}

}
