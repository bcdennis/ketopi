/**
 * 
 */
package com.ketopi.test;

import java.io.UnsupportedEncodingException;

import junit.framework.TestCase;

import org.apache.http.impl.client.DefaultHttpClient;

import com.ketopi.rest.RestClient;
import com.ketopi.test.mocks.ClientProtocolExceptionClient;
import com.ketopi.test.mocks.StreamIOExceptionClient;
import com.ketopi.test.mocks.StreamIOExceptionClient2;

/**
 * @author brad
 *
 */
public class RestClientTests extends TestCase {

    private RestClient mClient;

    /**
     * @param name
     */
    public RestClientTests(final String name) {
        super(name);
    }

    /* (non-Javadoc)
     * @see junit.framework.TestCase#setUp()
     */
    @Override
    protected void setUp() throws Exception {
        super.setUp();

        mClient = new RestClient("http://www.ketopi.com/api_test/search_mirror.json");
    }

    /* (non-Javadoc)
     * @see junit.framework.TestCase#tearDown()
     */
    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    /**
     * Test method for {@link com.ketopi.rest.RestClient#addHeader(java.lang.String, java.lang.String)}.
     * @throws UnsupportedEncodingException
     */
    public final void testAddHeader() throws UnsupportedEncodingException {
        mClient = new RestClient("http://www.ketopi.com/api_test/echo_headers.json");
        mClient.addHeader("Accept-Language", "en-US");
        mClient.addHeader("Accept-Charset", "utf-8");

        mClient.execute(new DefaultHttpClient());

        String response = mClient.getResponse();
        assertTrue(response.matches(".*en-US.*"));
        assertTrue(response.matches(".*utf-8.*"));
        assertTrue(mClient.getResponseCode() == 200);

    }

    /**
     * Test method for {@link com.ketopi.rest.RestClient#addParam(java.lang.String, java.lang.String)}.
     * @throws UnsupportedEncodingException
     */
    public final void testAddMultipleParam() throws UnsupportedEncodingException {
        mClient = new RestClient("http://www.ketopi.com/api_test/echo_params.json");
        mClient.addParam("param0", "test-query");
        mClient.addParam("param1", "test1-query1");
        mClient.addParam("param2", "test2-query2");

        mClient.execute(new DefaultHttpClient());

        String response = mClient.getResponse();
        assertTrue(response.matches(".*test-query.*"));
        assertTrue(response.matches(".*test1-query1.*"));
        assertTrue(response.matches(".*test2-query2.*"));
        assertTrue(mClient.getResponseCode() == 200);
    }

    /**
     * Test method for {@link com.ketopi.rest.RestClient#addParam(java.lang.String, java.lang.String)}.
     * @throws UnsupportedEncodingException
     */
    public final void testAddParam() throws UnsupportedEncodingException {
        mClient = new RestClient("http://www.ketopi.com/api_test/echo_params.json");
        mClient.addParam("param0", "test-query");

        mClient.execute(new DefaultHttpClient());

        String response = mClient.getResponse();
        assertTrue(response.matches(".*test-query.*"));
        assertTrue(mClient.getResponseCode() == 200);
    }

    /**
     * Test method for {@link com.ketopi.rest.RestClient#getResponseCode()}.
     * @throws UnsupportedEncodingException
     */
    public final void testClientShouldIOException() throws UnsupportedEncodingException {
        mClient = new RestClient("https://www.ketopi.com/api_test/search_emptyresults.json");
        mClient.addParam("query", "test-query");

        mClient.execute(new DefaultHttpClient());

        assertTrue(mClient.getResponseCode() == 0);
    }

    /**
     * Test method for {@link com.ketopi.rest.RestClient#getErrorMessage()}.
     * @throws UnsupportedEncodingException
     */
    public final void testGetErrorMessage() throws UnsupportedEncodingException {
        mClient = new RestClient("http://www.ketopi.com/api_test/search_401.json");
        mClient.addParam("query", "test-query");

        mClient.execute(new DefaultHttpClient());

        assertTrue(mClient.getResponseCode() == 401);
    }

    /**
     * Test method for {@link com.ketopi.rest.RestClient#getResponse()}.
     * @throws UnsupportedEncodingException
     */
    public final void testGetResponse() throws UnsupportedEncodingException {
        mClient = new RestClient("http://www.ketopi.com/api_test/search_emptyresults.json");
        mClient.addParam("query", "test-query");

        mClient.execute(new DefaultHttpClient());

        assertTrue(mClient.getResponse().equals("{\"query\":\"test-query\",\"results\":\"\"}"));
        assertTrue(mClient.getResponseCode() == 200);
    }

    /**
     * Test method for {@link com.ketopi.rest.RestClient#getResponseCode()}.
     * @throws UnsupportedEncodingException
     */
    public final void testGetResponseCode() throws UnsupportedEncodingException {
        mClient = new RestClient("http://www.ketopi.com/api_test/search_emptyresults.json");
        mClient.addParam("query", "test-query");

        mClient.execute(new DefaultHttpClient());

        assertTrue(mClient.getResponseCode() == 200);
    }

    /**
     * Test method for {@link com.ketopi.rest.RestClient#getResponseCode()}.
     * @throws UnsupportedEncodingException
     */
    public final void testShouldHandleClientProtocolException() throws UnsupportedEncodingException {
        mClient = new RestClient("http://www.ketopi.com/api_test/error_500.json");
        mClient.addParam("query", "test-query");

        mClient.execute(new ClientProtocolExceptionClient());

        assertTrue(mClient.getResponseCode() == 0);
    }


    /**
     * Test method for {@link com.ketopi.rest.RestClient#getResponseCode()}.
     * @throws UnsupportedEncodingException
     */
    public final void testStreamShouldIOException() throws UnsupportedEncodingException {
        mClient = new RestClient("https://www.ketopi.com/api_test/search_emptyresults.json");
        mClient.addParam("query", "test-query");

        mClient.execute(new StreamIOExceptionClient());

        assertTrue(mClient.getResponseCode() == 0);
    }


    /**
     * Test method for {@link com.ketopi.rest.RestClient#getResponseCode()}.
     * @throws UnsupportedEncodingException
     */
    public final void testStreamShouldIOExceptionOnClose() throws UnsupportedEncodingException {
        mClient = new RestClient("https://www.ketopi.com/api_test/search_emptyresults.json");
        mClient.addParam("query", "test-query");

        mClient.execute(new StreamIOExceptionClient2());

        assertTrue(mClient.getResponseCode() == 0);
    }
}
