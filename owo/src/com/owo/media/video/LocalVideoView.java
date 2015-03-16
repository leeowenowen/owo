package com.owo.media.video;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.owo.app.common.ContextManager;
import com.owo.media.MediaCursorAdapter;
import com.owo.media.QueryUtil;
import com.owo.media.interfaces.ScaleLevel;
import com.owo.mediaplayer.MediaPlayActivity;

public class LocalVideoView extends ListView {
	private LocalVideoViewModel mViewModel;
	private MediaCursorAdapter mAdapter;

	public LocalVideoView(Context context) {
		super(context);

		mAdapter = new MediaCursorAdapter();
		mAdapter.setCursorTransformer(VideoCursorTransformerFactory.create(ScaleLevel.sLevel2));

		mViewModel = new LocalVideoViewModel();
		mViewModel.attach(mClient);

		setAdapter(mAdapter);
		setOnItemClickListener(mOnItemClickListener);
	}

	private LocalVideoViewModel.ClientImpl mClient = new LocalVideoViewModel.ClientImpl() {

		@Override
		public void onDataChanged(Cursor cursor) {
			mAdapter.setCursor(cursor);
		}

		@Override
		public void onLevelChanged(int level) {
		}
	};

	private OnItemClickListener mOnItemClickListener = new OnItemClickListener() {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
			Cursor cursor = (Cursor) mAdapter.getItem(position);
			// final String path = QueryUtil.getColumn(cursor,
			// MediaStore.Video.Media.DATA);
			String pathString = "";
			cursor.moveToFirst();
			while (!cursor.isAfterLast()) {
				String path = QueryUtil.getColumn(cursor, MediaStore.Video.Media.DATA);
				String title = QueryUtil.getColumn(cursor, MediaStore.Video.Media.TITLE);
				Log.v("xxx", "[path:" + path + "][title:" + title + "]");
				pathString += (path + "@@@@" + title);
				pathString += "####";
				cursor.moveToNext();
			}
			Log.v("xxx","############################pathString:" + pathString);
			Intent intent = new Intent(ContextManager.activity(), MediaPlayActivity.class);
			intent.putExtra("path_title", pathString);
			intent.putExtra("index", position);
			ContextManager.activity().startActivity(intent);
		}
	};
}
