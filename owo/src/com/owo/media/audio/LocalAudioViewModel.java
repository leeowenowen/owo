package com.owo.media.audio;

import android.database.Cursor;
import android.provider.MediaStore;

import com.owo.app.common.ContextManager;
import com.owo.base.pattern.Singleton;
import com.owo.base.util.TextHelper;
import com.owo.media.MediaData;
import com.owo.media.MediaViewModel;
import com.owo.media.interfaces.MediaType;

public class LocalAudioViewModel extends MediaViewModel {
	private Cursor mCursor;

	public LocalAudioViewModel() {
		Singleton.of(MediaData.class).addListener(mListener);
	}

	private Cursor query(String searchText) {
		String[] audioColumns = new String[] { MediaStore.Audio.Media._ID,//
				MediaStore.Audio.Media.DATA, //
				MediaStore.Audio.Media.SIZE, //
				MediaStore.Audio.Media.TITLE,//
				MediaStore.Audio.Media.DURATION,//
				MediaStore.Audio.Media.MIME_TYPE };
		if (TextHelper.isEmptyOrSpaces(searchText)) {
			return ContextManager.contentResolver().query(
					MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, audioColumns, null, null, null);
		} else {
			String queryString = MediaStore.Audio.Media.TITLE + " like '%" + searchText + "%'";
			return ContextManager.contentResolver().query(
					MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, audioColumns, queryString, null,
					null);
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

	private MediaData.Listener mListener = new MediaData.Listener() {

		@Override
		public void onTypeChanged(int type) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onSearchTextChanged(String text) {
			if (!MediaType.AUDIO.equals(Singleton.of(MediaData.class).type())) {
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
