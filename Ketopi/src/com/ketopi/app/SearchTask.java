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

public class SearchTask extends AsyncTask<String, Void, String> {

	private static String API = "http://www.ketopi.com/api/";
	private static String SEARCH = "search.json?query=";


	private static String API_TEST = "http://www.ketopi.com/api_test/";
	private static String SEARCH_LR = "search_longrunning.json?query=";
	private static String SEARCH_MF = "search_malformed.json?query=";

	private SearchTaskCompleteListener<String, Food[]> mCallback;

	private ProgressDialog mProgress;


	public SearchTask(final Context context,
			final SearchTaskCompleteListener<String, Food[]> listener) {


		mProgress = new ProgressDialog(context, R.style.BusyWaitDialog);
		mProgress.setMessage(context.getString(R.string.searchDialog_text));
		mCallback = listener;

	}

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
			if (client.getResponseCode() != 200) {
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

	@Override
	protected void onPostExecute(final String responseString) {

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
			// BADSMELL Auto-generated Catch Block
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		mProgress.dismiss();

		mCallback.onTaskComplete(query, results);
	}



	@Override
	protected void onPreExecute() {

		mProgress.setIndeterminate(true);
		mProgress.setCancelable(false);
		mProgress.show();

	}

}
