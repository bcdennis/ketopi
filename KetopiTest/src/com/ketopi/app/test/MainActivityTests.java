package com.ketopi.app.test;

import android.test.ActivityInstrumentationTestCase2;
import android.test.ViewAsserts;
import android.test.suitebuilder.annotation.SmallTest;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;

import com.ketopi.app.SearchActivity;

public class MainActivityTests extends
ActivityInstrumentationTestCase2<SearchActivity> {

    private SearchActivity mActivity;
    private EditText mSearchText;
    private ListView mSearchList;
    private Button mSearchButton;

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

        mSearchList = (ListView) mActivity
                .findViewById(com.ketopi.app.R.id.searchList);

        mSearchButton = (Button) mActivity
                .findViewById(com.ketopi.app.R.id.searchButton);

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
     * android:id="@+id/searchButton"
     */
    @SmallTest
    public void testSearchButtonProperties() {
        /*
         * ====== [ Untestable Attributes (AFAIK) ] ======
         * android:onClick="searchButtonOnClick"
         */

        // android:text="@string/searchButton_text" */
        assertEquals("Go", mSearchButton.getText().toString());

        LayoutParams parameters = (LayoutParams) mSearchButton
                .getLayoutParams();

        int[] rules = parameters.getRules();

        // android:layout_height="wrap_content" */
        assertEquals("mSearchButton height is not WRAP_CONTENT",
                ViewGroup.LayoutParams.WRAP_CONTENT, parameters.height);

        // android:layout_width="wrap_content" */
        assertEquals("mSearchButton height is not WRAP_CONTENT",
                ViewGroup.LayoutParams.WRAP_CONTENT, parameters.width);

        // android:layout_alignParentRight="true" */
        assertTrue(RelativeLayout.TRUE == rules[RelativeLayout.ALIGN_PARENT_RIGHT]);
    }

    /*
     * android:id="@+id/searchList"
     */
    @SmallTest
    public void testSearchListProperties() {

        /*
         * ====== [ Untestable Attributes (AFAIK) ] ======
         */
        assertNull("No adapter should be assigned.", mSearchList.getAdapter());

        LayoutParams parameters = (LayoutParams) mSearchList.getLayoutParams();

        int[] rules = parameters.getRules();

        // android:layout_height="wrap_content"
        assertEquals("SearchText height is not WRAP_CONTENT",
                ViewGroup.LayoutParams.WRAP_CONTENT, parameters.height);

        // android:layout_width="match_parent"
        assertEquals("SearchText width is not MATCH_PARENT",
                ViewGroup.LayoutParams.MATCH_PARENT, parameters.width);

        // android:layout_alignParentLeft="true"
        assertTrue(RelativeLayout.TRUE == rules[RelativeLayout.ALIGN_PARENT_LEFT]);

        // android:layout_alignParentTop="true"
        assertTrue(RelativeLayout.TRUE == rules[RelativeLayout.ALIGN_PARENT_BOTTOM]);

        // android:layout_toLeftOf="@+id/searchButton"
        assertTrue(com.ketopi.app.R.id.searchButton == rules[RelativeLayout.BELOW]);
    }

    /*
     * android:id="@+id/searchText"
     */
    @SmallTest
    public void testSearchTextProperties() {

        /*
         * ====== [ Untestable Attributes (AFAIK) ] ======
         *  android:ems="10"
         */

        // android:hint="@string/searchText_hint"
        assertEquals("Search our database\u2026", mSearchText.getHint()
                .toString());

        // android:text="@string/searchText_text"
        assertEquals("", mSearchText.getText().toString());

        LayoutParams parameters = (LayoutParams) mSearchText.getLayoutParams();

        int[] rules = parameters.getRules();

        // android:layout_height="wrap_content"
        assertEquals("SearchText height is not WRAP_CONTENT",
                ViewGroup.LayoutParams.WRAP_CONTENT, parameters.height);

        // android:layout_width="wrap_content"
        assertEquals("SearchText width is not WRAP_CONTENT",
                ViewGroup.LayoutParams.WRAP_CONTENT, parameters.width);

        // android:layout_alignParentLeft="true"
        assertTrue(RelativeLayout.TRUE == rules[RelativeLayout.ALIGN_PARENT_LEFT]);

        // android:layout_alignParentTop="true"
        assertTrue(RelativeLayout.TRUE == rules[RelativeLayout.ALIGN_PARENT_TOP]);

        // android:layout_toLeftOf="@+id/searchButton"
        assertTrue(com.ketopi.app.R.id.searchButton == rules[RelativeLayout.LEFT_OF]);

    }

    @SmallTest
    public void testUIHasElements() {
        assertNotNull("Search Text View exists.", mSearchText);
        assertNotNull("Search List View exists.", mSearchList);
        assertNotNull("Search Button exists.", mSearchButton);

    }

    @SmallTest
    public void testViewsOnScreen() {
        final View origin = mActivity.getWindow().getDecorView();

        ViewAsserts.assertOnScreen(origin, mSearchText);
        ViewAsserts.assertOnScreen(origin, mSearchList);
        ViewAsserts.assertOnScreen(origin, mSearchButton);
    }

}
