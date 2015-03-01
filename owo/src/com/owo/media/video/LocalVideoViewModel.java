package com.owo.media.video;

import android.database.Cursor;
import android.provider.MediaStore;

import com.owo.app.common.ContextManager;
import com.owo.base.pattern.Singleton;
import com.owo.base.util.TextHelper;
import com.owo.media.MediaData;
import com.owo.media.MediaViewModel;
import com.owo.media.interfaces.MediaType;

public class LocalVideoViewModel extends MediaViewModel {
	private Cursor mCursor;

	public LocalVideoViewModel() {
		Singleton.of(MediaData.class).addListener(mListener);
	}

	private Cursor query(String searchText) {
		String[] videoColumns = new String[] { MediaStore.Video.Media._ID,//
				MediaStore.Video.Media.DATA, //
				MediaStore.Video.Media.SIZE, //
				MediaStore.Video.Media.TITLE,//
				MediaStore.Video.Media.DURATION,//
				MediaStore.Video.Media.WIDTH,//
				MediaStore.Video.Media.HEIGHT,//
				MediaStore.Video.Media.RESOLUTION,//
				MediaStore.Video.Media.MIME_TYPE };
		if (TextHelper.isEmptyOrSpaces(searchText)) {
			return ContextManager.contentResolver().query(
					MediaStore.Video.Media.EXTERNAL_CONTENT_URI, videoColumns, null, null, null);
		} else {
			String queryString = MediaStore.Video.Media.TITLE + " like '%" + searchText + "%'";
			return ContextManager.contentResolver().query(
					MediaStore.Video.Media.EXTERNAL_CONTENT_URI, videoColumns, queryString, null,
					null);
		}
	}

	@Override
	protected void onAttached(com.owo.media.MediaViewModel.Client client) {
		if (mCursor == null) {
			mCursor = query(null);
		}
		if (mCursor != null) {
			client.onDataChanged(mCursor);
		}
	}

	@Override
	protected void onDettached(com.owo.media.MediaViewModel.Client client) {

	}

	public static interface ClientImpl extends Client {
		void onLevelChanged(int level);
	}

	private MediaData.Listener mListener = new MediaData.Listener() {

		@Override
		public void onTypeChanged(int type) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onSearchTextChanged(String text) {
			if (!MediaType.VIDEO.equals(Singleton.of(MediaData.class).type())) {
				return;
			}
			Cursor cursor = query(text);
			mClient.onDataChanged(cursor);
		}

		@Override
		public void onLevelChanged(int level) {
			// TODO Auto-generated method stub

		}
	};
}
