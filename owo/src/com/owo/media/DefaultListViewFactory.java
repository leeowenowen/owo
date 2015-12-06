package com.owo.media;

import android.content.Context;
import android.widget.AbsListView;
import android.widget.ListView;

import com.owo.media.interfaces.IListViewFactory;
import com.owo.media.interfaces.ListViewType;
import com.owo.mediaplayer.interfaces.ListViewConfig;

public class DefaultListViewFactory implements IListViewFactory {

	@Override
	public AbsListView create(Context context, ListViewConfig config) {
		switch (config.getType()) {
		case ListViewType.sAndroidGridView:
			return new GridViewMaker().create(context, config);
		case ListViewType.sAndroidListView:
			return new ListView(context);
		default:
			return null;
		}

	}

}
