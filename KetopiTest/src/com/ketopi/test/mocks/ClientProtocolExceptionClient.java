/**
 * 
 */
package com.ketopi.test.mocks;

import java.io.IOException;

import org.apache.http.ConnectionReuseStrategy;
import org.apache.http.HttpException;
import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.client.AuthenticationHandler;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.RedirectHandler;
import org.apache.http.client.UserTokenHandler;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.ConnectionKeepAliveStrategy;
import org.apache.http.conn.routing.HttpRoutePlanner;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.DefaultRequestDirector;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HttpContext;
import org.apache.http.protocol.HttpProcessor;
import org.apache.http.protocol.HttpRequestExecutor;


/**
 * @author brad
 *
 */
public class ClientProtocolExceptionClient extends DefaultHttpClient {

    private class RequestDirector extends DefaultRequestDirector {

        public RequestDirector(final HttpRequestExecutor requestExec,
                final ClientConnectionManager conman,
                final ConnectionReuseStrategy reustrat,
                final ConnectionKeepAliveStrategy kastrat, final HttpRoutePlanner rouplan,
                final HttpProcessor httpProcessor,
                final HttpRequestRetryHandler retryHandler,
                final RedirectHandler redirectHandler,
                final AuthenticationHandler targetAuthHandler,
                final AuthenticationHandler proxyAuthHandler,
                final UserTokenHandler userTokenHandler, final HttpParams params) {
            super(requestExec, conman, reustrat, kastrat, rouplan, httpProcessor,
                    retryHandler, redirectHandler, targetAuthHandler, proxyAuthHandler,
                    userTokenHandler, params);
        }

        @Override
        public HttpResponse execute(final HttpHost target, final HttpRequest request,
                final HttpContext context)
                        throws HttpException, IOException {

            throw new HttpException("Mock HttpException");

        }

    }

    @Override
    protected RequestDirector createClientRequestDirector(
            final HttpRequestExecutor requestExec,
            final ClientConnectionManager conman,
            final ConnectionReuseStrategy reustrat,
            final ConnectionKeepAliveStrategy kastrat,
            final HttpRoutePlanner rouplan,
            final HttpProcessor httpProcessor,
            final HttpRequestRetryHandler retryHandler,
            final RedirectHandler redirectHandler,
            final AuthenticationHandler targetAuthHandler,
            final AuthenticationHandler proxyAuthHandler,
            final UserTokenHandler stateHandler,
            final HttpParams params) {
        return new RequestDirector(
                requestExec,
                conman,
                reustrat,
                kastrat,
                rouplan,
                httpProcessor,
                retryHandler,
                redirectHandler,
                targetAuthHandler,
                proxyAuthHandler,
                stateHandler,
                params);
    }


}
