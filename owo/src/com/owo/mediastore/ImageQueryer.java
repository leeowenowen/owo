package com.owo.mediastore;

import android.content.ContentResolver;
import android.database.Cursor;
import android.provider.MediaStore;

public class ImageQueryer extends ContentMediaQueryer {

	protected ImageQueryer(ContentResolver contentResolver) {
		super(contentResolver);
	}

	@Override
	protected Cursor queryImpl() {
		String[] imageColumns = new String[] { MediaStore.Images.Media._ID,//
				MediaStore.Images.Media.DATA, //
				MediaStore.Images.Media.SIZE, //
				MediaStore.Images.Media.TITLE,//
				MediaStore.Images.Media.WIDTH,//
				MediaStore.Images.Media.HEIGHT,//
				MediaStore.Images.Media.MIME_TYPE };
		return mContentResolver.query(
				MediaStore.Images.Media.EXTERNAL_CONTENT_URI, imageColumns,
				null, null, null);
	}

}
