package com.owo.media.audio;

import android.content.Context;
import android.database.Cursor;
import android.widget.ListView;

import com.owo.base.mvc.interfaces.IMessageHandler;
import com.owo.media.MediaCursorAdapter;
import com.owo.media.base.MediaListView;
import com.owo.media.interfaces.ScaleLevel;

public class LocalAudioView extends MediaListView {
	private LocalAudioViewModel mViewModel;
	private MediaCursorAdapter mAdapter;

	public LocalAudioView(Context context, IMessageHandler messageHandler) {
		super(context, messageHandler);

		mAdapter = new MediaCursorAdapter();
		mAdapter.setCursorTransformer(AudioCursorTransformerFactory.create(ScaleLevel.sLevel0));

		mViewModel = new LocalAudioViewModel();
		mViewModel.attach(mClient);

		setAdapter(mAdapter);
	}

	private LocalAudioViewModel.ClientImpl mClient = new LocalAudioViewModel.ClientImpl() {

		@Override
		public void onDataChanged(Cursor cursor) {
			mAdapter.setCursor(cursor);
		}

		@Override
		public void onLevelChanged(int level) {
		}
	};
}
