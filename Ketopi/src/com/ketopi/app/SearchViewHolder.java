package com.ketopi.app;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * The Class SearchViewHolder.
 *
 * @author Brad Dennis
 */
public final class SearchViewHolder {

    /**
     * Gets the cached inflated view..
     *
     * @param convertView the view to convert.
     * @param parent the parent view.
     * @return the static reference to the search view holder
     */
    public static SearchViewHolder get(final View convertView,
            final ViewGroup parent) {
        if (convertView == null) {
            return new SearchViewHolder(parent);
        }
        return (SearchViewHolder) convertView.getTag();
    }

    // CHECKSTYLE.OFF: Visibility - Explicit use of the ViewHolder Pattern
    /** The reference to the view. */
    public final View root;

    /** The food description. */
    public TextView foodDescription;

    /** The food serving. */
    public TextView foodServing;

    /** The food net Carbohydratess. */
    public TextView foodNetCarbs;

    // CHECKSTYLE.ON: Visibility

    /**
     * Instantiates a new search view holder.
     *
     * @param parent the parent view
     */
    private SearchViewHolder(final ViewGroup parent) {
        root = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.search_list_item, parent, false);
        root.setTag(this);

        foodDescription = (TextView) root.findViewById(R.id.foodDescription);
        foodServing = (TextView) root.findViewById(R.id.foodServing);
        foodNetCarbs = (TextView) root.findViewById(R.id.foodNetCarbs);
    }
}
