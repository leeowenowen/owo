package com.owo.media.image;

import android.database.Cursor;
import android.provider.MediaStore;

import com.owo.app.ContextManager;
import com.owo.base.pattern.Instance;
import com.owo.base.util.TextHelper;
import com.owo.media.MediaStoreData;
import com.owo.media.MediaViewModel;

public class LocalImageViewModel extends MediaViewModel {
	private Cursor mCursor;

	public LocalImageViewModel() {
		Instance.of(MediaStoreData.class).addListener(mListener);
	}

	private Cursor query(String searchText) {
		String[] imageColumns = new String[] { MediaStore.Images.Media._ID,//
				MediaStore.Images.Media.DATA, //
				MediaStore.Images.Media.SIZE, //
				MediaStore.Images.Media.TITLE,//
				MediaStore.Images.Media.WIDTH,//
				MediaStore.Images.Media.HEIGHT,//
				MediaStore.Images.Media.MIME_TYPE };
		if (TextHelper.isEmptyOrSpaces(searchText)) {
			return ContextManager.contentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, imageColumns, null, null, null);
		} else {
			return ContextManager.contentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, imageColumns, "title like %?s%", new String[] { searchText }, null);
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