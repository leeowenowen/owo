package com.owo.media;

import android.content.Context;
import android.widget.AbsListView;
import android.widget.GridView;

import com.owo.media.interfaces.IListViewFactory;
import com.owo.mediaplayer.interfaces.ListViewConfig;

public class GridViewMaker implements IListViewFactory {

	@Override
	public AbsListView create(Context context, ListViewConfig config) {
		GridView gridView = new GridView(context);
		gridView.setNumColumns(3);
		gridView.setHorizontalSpacing(0);
		gridView.setVerticalSpacing(0);
		return gridView;
	}

}
