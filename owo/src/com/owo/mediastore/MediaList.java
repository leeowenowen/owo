package com.owo.mediastore;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.owo.app.util.MediaUtil;

public class MediaList extends ListView {

	public MediaList(Context context) {
		super(context);
		ContentResolver contentResolver = context.getContentResolver();
		String[] videoColumns = new String[] { MediaStore.Video.Media._ID,//
				MediaStore.Video.Media.DATA, //
				MediaStore.Video.Media.TITLE,//
				MediaStore.Video.Media.DURATION,//
				MediaStore.Video.Media.WIDTH,//
				MediaStore.Video.Media.HEIGHT,//
				MediaStore.Video.Media.RESOLUTION,//
				MediaStore.Video.Media.MIME_TYPE };
		MediaAdapter adapter = new MediaAdapter();
		Cursor cursor = contentResolver.query(
				MediaStore.Video.Media.EXTERNAL_CONTENT_URI, videoColumns,
				null, null, null);
		adapter.update(cursor);
		setAdapter(adapter);
	}

	private class MediaAdapter extends BaseAdapter {
		private Cursor mCursor;

		public void update(Cursor cursor) {
			mCursor = cursor;
		}

		@Override
		public int getCount() {
			return mCursor.getCount();
		}

		@Override
		public Object getItem(int position) {
			return null;
		}

		@Override
		public long getItemId(int position) {
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ItemView view = null;
			if (convertView != null) {
				view = (ItemView) convertView;
			}

			else {
				view = new ItemView(getContext());
			}
			mCursor.moveToPosition(position);
			view.updateData(mCursor);
			return view;
		}
	}

	private class ItemView extends LinearLayout {
		private TextView mTitle;
		private TextView mPath;
		private ImageView mThumbnail;

		public ItemView(Context context) {
			super(context);
			mTitle = new TextView(getContext());
			mPath = new TextView(getContext());
			mThumbnail = new ImageView(getContext());
			setOrientation(LinearLayout.VERTICAL);
			addView(mTitle);
			addView(mPath);
			addView(mThumbnail, new LinearLayout.LayoutParams(
					LinearLayout.LayoutParams.WRAP_CONTENT,
					LinearLayout.LayoutParams.WRAP_CONTENT));
		}

		void updateData(Cursor cursor) {
			String filePath = cursor.getString(cursor
					.getColumnIndexOrThrow(MediaStore.Video.Media.DATA));
			String title = cursor.getString(cursor
					.getColumnIndexOrThrow(MediaStore.Video.Media.TITLE));
			mTitle.setText(title);
			mPath.setText(filePath);
			new GetThumbnailTask().execute(filePath);
		}

		private class GetThumbnailTask extends AsyncTask<String, Void, Bitmap> {

			@Override
			protected Bitmap doInBackground(String... params) {
				String path = (String) params[0];
				return MediaUtil.createVideoThumbnail(path, 400, 400, null,
						null);
			}

			@Override
			protected void onPostExecute(Bitmap bmp) {
				mThumbnail.setBackgroundDrawable(new BitmapDrawable(bmp));

			}

		}
	}
}
