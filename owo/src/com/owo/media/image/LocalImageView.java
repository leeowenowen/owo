package com.owo.media.image;

import android.content.Context;
import android.database.Cursor;

import com.owo.base.mvc.interfaces.IMessageHandler;
import com.owo.media.MediaCursorAdapter;
import com.owo.media.base.MediaListView;
import com.owo.media.interfaces.ScaleLevel;

public class LocalImageView extends MediaListView {
	private LocalImageViewModel mViewModel;
	private MediaCursorAdapter mAdapter;

	public LocalImageView(Context context, IMessageHandler messageHandler) {
		super(context, messageHandler);

		mAdapter = new MediaCursorAdapter();
		mAdapter.setCursorTransformer(ImageCursorTransformerFactory.create(ScaleLevel.sLevel0));

		mViewModel = new LocalImageViewModel();
		mViewModel.attach(mClient);

		setAdapter(mAdapter);
	}

	private LocalImageViewModel.ClientImpl mClient = new LocalImageViewModel.ClientImpl() {

		@Override
		public void onDataChanged(Cursor cursor) {
			mAdapter.setCursor(cursor);
		}

		@Override
		public void onLevelChanged(int level) {
		}
	};
}
