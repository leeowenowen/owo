package com.owo.mediastore;

import android.content.Context;
import android.widget.AbsListView;
import android.widget.FrameLayout;

import com.owo.mediastore.interfaces.IMediaStoreController;
import com.owo.mediastore.interfaces.IMediaStoreController.Client;

public class MediaStore extends FrameLayout {
	private AbsListView mListView;
	private IMediaStoreController mController;

	public MediaStore(Context context) {
		super(context);

		initComponents(context);
		setupListener();
	}

	private void initComponents(Context context) {

	}

	private void setupListener() {

	}

	public void setController(IMediaStoreController controller) {
		mController = controller;
		mController.setClient(mClient);
	}

	private void updateListView(AbsListView listView) {
		if (mListView != null) {
			removeView(mListView);
		}
		mListView = listView;
		addView(mListView);
	}

	private Client mClient = new Client() {
		@Override
		public void onAdapterViewChanged(AbsListView adapterView) {
			updateListView(adapterView);
		}
	};
}
