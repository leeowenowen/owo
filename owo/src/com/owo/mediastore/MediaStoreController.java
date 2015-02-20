package com.owo.mediastore;

import android.R.integer;
import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.DataSetObserver;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;

import com.owo.mediastore.interfaces.IAbsMediaProviderFactory;
import com.owo.mediastore.interfaces.ICursorTransformer;
import com.owo.mediastore.interfaces.IListViewFactory;
import com.owo.mediastore.interfaces.IMediaQueryer;
import com.owo.mediastore.interfaces.IMediaStoreController;
import com.owo.mediastore.interfaces.ListViewType;
import com.owo.mediastore.interfaces.MediaType;
import com.owo.mediastore.interfaces.ScaleLevel;

public class MediaStoreController implements IMediaStoreController {
	private IListViewFactory mAdapterViewFactory;
	private IAbsMediaProviderFactory mMediaProviderFactory;
	private Client mClient;
	private ICursorTransformer mTransformer;
	private IMediaQueryer mQueryer;
	private AbsListView mListView;

	@Override
	public void setClient(Client client) {
		mClient = client;
		mClient.onAdapterViewChanged(mListView);
	}

	public MediaStoreController(Context context, int level , int mediaType) {
		mAdapterViewFactory = new DefaultListViewFactory();
		mMediaProviderFactory = new DefaultMediaProviderFactory(context);

		mTransformer = mMediaProviderFactory.createCursorTransformer(level,
				mediaType);
		mQueryer = mMediaProviderFactory.createMediaQueryer(mediaType);

		mListView = createListView(context);
	}

	@SuppressLint("NewApi")
	@Override
	public AbsListView createListView(Context context) {
		// 1) create AbsListView
		// TODO:create by policy
		AbsListView listView = mAdapterViewFactory.create(
				ListViewType.sListView, context);
		// 2) create cursor
		Cursor cursor = mQueryer.query(mCursorObserver);
		// 3) create Adapter
		mAdapter = new MediaStoreAdapter(cursor);

		listView.setAdapter(mAdapter);
		return listView;
	}

	private MediaStoreAdapter mAdapter;
	private DataSetObserver mCursorObserver = new DataSetObserver() {
		public void onChanged() {
			mAdapter.update();
		}
	};

	// TODO: re-factor this adapter after see source code of #CursorAdapter
	private class MediaStoreAdapter extends BaseAdapter {
		private Cursor mCursor;

		public MediaStoreAdapter(Cursor cursor) {
			mCursor = cursor;
		}

		@SuppressWarnings("deprecation")
		public void update() {
			mCursor.requery();
			notifyDataSetChanged();
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
			mCursor.moveToPosition(position);
			return mTransformer.transform(convertView, mCursor);
		}

	}
	// TODO: as system setting observer

}
