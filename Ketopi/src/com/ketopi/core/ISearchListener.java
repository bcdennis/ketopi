package com.ketopi.core;


/**
 * The listener interface for receiving search events.
 * The class that is interested in processing a search
 * event implements this interface, and the object created
 * with that class is registered with a component using the
 * component's <code>addSearchListener</code> method. When
 * the search event occurs, that object's appropriate
 * method is invoked.
 *
 * @param <T> the generic type
 * @see SearchEvent
 */
public interface ISearchListener<T> {

    /**
     * Called when the search is completed.
     *
     * @param result the search results
     */
    public void onSearchFinish(T result);

    /**
     * Called before the search is executed.
     */
    public void onSearchStart();
}
