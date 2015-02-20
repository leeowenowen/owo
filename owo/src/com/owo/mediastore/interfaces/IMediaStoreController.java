package com.owo.mediastore.interfaces;

import android.content.Context;
import android.widget.AbsListView;

public interface IMediaStoreController {
	AbsListView createListView(Context context);

	void setClient(Client client);

	public static interface Client {
		void onAdapterViewChanged(AbsListView adapterView);
	}
}
