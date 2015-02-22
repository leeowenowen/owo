package com.owo.app.widget;

import com.owo.app.ParamKey;
import com.owo.app.mvc.MessageId;
import com.owo.base.common.Param;
import com.owo.base.mvc.interfaces.IMessageHandler;
import com.owo.mediaplayer.view.shape.VF;
import com.owo.mediaplayer.view.utils.LP;

import android.content.Context;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.SubMenu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.PopupWindow;
import android.widget.TextView;

public class SearchWidget extends LinearLayout {
	private TextView mTitle;
	private View mSearchView;
	private View mMenuView;
	private IMessageHandler mMessageHandler;

	public SearchWidget(Context context, IMessageHandler messageHandler) {
		super(context);
		mMessageHandler = messageHandler;
		initComponents(context);
		setupListener();
	}

	private void initComponents(Context context) {
		mTitle = new TextView(context);
		mSearchView = VF.of(context, VF.ViewID.Search);
		mMenuView = VF.of(context, VF.ViewID.Menu);

		addView(mTitle, LP.L0W1);
		LinearLayout.LayoutParams itemLayoutParams = new LinearLayout.LayoutParams(
				150, 150);
		addView(mSearchView, itemLayoutParams);
		addView(mMenuView, itemLayoutParams);
	}

	private void setupListener() {
		mMenuView.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Param param = Param.obtain().put(ParamKey.View, mMenuView);
				mMessageHandler.handleMessage(MessageId.ShowMenuWidget, param,
						null);
				param.recycle();
			}
		});
	}
	@Override
	public boolean dispatchKeyEvent(KeyEvent event) {
		// TODO Auto-generated method stub
		return super.dispatchKeyEvent(event);
	}
}
