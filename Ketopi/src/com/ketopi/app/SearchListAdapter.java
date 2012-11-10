package com.ketopi.app;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

/**
 * The Class SearchListAdapter.
 */
public class SearchListAdapter extends ArrayAdapter<Food> {

    /**
     * The Class ViewHolder.
     */
    static class ViewHolder {

        /** The food description. */
        // CHECKSTYLE.OFF: Visibility - Explicit use of the Holder Pattern
        public TextView foodDescription;

        /** The food serving. */
        public TextView foodServing;

        /** The food net Carbohydratess. */
        public TextView foodNetCarbs;
        // CHECKSTYLE.ON: Visibility
    }


    /** The Foods. */
    private final Food[] mFoods;

    /** The Context. */
    private final Activity mContext;

    /**
     * Instantiates a new search list adapter.
     *
     * @param context the context
     * @param foods the foods
     */
    public SearchListAdapter(final Activity context, final Food[] foods) {
        super(context, R.layout.search_list_item, foods);

        mContext = context;
        mFoods = foods.clone();
    }

    /* (non-Javadoc)
     * @see android.widget.ArrayAdapter#getView(int, android.view.View, android.view.ViewGroup)
     */
    @Override
    public View getView(final int position, final View convertView, final ViewGroup parent) {
        View rowView = convertView;
        if (rowView == null) {
            final LayoutInflater inflater = mContext.getLayoutInflater();
            rowView = inflater.inflate(R.layout.search_list_item, null);

            final ViewHolder viewHolder = new ViewHolder();
            viewHolder.foodDescription = (TextView) rowView
                    .findViewById(R.id.foodDescription);
            viewHolder.foodServing = (TextView) rowView
                    .findViewById(R.id.foodServing);
            viewHolder.foodNetCarbs = (TextView) rowView
                    .findViewById(R.id.foodNetCarbs);
            rowView.setTag(viewHolder);
        }

        final ViewHolder holder = (ViewHolder) rowView.getTag();
        final Food item = mFoods[position];

        holder.foodDescription.setText(item.getDescription());
        holder.foodServing.setText(item.getServing());
        holder.foodNetCarbs.setText(item.getNetCarbs());

        return rowView;
    }
}
