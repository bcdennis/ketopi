package com.ketopi.core;

import com.ketopi.app.SearchActivity;

import android.os.AsyncTask;


/**
 * AsyncTask wrapper around Search Task.
 *
 * @author brad
 *
 */
public class AsyncSearchTask extends AsyncTask<String, Void, SearchResult> {


    private SearchActivity mActivity;

    public AsyncSearchTask(final SearchActivity activity) {
        mActivity = activity;
    }

    @Override
    protected SearchResult doInBackground(final String... arg0) {
        return new SearchTask().execute(arg0[0]);
    }

    /* (non-Javadoc)
     * @see android.os.AsyncTask#onPostExecute(java.lang.Object)
     */
    @Override
    protected void onPostExecute(final SearchResult result) {

        mActivity.processResult(result);
        mActivity.endProgress();
    }

    /* (non-Javadoc)
     * @see android.os.AsyncTask#onPreExecute()
     */
    @Override
    protected void onPreExecute() {
        mActivity.startProgress();
    }

}