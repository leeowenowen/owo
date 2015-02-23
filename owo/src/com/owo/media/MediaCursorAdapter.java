package com.owo.media;

import com.owo.media.interfaces.ICursorTransformer;

import android.database.Cursor;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class MediaCursorAdapter extends BaseAdapter {
	private Cursor mCursor;
	private ICursorTransformer mTransformer;

	public MediaCursorAdapter() {
	}

	public void setCursorTransformer(ICursorTransformer transformer) {
		mTransformer = transformer;
	}

	public void setCursor(Cursor cursor) {
		mCursor = cursor;
		notifyDataSetChanged();
	}

	@Override
	public int getCount() {
		return mCursor.getCount();
	}

	@Override
	public Object getItem(int position) {
		mCursor.moveToPosition(position);
		return mCursor;
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		mCursor.moveToPosition(position);
		return mTransformer.transform(convertView, mCursor);
	}
}
