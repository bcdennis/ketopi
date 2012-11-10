package com.ketopi.app;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.ketopi.rest.RestClient;
import com.ketopi.rest.RestException;
import com.ketopi.rest.RestMethod;

/**
 * The Class SearchTask.
 */
public class SearchTask extends AsyncTask<String, Void, String> {

    /** The LogCat Tag. */
    private static final String TAG = "Ketopi";

    /** The Constant HTTP_OK. */
    private static final int HTTP_OK = 200;

    /** The API Url. */
    private static final String API = "http://www.ketopi.com/api/";

    /** The search. */
    private static final String SEARCH = "search.json?query=";

    /** The Test API Url. */
    @SuppressWarnings("unused")
    private static final String TESTAPI = "http://www.ketopi.com/api_test/";

    /** The Long Running Search Test Method. */
    @SuppressWarnings("unused")
    private static final String SEARCHLONGRUNNING = "search_longrunning.json?query=";

    /** The Malformed Search Test Method. */
    @SuppressWarnings("unused")
    private static final String SEARCHMALFORMED = "search_malformed.json?query=";

    /** The Callback. */
    private final ISearchTaskCompleteListener<String, Food[]> mCallback;


    /** The Progress. */
    private final ProgressDialog mProgress;

    private final List<Exception> mExceptionsBag = new ArrayList<Exception>();

    /**
     * Instantiates a new search task.
     *
     * @param context the context
     * @param listener the listener
     */
    public SearchTask(final Context context,
            final ISearchTaskCompleteListener<String, Food[]> listener) {
        super();

        mProgress = new ProgressDialog(context, R.style.BusyWaitDialog);
        mProgress.setMessage(context.getString(R.string.searchDialog_text));
        mCallback = listener;

    }

    /* (non-Javadoc)
     * @see android.os.AsyncTask#doInBackground(Params[])
     */
    @Override
    protected String doInBackground(final String... args) {

        final String origQuery = args[0];
        String searchResult = ""; // NOPMD by brad on 11/9/12 12:50 PM

        final RestClient client = new RestClient(API + SEARCH
                + URLEncoder.encode(origQuery));

        // final RESTClient client = new RESTClient(API_TEST + SEARCH_LR +
        // URLEncoder.encode(query));

        // final RESTClient client = new RESTClient(API_TEST + SEARCH_MF +
        // URLEncoder.encode(query));

        try {
            client.execute(RestMethod.GET);
            if (client.getResponseCode() != HTTP_OK) {

                mExceptionsBag.add(new RestException(client.getErrorMessage()));
            }
            // return valid data
            searchResult = client.getResponse();

        } catch (Exception e) {
            mExceptionsBag.add(e);
        }

        return searchResult;
    }

    /* (non-Javadoc)
     * @see android.os.AsyncTask#onPostExecute(java.lang.Object)
     */
    @Override
    protected final void onPostExecute(final String responseString) {

        final String str = responseString.trim() + "}";

        try {
            final JSONObject response = new JSONObject(str);
            final String query = response.getString("query");
            final JSONArray json = response.getJSONArray("results");
            final List<Food> items = new ArrayList<Food>();

            for (int i = 0; i < json.length(); ++i) {
                items.add(Food.fromJSON(json.getJSONObject(i)));
            }

            mCallback.onTaskComplete(query, items.toArray(new Food[]{}));

        } catch (JSONException e) {
            Log.e(TAG, e.getMessage());
        } finally {

            mProgress.dismiss();
        }
    }

    /* (non-Javadoc)
     * @see android.os.AsyncTask#onPreExecute()
     */
    @Override
    protected final void onPreExecute() {

        mProgress.setIndeterminate(true);
        mProgress.setCancelable(false);
        mProgress.show();

    }

}
