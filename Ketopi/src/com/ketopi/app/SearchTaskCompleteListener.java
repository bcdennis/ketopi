package com.ketopi.app;

/**
 * @author brad
 *
 */
public interface SearchTaskCompleteListener<T, T1> {

	public void onTaskComplete(T query, T1 results);

}
