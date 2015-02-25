package com.owo.app.main.ui;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.owo.app.common.ContextManager;
import com.owo.app.language.Language;
import com.owo.app.language.LanguageObserver;
import com.owo.app.language.LanguageResourceKeys;
import com.owo.app.system_settings.SysSettingActivity;
import com.owo.app.system_settings.SystemSettingKeys;
import com.owo.app.system_settings.SystemSettingsData;
import com.owo.app.theme.Theme;
import com.owo.app.theme.ThemeObserver;
import com.owo.app.theme.ui.ThemeSelectWidget;
import com.owo.app.theme.ui.ThemeSelectWidget.Client;
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
				final Dialog dialog = new Dialog(getContext());
				dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
				ThemeSelectWidget widget = new ThemeSelectWidget(getContext());
				widget.client(new Client() {
					@Override
					public void onThemeSelected(String themeColor) {
						Instance.of(SystemSettingsData.class).set(SystemSettingKeys.Theme,
								themeColor);
						dialog.dismiss();
					}
				});
				dialog.setContentView(widget, new ViewGroup.LayoutParams(1000, 300));
				dialog.show();
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
		// setBackgroundColor(Instance.of(Theme.class).bgColor());
		Theme.updateTheme(this);
	}
}
