package com.owo.mediastore;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.provider.MediaStore;
import android.util.Log;

public class MediaScanner {
	public static void start(Context context) {
		ContentResolver contentResolver = context.getContentResolver();
		String[] videoColumns = new String[] { MediaStore.Video.Media._ID,
				MediaStore.Video.Media.DATA, MediaStore.Video.Media.TITLE,
				MediaStore.Video.Media.MIME_TYPE };

		Cursor cursor = contentResolver.query(
				MediaStore.Video.Media.EXTERNAL_CONTENT_URI, videoColumns,
				null, null, null);
		while (cursor.moveToNext()) {
			String _id = cursor.getString(cursor
					.getColumnIndexOrThrow(MediaStore.Video.Media._ID));
			String filePath = cursor.getString(cursor
					.getColumnIndexOrThrow(MediaStore.Video.Media.DATA));
			String title = cursor.getString(cursor
					.getColumnIndexOrThrow(MediaStore.Video.Media.TITLE));
			String mime_type = cursor.getString(cursor
					.getColumnIndexOrThrow(MediaStore.Video.Media.MIME_TYPE));
			Log.v("xxx", "_id=" + _id);
			Log.v("xxx", "title=" + title);
			Log.v("xxx", "filePath=" + filePath);
			Log.v("xxx", "mime_type=" + mime_type);
		}
	}
}
