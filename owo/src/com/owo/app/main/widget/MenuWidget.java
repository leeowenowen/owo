package com.owo.app.main.widget;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.owo.app.common.ContextManager;
import com.owo.app.system_settings.SysSettingActivity;
import com.owo.widget.interfaces.IConfigurable;

public class MenuWidget extends LinearLayout implements IConfigurable {
	private TextView mChangeSkin;
	private TextView mSettings;
	private TextView mHelp;

	public MenuWidget(Context context) {
		super(context);
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
				Intent intent = new Intent(ContextManager.activity(), SysSettingActivity.class);
				ContextManager.activity().startActivity(intent);
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
}
