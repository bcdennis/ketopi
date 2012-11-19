package com.ketopi.app;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.ketopi.core.Food;

/**
 * The Class SearchListAdapter.
 */
public class SearchListAdapter extends ArrayAdapter<Food> {

    //    private static final String TAG = "Ketopi";

    /** The Foods. */
    private final Food[] mFoods;

    /**
     * Instantiates a new search list adapter.
     *
     * @param context the context
     * @param foods the foods
     */
    public SearchListAdapter(final Activity context, final Food[] foods) {
        super(context, R.layout.search_list_item, foods);

        mFoods = foods.clone();
    }


    /* (non-Javadoc)
     * @see android.widget.ArrayAdapter#getView(int, android.view.View, android.view.ViewGroup)
     */
    @Override
    public View getView(final int position, final View convertView, final ViewGroup parent) {
        SearchViewHolder viewHolder = SearchViewHolder.get(convertView, parent);
        final Food item = mFoods[position];

        viewHolder.foodDescription.setText(item.getDescription());
        viewHolder.foodServing.setText(item.getServing());
        viewHolder.foodNetCarbs.setText(item.getNetCarbs());


        return viewHolder.root;
    }
}
