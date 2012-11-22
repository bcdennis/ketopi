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
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import android.util.Log;

import com.ketopi.core.NoOp;

/**
 * The Class RestClient.
 */
public class RestClient {

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
            Log.e(TAG, "I/O Exception");
        } finally {
            try {
                stream.close();
            } catch (IOException e) {
                Log.e(TAG, "I/O Exception");
            }
        }

        return buffer.toString().trim();
    }

    /** The headers. */
    private final List<NameValuePair> mHeaders;

    /** The message. */
    private String message;

    /** The request parameters. */
    private final List<NameValuePair> mParams;

    /** The response. */
    private String mResponse;

    /** The response code. */
    private int mResponseCode;

    /** The URL. */
    private final String mUrl;


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
     */
    private HttpUriRequest addHeaderParams(final HttpUriRequest request) {
        for (NameValuePair h : mHeaders) {
            request.addHeader(h.getName(), h.getValue());
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
     * @param client The HTTP Client to use.
     * @throws UnsupportedEncodingException
     *          Thrown when unrecognized encoding is used.
     */
    public void execute(final DefaultHttpClient client)
            throws UnsupportedEncodingException {
        final HttpGet getRequest = (HttpGet) addHeaderParams(new HttpGet(
                mUrl + addGetParams()));
        executeRequest(client, getRequest);
    }

    /**
     * Execute request.
     *
     * @param client the HTTP Client to use.
     * @param request the request
     */
    private void executeRequest(final DefaultHttpClient client, final HttpUriRequest request) {

        HttpResponse httpResponse;

        try {
            httpResponse = client.execute(request);
            mResponseCode = httpResponse.getStatusLine().getStatusCode();
            message = httpResponse.getStatusLine().getReasonPhrase();

            final HttpEntity entity = httpResponse.getEntity();

            mResponse = convertStreamToString(entity.getContent());

            // repair JSON
            if (mResponse.startsWith("{")) {
                mResponse += "}";
            } else {
                NoOp.getInstance().run();
            }


        } catch (ClientProtocolException e) {
            Log.e(TAG, e.getCause().getMessage());
        } catch (IOException e) {
            Log.e(TAG, e.getMessage());
        } finally {
            client.getConnectionManager().shutdown();
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

}