package com.owo.app.main.widget;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.owo.app.common.ContextManager;
import com.owo.app.language.LanguageObserver;
import com.owo.app.language.LanguageResourceKeys;
import com.owo.app.language.Language;
import com.owo.app.system_settings.SysSettingActivity;
import com.owo.app.theme.ThemeObserver;
import com.owo.base.pattern.Instance;

public class MenuWidget extends LinearLayout implements LanguageObserver, ThemeObserver {
	private TextView mChangeSkin;
	private TextView mSettings;
	private TextView mHelp;

	public MenuWidget(Context context) {
		super(context);
		initComponents(context);
		setupListener();
		onLanguageChanged();
		onThemeChanged();
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
	public void onLanguageChanged() {
		mChangeSkin.setText(Instance.of(Language.class).get(LanguageResourceKeys.ChangeSkin));
		mSettings.setText(Instance.of(Language.class).get(LanguageResourceKeys.Setting));
		mHelp.setText(Instance.of(Language.class).get(LanguageResourceKeys.Help));
	}

	@Override
	public void onThemeChanged() {
		setBackgroundColor(Color.argb(255, 100, 100, 0));
	}
}
