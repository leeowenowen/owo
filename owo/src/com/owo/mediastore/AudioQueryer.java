package com.owo.mediastore;

import android.content.ContentResolver;
import android.database.Cursor;
import android.provider.MediaStore;

public class AudioQueryer extends ContentMediaQueryer {

	protected AudioQueryer(ContentResolver contentResolver) {
		super(contentResolver);
	}

	@Override
	protected Cursor queryImpl() {
		String[] audioColumns = new String[] { MediaStore.Audio.Media._ID,//
				MediaStore.Audio.Media.DATA, //
				MediaStore.Audio.Media.SIZE, //
				MediaStore.Audio.Media.TITLE,//
				MediaStore.Audio.Media.DURATION,//
				MediaStore.Audio.Media.MIME_TYPE };
		return mContentResolver.query(
				MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, audioColumns,
				null, null, null);
	}

}
