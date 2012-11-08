/*
 * Copyright (C) 2011 Tyler Smith.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.ketopi.rest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URLEncoder;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.auth.BasicScheme;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;

/**
 * @author Brad Dennis
 */
public class RESTClient {

    /**
     * Timeout for HTTP and Socket connection.
     */
    private static final int THIRTY_SECONDS = 30 * 1000;

    /**
     * @param is
     *            the InputStream
     * @return the converted string.
     */
    private static String convertStreamToString(final InputStream is) {

        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();

        String line = null;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }

    /**
     * Flag to use or not use Authentication.
     */
    private boolean mAuthentication;

    /**
     * The HTTP Headers.
     */
    private ArrayList<NameValuePair> mHeaders;

    /**
     * The message returned from the server.
     */
    private String                   mMessage;

    /**
     * The Name-Value Parameter list.
     */
    private ArrayList<NameValuePair> mParams;

    /**
     * The JSON Body.
     */
    private String                   mJsonBody;

    private String mResponse;

    private int mResponseCode;

    private String                   mUrl;

    // HTTP Basic Authentication
    private String mUsername;
    private String mPassword;

    //private Context mContext;

    /**
     * @param url
     *            The URL for the REST API
     */
    public RESTClient(final String url) {
        mUrl = url;
        mParams = new ArrayList<NameValuePair>();
        mHeaders = new ArrayList<NameValuePair>();
    }


    /**
     * Adds the basic authentication. Be warned that this is sent in clear text,
     * don't use Basic Authentication unless you have to.
     *
     * @param user
     *            the user
     * @param pass
     *            the pass
     */
    public final void addBasicAuthentication(final String user,
            final String pass) {
        mAuthentication = true;
        mUsername = user;
        mPassword = pass;
    }

    /**
     * Adds the body params.
     *
     * @param request
     *            the request
     * @return the HTTP URI request
     * @throws Exception
     *             the exception
     */
    private HttpUriRequest addBodyParams(final HttpUriRequest request)
            throws Exception {
        if (mJsonBody != null) {
            request.addHeader("Content-Type", "application/json");
            if (request instanceof HttpPost) {
                ((HttpPost) request).setEntity(new StringEntity(mJsonBody,
                        "UTF-8"));
            } else if (request instanceof HttpPut) {
                ((HttpPut) request).setEntity(new StringEntity(mJsonBody,
                        "UTF-8"));
            }

        } else if (!mParams.isEmpty()) {
            if (request instanceof HttpPost) {
                ((HttpPost) request).setEntity(new UrlEncodedFormEntity(mParams,
                        HTTP.UTF_8));
            } else if (request instanceof HttpPut) {
                ((HttpPut) request).setEntity(new UrlEncodedFormEntity(mParams,
                        HTTP.UTF_8));
            }
        }
        return request;
    }

    /**
     * Adds the get params.
     *
     * @return the string
     * @throws Exception
     *             the exception
     */
    private String addGetParams() throws Exception {
        StringBuffer combinedParams = new StringBuffer();
        if (!mParams.isEmpty()) {
            combinedParams.append("?");
            for (NameValuePair p : mParams) {
                combinedParams.append((combinedParams.length() > 1 ? "&" : "")
                        + p.getName() + "="
                        + URLEncoder.encode(p.getValue(), "UTF-8"));
            }
        }
        return combinedParams.toString();
    }

    /**
     * Adds the header.
     *
     * @param name
     *            the name
     * @param value
     *            the value
     */
    public final void addHeader(final String name, final String value) {
        mHeaders.add(new BasicNameValuePair(name, value));
    }

    /**
     * Adds the header params.
     *
     * @param request
     *            the request
     * @return the http uri request
     * @throws Exception
     *             the exception
     */
    private HttpUriRequest addHeaderParams(final HttpUriRequest request)
            throws Exception {
        for (NameValuePair h : mHeaders) {
            request.addHeader(h.getName(), h.getValue());
        }

        if (mAuthentication) {

            UsernamePasswordCredentials creds = new UsernamePasswordCredentials(
                    mUsername, mPassword);
            request.addHeader(new BasicScheme().authenticate(creds, request));
        }

        return request;
    }

    /**
     * Adds the param.
     *
     * @param name
     *            the name
     * @param value
     *            the value
     */
    public final void addParam(final String name, final String value) {
        mParams.add(new BasicNameValuePair(name, value));
    }

    /**
     * Execute.
     *
     * @param method
     *            the method
     * @throws Exception
     *             the exception
     */
    public final void execute(final RESTMethod method) throws Exception {

        switch (method) {
        case GET:
            HttpGet getRequest = new HttpGet(mUrl + addGetParams());
            getRequest = (HttpGet) addHeaderParams(getRequest);
            executeRequest(getRequest, mUrl);
            break;
        case POST:
            HttpPost postRequest = new HttpPost(mUrl);
            postRequest = (HttpPost) addHeaderParams(postRequest);
            postRequest = (HttpPost) addBodyParams(postRequest);
            executeRequest(postRequest, mUrl);
            break;
        case PUT:
            HttpPut putRequest = new HttpPut(mUrl);
            putRequest = (HttpPut) addHeaderParams(putRequest);
            putRequest = (HttpPut) addBodyParams(putRequest);
            executeRequest(putRequest, mUrl);
            break;
        case DELETE:
            HttpDelete deleteRequest = new HttpDelete(mUrl);
            deleteRequest = (HttpDelete) addHeaderParams(deleteRequest);
            executeRequest(deleteRequest, mUrl);
        default:
            break;
        }
    }

    /**
     * Execute request.
     *
     * @param request
     *            the request
     * @param url
     *            the url
     */
    private void executeRequest(final HttpUriRequest request,
            final String url) {

        DefaultHttpClient client = new DefaultHttpClient();
        HttpParams parameters = client.getParams();

        // http://developer.android.com/reference/org/apache/http/
        // params/HttpConnectionParams.html
        // **A timeout value of zero is interpreted as an infinite
        // timeout.
        // This value is used when no socket timeout is set in the
        // method parameters.**

        // BADSMELL Pollyannish Integration Point
        HttpConnectionParams.setConnectionTimeout(parameters, THIRTY_SECONDS);
        HttpConnectionParams.setSoTimeout(parameters, THIRTY_SECONDS);

        HttpResponse httpResponse;

        try {
            httpResponse = client.execute(request);
            mResponseCode = httpResponse.getStatusLine().getStatusCode();
            mMessage = httpResponse.getStatusLine().getReasonPhrase();

            HttpEntity entity = httpResponse.getEntity();

            if (entity != null) {

                InputStream instream = entity.getContent();
                mResponse = convertStreamToString(instream);

                // Closing the input stream will trigger connection release
                instream.close();
            }

        } catch (ClientProtocolException e) {
            client.getConnectionManager().shutdown();
            e.printStackTrace();
        } catch (IOException e) {
            client.getConnectionManager().shutdown();
            e.printStackTrace();
        }
    }

    /**
     * Gets the Error Message from the HTTP Transaction.
     *
     * @return the message
     */
    public final String getErrorMessage() {
        return mMessage;
    }

    /**
     * Gets the Response from the HTTP Transaction.
     *
     * @return the response
     */
    public final String getResponse() {
        return mResponse;
    }

    /**
     * Gets the Error Message from the HTTP Transaction.
     *
     * @return the calories
     */

    public final int getResponseCode() {
        return mResponseCode;
    }

    /**
     * Sets the Android Context.
     *
     * @param context
     *            The android context.
     */

    //    public final void setContext(final Context context) {
    //        mContext = context;
    //    }

    /**
     * Gets the Error Message from the HTTP Transaction.
     *
     * @param data
     *            the JSON String
     */
    public final void setJSONString(final String data) {
        mJsonBody = data;
    }
}
