package com.owo.media.interfaces;

import com.owo.mediaplayer.interfaces.ListViewConfig;

import android.content.Context;
import android.widget.AbsListView;

public interface IListViewFactory {
	AbsListView create(Context context, ListViewConfig config);
}
