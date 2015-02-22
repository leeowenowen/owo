package com.owo.app.widget;

import android.content.Context;
import android.graphics.Color;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.owo.app.mvc.MessageId;
import com.owo.base.mvc.interfaces.IMessageHandler;
import com.owo.widget.interfaces.IConfigurable;

public class MenuWidget extends LinearLayout implements IConfigurable {
	private TextView mChangeSkin;
	private TextView mSettings;
	private TextView mHelp;
	IMessageHandler mMessageHandler;

	public MenuWidget(Context context, IMessageHandler messageHandler) {
		super(context);
		mMessageHandler = messageHandler;
		initComponents(context);
		setupListener();
		updateLanguage();
		updateTheme();
	}

	private void initComponents(Context context) {
		setOrientation(LinearLayout.VERTICAL);
		mChangeSkin = new TextView(context);
		mSettings = new TextView(context);
		mHelp = new TextView(context);

		addView(mChangeSkin);
		addView(mSettings);
		addView(mHelp);
	}

	private void setupListener() {
		mChangeSkin.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {

			}
		});

		mSettings.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
//				PopupMenu popupMenu = new PopupMenu(getContext(), mSettings);
//				Menu menu = popupMenu.getMenu();
//				menu.add("setting1");
//				menu.add("setting2");
//				menu.addSubMenu("sub_setting1");
//				popupMenu.show();
			}
		});

		mHelp.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {

			}
		});
	}

	@Override
	public void updateLanguage() {
		mChangeSkin.setText("更换皮肤");
		mSettings.setText("设置");
		mHelp.setText("帮助");
	}

	@Override
	public void updateTheme() {
		setBackgroundColor(Color.argb(255, 100, 100, 0));
	}

	@Override
	public boolean dispatchKeyEvent(KeyEvent event) {
		if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
			return mMessageHandler.handleMessage(MessageId.HideMenuWidget,
					null, null);
		}
		boolean ret = super.dispatchKeyEvent(event);
		return ret;
	}
}
