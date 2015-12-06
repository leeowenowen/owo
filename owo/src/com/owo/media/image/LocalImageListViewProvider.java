package com.owo.media.image;

import android.content.Context;
import android.database.Cursor;
import android.view.ViewGroup;
import android.widget.AbsListView;

import com.owo.media.DefaultListViewFactory;
import com.owo.media.MediaCursorAdapter;
import com.owo.media.interfaces.IListViewFactory;
import com.owo.media.interfaces.ScaleLevel;
import com.owo.mediaplayer.interfaces.ListViewConfig;

public class LocalImageListViewProvider {
	private LocalImageViewModel mViewModel;
	private MediaCursorAdapter mAdapter;
	private IListViewFactory mFactory = new DefaultListViewFactory();
	public AbsListView mListView;

	public ViewGroup get() {
		return mListView;
	}

	public LocalImageListViewProvider(Context context, ListViewConfig config) {

		mAdapter = new MediaCursorAdapter();
		mAdapter.setCursorTransformer(ImageCursorTransformerFactory
				.create(ScaleLevel.sLevel1));

		mViewModel = new LocalImageViewModel();
		mViewModel.attach(mClient);

		mListView = mFactory.create(context, config);
		mListView.setAdapter(mAdapter);
	}

	private LocalImageViewModel.ClientImpl mClient = new LocalImageViewModel.ClientImpl() {

		@Override
		public void onDataChanged(Cursor cursor) {
			mAdapter.setCursor(cursor);
		}

		@Override
		public void onLevelChanged(int level) {
		}
	};
}
