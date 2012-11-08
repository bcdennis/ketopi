package com.ketopi.app;

import java.net.URLEncoder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import com.ketopi.rest.RESTClient;
import com.ketopi.rest.RESTMethod;

/**
 * The Class SearchTask.
 */
public class SearchTask extends AsyncTask<String, Void, String> {

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
    private ISearchTaskCompleteListener<String, Food[]> mCallback;

    /** The Progress. */
    private ProgressDialog mProgress;

    /**
     * Instantiates a new search task.
     *
     * @param context the context
     * @param listener the listener
     */
    public SearchTask(final Context context,
            final ISearchTaskCompleteListener<String, Food[]> listener) {

        mProgress = new ProgressDialog(context, R.style.BusyWaitDialog);
        mProgress.setMessage(context.getString(R.string.searchDialog_text));
        mCallback = listener;

    }

    /* (non-Javadoc)
     * @see android.os.AsyncTask#doInBackground(Params[])
     */
    @Override
    protected String doInBackground(final String... args) {

        String query = args[0];
        String result = "";

        RESTClient client = new RESTClient(API + SEARCH
                + URLEncoder.encode(query));

        // client = new RESTClient(API_TEST + SEARCH_LR +
        // URLEncoder.encode(query));

        // client = new RESTClient(API_TEST + SEARCH_MF +
        // URLEncoder.encode(query));

        try {
            client.execute(RESTMethod.GET);
            if (client.getResponseCode() != HTTP_OK) {
                // return server error
                return client.getErrorMessage();
            }
            // return valid data
            result = client.getResponse();

        } catch (Exception e) {
            return e.toString();
        }

        return result;
    }

    /* (non-Javadoc)
     * @see android.os.AsyncTask#onPostExecute(java.lang.Object)
     */
    @Override
    protected final void onPostExecute(final String responseString) {

        String query = "";
        Food[] results = null;
        String str = responseString.trim() + "}";

        try {
            JSONObject response = new JSONObject(str);
            query = response.getString("query");
            JSONArray json = response.getJSONArray("results");
            results = new Food[json.length()];

            for (int i = 0; i < json.length(); ++i) {
                results[i] = new Food(json.getJSONObject(i));
            }

        } catch (JSONException e) {
            // BADSMELL Auto-generated catch block
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        mProgress.dismiss();

        mCallback.onTaskComplete(query, results);
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
