package com.ketopi.app;

/**
 * @author Brad Dennis
 * @param <T>
 * @param <T1>
 */
public interface ISearchTaskCompleteListener<T, T1> {

    /**
     * @param query
     *            the original query
     * @param results
     *            the results set
     */
    void onTaskComplete(T query, T1 results);
}
