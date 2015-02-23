package com.owo.app.system_settings.widget;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.owo.widget.interfaces.IConfigurable;

public class SysSettingWidget extends LinearLayout implements IConfigurable {
	private TextView mChangeLanguage;

	public SysSettingWidget(Context context) {
		super(context);

		initComponents(context);
		setupListener();
		updateLanguage();
		updateTheme();
	}

	private void initComponents(Context context) {
		mChangeLanguage = new TextView(context);

		setOrientation(LinearLayout.VERTICAL);
		addView(mChangeLanguage);
	}

	private void setupListener() {
		setOnKeyListener(new OnKeyListener() {
			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				if (event.getKeyCode() == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP) {
					Log.v("xxx", "back");
				}
				return false;
			}
		});

	}

	@Override
	public void updateLanguage() {
		mChangeLanguage.setText("切换语言");
	}

	@Override
	public void updateTheme() {
		setBackgroundColor(Color.BLACK);
	}

}
