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
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.auth.AuthenticationException;
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

import android.util.Log;

/**
 * The Class RestClient.
 */
public class RestClient {

    private static final int THIRTY_SECONDS = 30 * 1000;

    /** The LogCat Tag. */
    private static final String TAG = "Ketopi-REST";

    /**
     * Convert stream to string.
     *
     * @param stream the InputStream
     * @return the string
     */
    private static String convertStreamToString(final InputStream stream) {

        final BufferedReader reader = new BufferedReader(new InputStreamReader(
                stream));
        final StringBuilder buffer = new StringBuilder();

        try {
            String line = reader.readLine();
            while (line != null) {
                buffer.append(line + "\n");
                line = reader.readLine();
            }
        } catch (IOException e) {
            Log.e(TAG, e.getMessage());
        } finally {
            try {
                stream.close();
            } catch (IOException e) {
                Log.e(TAG, e.getMessage());
            }
        }
        return buffer.toString();
    }

    /** The authentication. */
    private boolean authentication;

    /** The headers. */
    private final List<NameValuePair> mHeaders;

    /** The json body. */
    private String mJsonBody;

    /** The message. */
    private String message;

    /** The params. */
    private final List<NameValuePair> mParams;

    /** The response. */
    private String mResponse;

    /** The response code. */
    private int mResponseCode;

    /** The url. */
    private final String mUrl;

    // HTTP Basic Authentication
    /** The username. */
    private String mUsername;

    /** The password. */
    private String mPassword;

    //    /** The context. */
    //    private Context mContext;

    /**
     * Instantiates a new rest client.
     *
     * @param url the URL
     */
    public RestClient(final String url) {
        this.mUrl = url;
        mParams = new ArrayList<NameValuePair>();
        mHeaders = new ArrayList<NameValuePair>();
    }

    //Be warned that this is sent in clear text, don't use basic auth unless you have to.
    /**
     * Adds the basic authentication.
     *
     * @param user the user
     * @param pass the pass
     */
    public void addBasicAuthentication(final String user, final String pass) {
        authentication = true;
        mUsername = user;
        mPassword = pass;
    }

    /**
     * Adds the body params.
     *
     * @param request the request
     * @return the HTTP URI request
     * @throws UnsupportedEncodingException
     *                  Thrown if an unrecognized encoding is encountered.
     */
    private HttpUriRequest addBodyParams(final HttpUriRequest request)
            throws UnsupportedEncodingException {

        if (!mParams.isEmpty()) {
            if (request instanceof HttpPost) {
                ((HttpPost) request).setEntity(new UrlEncodedFormEntity(
                        mParams, HTTP.UTF_8));
            } else if (request instanceof HttpPut) {
                ((HttpPut) request).setEntity(new UrlEncodedFormEntity(mParams,
                        HTTP.UTF_8));
            }
        }

        if (mJsonBody != null) {
            request.addHeader("Content-Type", "application/json");
            if (request instanceof HttpPost) {
                ((HttpPost) request).setEntity(new StringEntity(mJsonBody,
                        "UTF-8"));
            } else if (request instanceof HttpPut) {
                ((HttpPut) request).setEntity(new StringEntity(mJsonBody,
                        "UTF-8"));
            }
        }

        return request;
    }

    /**
     * Adds the get params.
     *
     * @return the string
     * @throws UnsupportedEncodingException
     *          Thrown when an unsupported encoding is encountered.
     */
    private String addGetParams() throws UnsupportedEncodingException {
        final StringBuffer combinedParams = new StringBuffer();
        if (!mParams.isEmpty()) {
            combinedParams.append('?');
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
     * @param name the name
     * @param value the value
     */
    public void addHeader(final String name, final String value) {
        mHeaders.add(new BasicNameValuePair(name, value));
    }

    /**
     * Adds the header params.
     *
     * @param request the request
     * @return the http uri request
     * @throws AuthenticationException
     *          Thrown when an auth issue is encountered.
     */
    private HttpUriRequest addHeaderParams(final HttpUriRequest request)
            throws AuthenticationException {
        for (NameValuePair h : mHeaders) {
            request.addHeader(h.getName(), h.getValue());
        }

        if (authentication) {

            final UsernamePasswordCredentials creds = new UsernamePasswordCredentials(
                    mUsername, mPassword);
            request.addHeader(new BasicScheme().authenticate(creds, request));
        }

        return request;
    }

    /**
     * Adds the param.
     *
     * @param name the name
     * @param value the value
     */
    public void addParam(final String name, final String value) {
        mParams.add(new BasicNameValuePair(name, value));
    }

    /**
     * Execute.
     *
     * @param method the method
     * @throws UnsupportedEncodingException
     *          Thrown when unrecognized encoding is used.
     * @throws AuthenticationException
     *          Thrown when an authentication issue is encountered.
     */
    public void execute(final RestMethod method)
            throws UnsupportedEncodingException, AuthenticationException {
        switch (method) {
        case GET:
            final HttpGet getRequest = (HttpGet) addHeaderParams(new HttpGet(
                    mUrl + addGetParams()));
            executeRequest(getRequest);
            break;

        case POST:
            final HttpPost postRequest = (HttpPost) addBodyParams(addHeaderParams(new HttpPost(
                    mUrl)));
            executeRequest(postRequest);
            break;

        case PUT:
            final HttpPut putRequest = (HttpPut) addBodyParams(addHeaderParams(new HttpPut(
                    mUrl)));
            executeRequest(putRequest);
            break;

        case DELETE:
            final HttpDelete deleteRequest = (HttpDelete) addHeaderParams(new HttpDelete(
                    mUrl));
            executeRequest(deleteRequest);

        default:
            break;
        }
    }

    /**
     * Execute request.
     *
     * @param request the request
     */
    private void executeRequest(final HttpUriRequest request) {

        final DefaultHttpClient client = new DefaultHttpClient();
        final HttpParams params = client.getParams();

        // Setting 30 second timeouts
        HttpConnectionParams.setConnectionTimeout(params, THIRTY_SECONDS);
        HttpConnectionParams.setSoTimeout(params, THIRTY_SECONDS);

        HttpResponse httpResponse;

        try {
            httpResponse = client.execute(request);
            mResponseCode = httpResponse.getStatusLine().getStatusCode();
            message = httpResponse.getStatusLine().getReasonPhrase();

            final HttpEntity entity = httpResponse.getEntity();

            if (entity != null) {

                final InputStream instream = entity.getContent();
                mResponse = convertStreamToString(instream);

                // Closing the input stream will trigger connection release
                instream.close();
            }

        } catch (ClientProtocolException e) {
            client.getConnectionManager().shutdown();
            Log.e(TAG, e.getMessage());
        } catch (IOException e) {
            client.getConnectionManager().shutdown();
            Log.e(TAG, e.getMessage());
        }
    }

    /**
     * Gets the error message.
     *
     * @return the error message
     */
    public String getErrorMessage() {
        return message;
    }

    /**
     * Gets the response.
     *
     * @return the response
     */
    public String getResponse() {
        return mResponse;
    }

    /**
     * Gets the response code.
     *
     * @return the response code
     */
    public int getResponseCode() {
        return mResponseCode;
    }

    /**
     * Sets the context.
     *
     * @param ctx the new context
     */
    //    public void setContext(final Context ctx) {
    //        mContext = ctx;
    //    }

    /**
     * Sets the jSON string.
     *
     * @param data the new jSON string
     */
    public void setJSONString(final String data) {
        mJsonBody = data;
    }
}