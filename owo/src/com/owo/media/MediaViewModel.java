package com.owo.media;

import android.database.Cursor;

public abstract class MediaViewModel {
	public static interface Client {
		void onDataChanged(Cursor cursor);
	}

	protected Client mClient;

	@SuppressWarnings("unchecked")
	protected <Impl> Impl clientImpl() {
		return (Impl) mClient;
	}

	public void attach(Client client) {
		mClient = client;
		onAttached(client);
	}

	public void dettach(Client client) {
		mClient = null;
		onDettached(client);
	}

	protected abstract void onAttached(Client client);

	protected abstract void onDettached(Client client);
}
