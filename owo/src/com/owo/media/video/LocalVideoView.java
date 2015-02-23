package com.owo.media.video;

import android.content.Context;
import android.database.Cursor;
import android.widget.ListView;

import com.owo.media.MediaCursorAdapter;
import com.owo.media.interfaces.ScaleLevel;

public class LocalVideoView extends ListView {
	private LocalVideoViewModel mViewModel;
	private MediaCursorAdapter mAdapter;

	public LocalVideoView(Context context) {
		super(context);

		mAdapter = new MediaCursorAdapter();
		mAdapter.setCursorTransformer(VideoCursorTransformerFactory.create(ScaleLevel.sLevel0));

		mViewModel = new LocalVideoViewModel();
		mViewModel.attach(mClient);

		setAdapter(mAdapter);
	}

	private LocalVideoViewModel.ClientImpl mClient = new LocalVideoViewModel.ClientImpl() {

		@Override
		public void onDataChanged(Cursor cursor) {
			mAdapter.setCursor(cursor);
		}

		@Override
		public void onLevelChanged(int level) {
		}
	};
}
