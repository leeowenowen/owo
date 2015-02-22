package com.owo.app.widget;

import com.owo.base.mvc.interfaces.IMessageHandler;
import com.owo.mediaplayer.view.utils.LP;

import android.content.Context;
import android.view.KeyEvent;
import android.widget.LinearLayout;

public class MainFrame extends LinearLayout {
	private SearchWidget mSearchWidget;
	private MediaStoreWidget mMediaStoreWidget;

	public MainFrame(Context context, IMessageHandler messageHandler) {
		super(context);
		initComponents(context, messageHandler);
	}

	private void initComponents(Context context, IMessageHandler messageHandler) {
		mSearchWidget = new SearchWidget(context, messageHandler);
		mMediaStoreWidget = new MediaStoreWidget(context, messageHandler);

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
