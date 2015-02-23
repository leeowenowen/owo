package com.owo.media.interfaces;

import android.widget.AbsListView;

public interface IMediaStoreViewModel {
	void setClient(Client client);

	public static interface Client {
		void onAdapterViewChanged(AbsListView adapterView);
	}
}
