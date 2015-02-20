package com.owo.mediastore;

import android.content.Context;
import android.widget.AbsListView;
import android.widget.GridView;
import android.widget.ListView;

import com.owo.mediastore.interfaces.IListViewFactory;
import com.owo.mediastore.interfaces.ListViewType;

public class DefaultListViewFactory implements IListViewFactory {

	@Override
	public AbsListView create(int type, Context context) {
		switch (type) {
		case ListViewType.sListView:
			return new ListView(context);
		case ListViewType.sGridView:
			return new GridView(context);
		default:
			return null;
		}
	}

}
