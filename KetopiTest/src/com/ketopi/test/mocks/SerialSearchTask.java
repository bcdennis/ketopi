package com.ketopi.test.mocks;

import com.ketopi.core.ISearchListener;
import com.ketopi.core.SearchRequest;
import com.ketopi.core.SearchResult;
import com.ketopi.core.SearchTask;

public class SerialSearchTask extends SearchTask {




    public SerialSearchTask(final ISearchListener<SearchResult> listener) {
        super(listener);

    }

    @Override
    public void execute(final SearchRequest request) {
        getListener().onSearchStart();
        SearchResult result = fetch(request);
        getListener().onSearchFinish(result);

    }

}
