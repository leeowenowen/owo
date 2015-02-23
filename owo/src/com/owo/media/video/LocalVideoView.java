package com.owo.media.video;

import android.content.Context;
import android.database.Cursor;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;

import com.owo.app.ParamKey;
import com.owo.app.mvc.MessageId;
import com.owo.base.common.Param;
import com.owo.base.mvc.interfaces.IMessageHandler;
import com.owo.media.MediaCursorAdapter;
import com.owo.media.QueryUtil;
import com.owo.media.base.MediaListView;
import com.owo.media.interfaces.ScaleLevel;

public class LocalVideoView extends MediaListView {
	private LocalVideoViewModel mViewModel;
	private MediaCursorAdapter mAdapter;

	public LocalVideoView(Context context, IMessageHandler messageHandler) {
		super(context, messageHandler);

		mAdapter = new MediaCursorAdapter();
		mAdapter.setCursorTransformer(VideoCursorTransformerFactory.create(ScaleLevel.sLevel0));

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
			final String path = QueryUtil.getColumn(cursor, MediaStore.Video.Media.DATA);
			Param param = Param.obtain().put(ParamKey.Value, path);
			mMessageHandler.handleMessage(MessageId.PlayVideo, param, null);
			param.recycle();
		}
	};
}
