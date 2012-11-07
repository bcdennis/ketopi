package com.ketopi.app;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class SearchListAdapter extends ArrayAdapter<Food> {

	static class ViewHolder {
		public TextView foodDescription;
		public TextView foodServing;
		public TextView foodNetCarbs;
	}

	private final Food[] mFoods;
	private final Activity mContext;

	public SearchListAdapter(final Activity context, final Food[] foods) {
		super(context, R.layout.search_list_item, foods);

		mContext = context;
		mFoods = foods;
	}

	@Override
	public View getView(final int position, final View convertView, final ViewGroup parent) {
		View rowView = convertView;
		if (rowView == null) {
			LayoutInflater inflater = mContext.getLayoutInflater();
			rowView = inflater.inflate(R.layout.search_list_item, null);

			ViewHolder viewHolder = new ViewHolder();
			viewHolder.foodDescription = (TextView) rowView
					.findViewById(R.id.foodDescription);
			viewHolder.foodServing = (TextView) rowView
					.findViewById(R.id.foodServing);
			viewHolder.foodNetCarbs = (TextView) rowView
					.findViewById(R.id.foodNetCarbs);
			rowView.setTag(viewHolder);
		}

		ViewHolder holder = (ViewHolder) rowView.getTag();
		Food item = mFoods[position];

		holder.foodDescription.setText(item.getDescription());
		holder.foodServing.setText(item.getServing());
		holder.foodNetCarbs.setText(item.getNetCarbs());

		return rowView;
	}
}
