package com.owo.media.interfaces;

import android.content.Context;
import android.widget.AbsListView;

public interface IListViewFactory {
	AbsListView create(int type, Context context);
}
