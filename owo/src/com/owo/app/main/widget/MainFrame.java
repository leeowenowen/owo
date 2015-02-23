package com.owo.app.main.widget;

import android.content.Context;
import android.view.KeyEvent;
import android.widget.LinearLayout;

import com.owo.mediaplayer.view.utils.LP;

public class MainFrame extends LinearLayout {
	private SearchWidget mSearchWidget;
	private MediaWidget mMediaStoreWidget;

	public MainFrame(Context context) {
		super(context);
		initComponents(context);
	}

	private void initComponents(Context context) {
		mSearchWidget = new SearchWidget(context);
		mMediaStoreWidget = new MediaWidget(context);

		setOrientation(LinearLayout.VERTICAL);
		addView(mSearchWidget);
		addView(mMediaStoreWidget, LP.LW01);
	}

	@Override
	public boolean dispatchKeyEvent(KeyEvent event) {
		// TODO Auto-generated method stub
		return super.dispatchKeyEvent(event);
	}
}
