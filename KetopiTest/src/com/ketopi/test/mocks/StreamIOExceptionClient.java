/**
 * 
 */
package com.ketopi.test.mocks;

import java.io.IOException;
import java.io.InputStream;

import org.apache.http.ConnectionReuseStrategy;
import org.apache.http.HttpEntity;
import org.apache.http.HttpException;
import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.ProtocolVersion;
import org.apache.http.client.AuthenticationHandler;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.RedirectHandler;
import org.apache.http.client.UserTokenHandler;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.ConnectionKeepAliveStrategy;
import org.apache.http.conn.routing.HttpRoutePlanner;
import org.apache.http.entity.BasicHttpEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.DefaultRequestDirector;
import org.apache.http.message.BasicHttpResponse;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HttpContext;
import org.apache.http.protocol.HttpProcessor;
import org.apache.http.protocol.HttpRequestExecutor;


/**
 * @author brad
 *
 */
public class StreamIOExceptionClient extends DefaultHttpClient {

    private class IOExceptionStream extends InputStream {

        /* (non-Javadoc)
         * @see java.io.InputStream#close()
         */
        //        @Override
        //        public void close() throws IOException {
        //            throw new IOException();
        //        }

        @Override
        public int read() throws IOException {
            throw new IOException();
        }

    }

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

            return new StreamIOExceptionResponse();

        }

    }

    private class StreamIOExceptionEntity extends BasicHttpEntity {

        /* (non-Javadoc)
         * @see org.apache.http.entity.BasicHttpEntity#getContent()
         */
        @Override
        public InputStream getContent() throws IllegalStateException {

            return new IOExceptionStream();
        }

    }

    private class StreamIOExceptionResponse extends BasicHttpResponse {

        public StreamIOExceptionResponse() {
            super(new ProtocolVersion("HTTP", 1, 1), 0, "");
        }

        public StreamIOExceptionResponse(final ProtocolVersion ver, final int code,
                final String reason) {
            super(ver, code, reason);

        }

        /* (non-Javadoc)
         * @see org.apache.http.message.BasicHttpResponse#getEntity()
         */
        @Override
        public HttpEntity getEntity() {

            return new StreamIOExceptionEntity();
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
