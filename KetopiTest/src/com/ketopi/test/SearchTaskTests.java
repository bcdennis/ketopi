/**
 * 
 */
package com.ketopi.test;

import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;

import junit.framework.TestCase;
import android.os.Looper;

import com.ketopi.core.ISearchListener;
import com.ketopi.core.SearchRequest;
import com.ketopi.core.SearchResult;
import com.ketopi.core.SearchTask;
import com.ketopi.rest.RestException;

/**
 * @author Brad Dennis
 *
 */
public class SearchTaskTests extends TestCase {

    private class LooperThread extends Thread {

        public LooperThread() {
        }

        @Override
        public void start() {
            new Thread() {
                @Override
                public void run() {
                    Looper.prepare();
                    LooperThread.this.run();
                    Looper.loop();
                }
            }.start();

        }
    }

    private SearchRequest mRequest;
    private String mResponse;

    /**
     * @param name
     */
    public SearchTaskTests(final String name) {
        super(name);
    }

    /* (non-Javadoc)
     * @see junit.framework.TestCase#setUp()
     */
    @Override
    protected void setUp() throws Exception {
        super.setUp();

        mRequest = new SearchRequest();
        mRequest.setQuery("DIGIORNO");
        mRequest.setUrl("http://www.ketopi.com/api/search.json");
        mResponse =  "{\"query\":\"DIGIORNO\",\"results\":[{\"ndb_no\":\"21474\",\"long_desc\":\"DIGIORNO Pizza, cheese topping, rising crust, frozen, baked\",\"carbs\":\"32\",\"calories\":\"256\",\"fat\":\"9\",\"protein\":\"13\",\"fiber\":\"2\",\"sugars\":\"5\",\"net_carbs\":\"30\",\"amount\":\"1\",\"measure\":\"slice 1\\/4 of pie\",\"grams\":\"183\",\"rank\":\"6.34481477737427\"}]}";


    }

    /* (non-Javadoc)
     * @see junit.framework.TestCase#tearDown()
     */
    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    /**
     * Test method for {@link com.ketopi.core.SearchTask#execute(com.ketopi.core.SearchRequest)}.
     * @throws InterruptedException
     */
    public final void testExecute() throws InterruptedException {

        final CountDownLatch latch = new CountDownLatch(1);

        // This thread is the "fake" UI thread
        new LooperThread() {
            @Override
            public void run() {

                new SearchTask(new ISearchListener<SearchResult>() {

                    public void onSearchFinish(final SearchResult result) {
                        assertTrue(result.getQuery().equals(mRequest.getQuery()));
                        assertTrue(result.getResponse().equals(mResponse));

                        latch.countDown();
                    }

                    public void onSearchStart() {

                    }
                }).execute(mRequest);

            }
        }.start();

        latch.await();
    }

    /**
     * Test method for {@link com.ketopi.core.SearchTask#execute(com.ketopi.core.SearchRequest)}.
     * @throws InterruptedException
     */
    public final void testExecuteEmptyResult() throws InterruptedException {

        mRequest.setQuery("DIGORNIO");
        mResponse = "{\"query\":\"DIGORNIO\",\"results\":[]}";

        final CountDownLatch latch = new CountDownLatch(1);

        // This thread is the "fake" UI thread
        new LooperThread() {
            @Override
            public void run() {

                new SearchTask(new ISearchListener<SearchResult>() {

                    public void onSearchFinish(final SearchResult result) {

                        assertTrue(result.getQuery().equals(mRequest.getQuery()));
                        assertTrue(result.getResponse().equals(mResponse));

                        latch.countDown();
                    }

                    public void onSearchStart() {

                    }
                }).execute(mRequest);

            }
        }.start();

        latch.await();
    }

    /**
     * Test method for {@link com.ketopi.core.SearchTask#execute(com.ketopi.core.SearchRequest)}.
     * @throws InterruptedException
     */
    public final void testExecuteShould403() throws InterruptedException {

        mRequest.setUrl("http://www.ketopi.com/api_test/search_403.json");
        final CountDownLatch latch = new CountDownLatch(1);

        // This thread is the "fake" UI thread
        new LooperThread() {
            @Override
            public void run() {

                new SearchTask(new ISearchListener<SearchResult>() {

                    public void onSearchFinish(final SearchResult result) {
                        ArrayList<Exception> exceptions = (ArrayList<Exception>) result.getExceptions();
                        RestException exception = (RestException) exceptions.get(0);

                        assertTrue(exceptions.size() == 1);
                        assertTrue(exception.getMessage().equals("Forbidden"));

                        latch.countDown();
                    }

                    public void onSearchStart() {

                    }
                }).execute(mRequest);

            }
        }.start();

        latch.await();
    }

    /**
     * Test method for {@link com.ketopi.core.SearchTask#execute(com.ketopi.core.SearchRequest)}.
     * @throws InterruptedException
     */
    public final void testExecuteShould404() throws InterruptedException {

        mRequest.setUrl("http://www.ketopi.com/api_test/search_404.json");
        final CountDownLatch latch = new CountDownLatch(1);

        // This thread is the "fake" UI thread
        new LooperThread() {
            @Override
            public void run() {

                new SearchTask(new ISearchListener<SearchResult>() {

                    public void onSearchFinish(final SearchResult result) {
                        ArrayList<Exception> exceptions = (ArrayList<Exception>) result.getExceptions();
                        RestException exception = (RestException) exceptions.get(0);

                        assertTrue(exceptions.size() == 1);
                        assertTrue(exception.getMessage().equals("Not Found"));

                        latch.countDown();
                    }

                    public void onSearchStart() {

                    }
                }).execute(mRequest);

            }
        }.start();

        latch.await();
    }
    /**
     * Test method for {@link com.ketopi.core.SearchTask#execute(com.ketopi.core.SearchRequest)}.
     * @throws InterruptedException
     */
    public final void testExecuteShouldUrlEncodingException() throws InterruptedException {

        mRequest.setUrl("http://www.ketopi.com/api_test/search_encoding.json");
        mRequest.setEncoding("ASMX-708");
        final CountDownLatch latch = new CountDownLatch(1);

        // This thread is the "fake" UI thread
        new LooperThread() {
            @Override
            public void run() {

                new SearchTask(new ISearchListener<SearchResult>() {

                    public void onSearchFinish(final SearchResult result) {
                        ArrayList<Exception> exceptions = (ArrayList<Exception>) result.getExceptions();
                        RestException exception = (RestException) exceptions.get(0);

                        assertTrue(exceptions.size() == 1);
                        assertTrue(exception.getMessage().equals("Unsupported Encoding"));

                        latch.countDown();
                    }

                    public void onSearchStart() {

                    }
                }).execute(mRequest);

            }
        }.start();

        latch.await();
    }
}

