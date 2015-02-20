package com.owo.mediastore.interfaces;

import android.content.Context;
import android.widget.AbsListView;

public interface IListViewFactory {
	AbsListView create(int type, Context context);
}
