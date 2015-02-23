package com.owo.media.video;

import android.database.Cursor;
import android.provider.MediaStore;

import com.owo.app.common.ContextManager;
import com.owo.base.pattern.Instance;
import com.owo.base.util.TextHelper;
import com.owo.media.MediaStoreData;
import com.owo.media.MediaViewModel;

public class LocalVideoViewModel extends MediaViewModel {
	private Cursor mCursor;

	public LocalVideoViewModel() {
		Instance.of(MediaStoreData.class).addListener(mListener);
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
			return ContextManager.contentResolver().query(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, videoColumns, null, null, null);
		} else {
			return ContextManager.contentResolver().query(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, videoColumns, "title like %?s%", new String[] { searchText }, null);
		}
	}

	@Override
	protected void onAttached(com.owo.media.MediaViewModel.Client client) {
		if (mCursor == null) {
			mCursor = query(null);
		}
		client.onDataChanged(mCursor);
	}

	@Override
	protected void onDettached(com.owo.media.MediaViewModel.Client client) {

	}

	public static interface ClientImpl extends Client {
		void onLevelChanged(int level);
	}

	private MediaStoreData.Listener mListener = new MediaStoreData.Listener() {

		@Override
		public void onTypeChanged(int type) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onSearchTextChanged(String text) {
			Cursor cursor = query(text);
			mClient.onDataChanged(cursor);
		}

		@Override
		public void onLevelChanged(int level) {
			// TODO Auto-generated method stub

		}
	};
}
