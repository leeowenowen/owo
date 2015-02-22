package com.owo.mediastore;

import android.content.ContentResolver;
import android.database.ContentObserver;
import android.database.Cursor;
import android.database.DataSetObserver;

import com.owo.base.util.BaseHandler;
import com.owo.mediastore.interfaces.IMediaQueryer;

public abstract class ContentMediaQueryer implements IMediaQueryer {
	protected final ContentResolver mContentResolver;
	protected ContentObserver mContentObserver;

	protected ContentMediaQueryer(ContentResolver contentResolver) {
		mContentResolver = contentResolver;
	}

	@Override
	public Cursor query(final DataSetObserver observer) {
		mContentObserver = new ContentObserver(BaseHandler.get()) {
			@Override
			public void onChange(boolean selfChange) {
				observer.onChanged();
			}
		};
		return queryImpl();
	}

	protected abstract Cursor queryImpl();
}
