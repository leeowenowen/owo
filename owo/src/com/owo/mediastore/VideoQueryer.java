package com.owo.mediastore;

import android.content.ContentResolver;
import android.database.Cursor;
import android.provider.MediaStore;

public class VideoQueryer extends ContentMediaQueryer {

	protected VideoQueryer(ContentResolver contentResolver) {
		super(contentResolver);
	}

	@Override
	protected Cursor queryImpl() {
		String[] videoColumns = new String[] { MediaStore.Video.Media._ID,//
				MediaStore.Video.Media.DATA, //
				MediaStore.Video.Media.SIZE, //
				MediaStore.Video.Media.TITLE,//
				MediaStore.Video.Media.DURATION,//
				MediaStore.Video.Media.WIDTH,//
				MediaStore.Video.Media.HEIGHT,//
				MediaStore.Video.Media.RESOLUTION,//
				MediaStore.Video.Media.MIME_TYPE };
		return mContentResolver.query(
				MediaStore.Video.Media.EXTERNAL_CONTENT_URI, videoColumns,
				null, null, null);
	}

}
