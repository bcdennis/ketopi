package com.ketopi.test;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import android.os.Bundle;
import android.test.ActivityInstrumentationTestCase2;
import android.test.UiThreadTest;
import android.test.ViewAsserts;
import android.test.suitebuilder.annotation.SmallTest;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;

import com.google.gson.Gson;
import com.ketopi.app.SearchActivity;
import com.ketopi.core.Food;
import com.ketopi.test.mocks.SerialSearchTask;

public class SearchActivityTests extends
ActivityInstrumentationTestCase2<SearchActivity> {

    private SearchActivity mActivity;
    private EditText mSearchText;
    private ListView mSearchList;

    private Button mSearchButton;
    private List<Food> mFoods;
    private Food mDigiorno;
    private String mDigiornoString;

    private JSONObject mDigiornoJSON;
    private String mQuery;


    private String mFoodsSerialized;

    public SearchActivityTests(final String name) {
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

        // Test Data
        Gson gson = new Gson();
        mQuery = "DIGIORNO";
        //mResponse  =  "{\"query\":\"DIGIORNO\",\"results\":[{\"ndb_no\":\"21474\",\"long_desc\":\"DIGIORNO Pizza, cheese topping, rising crust, frozen, baked\",\"carbs\":\"32\",\"calories\":\"256\",\"fat\":\"9\",\"protein\":\"13\",\"fiber\":\"2\",\"sugars\":\"5\",\"net_carbs\":\"30\",\"amount\":\"1\",\"measure\":\"slice 1\\/4 of pie\",\"grams\":\"183\",\"rank\":\"6.34481477737427\"}]}";
        mDigiornoString = "{\"ndb_no\":\"21474\",\"long_desc\":\"DIGIORNO Pizza, cheese topping, rising crust, frozen, baked\",\"carbs\":\"32\",\"calories\":\"256\",\"fat\":\"9\",\"protein\":\"13\",\"fiber\":\"2\",\"sugars\":\"5\",\"net_carbs\":\"30\",\"amount\":\"1\",\"measure\":\"slice 1\\/4 of pie\",\"grams\":\"183\",\"rank\":\"6.34481477737427\"}";
        mDigiornoJSON = new JSONObject(mDigiornoString);
        mDigiorno = Food.fromJSON(mDigiornoJSON);
        mFoods = new ArrayList<Food>();
        mFoods.add(mDigiorno);
        mFoodsSerialized = gson.toJson(mFoods.toArray(new Food[]{}),Food[].class);

    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    @UiThreadTest
    public void testActivityOnResume() throws Throwable {

        Bundle bundle = new Bundle();
        bundle.putString("query", mQuery);
        bundle.putString("results", mFoodsSerialized);

        getInstrumentation().callActivityOnCreate(mActivity, bundle);
        getInstrumentation().callActivityOnResume(mActivity);

        mSearchText = (EditText) mActivity
                .findViewById(com.ketopi.app.R.id.searchText);

        assertTrue(mQuery.equals(mSearchText.getText().toString()));
    }

    @UiThreadTest
    public void testActivityOnSaveInstanceState() throws Throwable {

        Bundle bundle = new Bundle();

        mSearchText.setText("Cheese");
        mActivity.setSearchTask(new SerialSearchTask(mActivity));
        mSearchButton.performClick();

        getInstrumentation().callActivityOnSaveInstanceState(mActivity, bundle);
        getInstrumentation().callActivityOnPause(mActivity);
        getInstrumentation().callActivityOnStop(mActivity);
        getInstrumentation().callActivityOnDestroy(mActivity);

        //initialize activity with the saved bundle
        getInstrumentation().callActivityOnCreate(mActivity, bundle);
        getInstrumentation().callActivityOnResume(mActivity);

    }

    public void testListViewCaching() {


        mActivity.runOnUiThread(new Runnable() {
            public void run() {
                mSearchText.setText("cheddar");
                mActivity.setSearchTask(new SerialSearchTask(mActivity));
                mSearchButton.performClick();
            }
        });

        getInstrumentation().waitForIdleSync();
        mActivity.runOnUiThread(new Runnable() {
            public void run() {
                mSearchList.setSelection(30);
            }
        });

        getInstrumentation().waitForIdleSync();
        mActivity.runOnUiThread(new Runnable() {
            public void run() {
                mSearchList.setSelection(0);
            }
        });
        getInstrumentation().waitForIdleSync();

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

    @UiThreadTest
    public void testSearchOnClick() {
        mSearchText.setText(mQuery);
        mActivity.setSearchTask(new SerialSearchTask(mActivity));
        mSearchButton.performClick();

        assertTrue(mActivity.getLastQuery().equals(mQuery));
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
        assertEquals(mQuery, mSearchText.getText().toString());

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
