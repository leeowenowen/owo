package com.owo.app.main.ui;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.view.Gravity;
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
import com.owo.app.theme.ui.ThemeSelectWidget.ThemeSelectWidgetClient;
import com.owo.base.pattern.Singleton;
import com.owo.base.util.DimensionUtil;

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
		mChangeSkin = new TextView(context);
		mSettings = new TextView(context);
		mHelp = new TextView(context);

		mChangeSkin.setGravity(Gravity.CENTER);
		mSettings.setGravity(Gravity.CENTER);
		mHelp.setGravity(Gravity.CENTER);

		setOrientation(LinearLayout.VERTICAL);
		LinearLayout.LayoutParams lParams = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.MATCH_PARENT, DimensionUtil.rowHeight());
		addView(mChangeSkin, lParams);
		addView(mSettings, lParams);
		addView(mHelp, lParams);
	}

	private void setupListener() {
		mChangeSkin.setOnClickListener(mOnClickListener);
		mSettings.setOnClickListener(mOnClickListener);
		mHelp.setOnClickListener(mOnClickListener);
	}

	private OnClickListener mOnClickListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			assert (mClient != null);
			mClient.onItemSelected();
			if (v.equals(mChangeSkin)) {
				final Dialog dialog = new Dialog(getContext());
				dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
				ThemeSelectWidget widget = new ThemeSelectWidget(getContext());
				widget.client(new ThemeSelectWidgetClient() {
					@Override
					public void onThemeSelected(String themeColor) {
						Singleton.of(SystemSettingsData.class).set(SystemSettingKeys.Theme,
								themeColor);
						dialog.dismiss();
					}
				});
				dialog.setContentView(widget, new ViewGroup.LayoutParams(DimensionUtil.w(1000),
						DimensionUtil.h(300)));
				dialog.show();

			} else if (v.equals(mSettings)) {
				Intent intent = new Intent(ContextManager.activity(), SysSettingActivity.class);
				ContextManager.activity().startActivity(intent);
			} else if (v.equals(mHelp)) {
			}
		}
	};

	@Override
	public void onLanguageChanged() {
		mChangeSkin.setText(Singleton.of(Language.class).get(LanguageResourceKeys.ChangeSkin));
		mSettings.setText(Singleton.of(Language.class).get(LanguageResourceKeys.Setting));
		mHelp.setText(Singleton.of(Language.class).get(LanguageResourceKeys.Help));
	}

	@Override
	public void onThemeChanged() {
		int textColor = Singleton.of(Theme.class).textColor();
		mChangeSkin.setTextColor(textColor);
		mSettings.setTextColor(textColor);
		mHelp.setTextColor(textColor);
	}

	public static interface MenuWidgetClient {
		void onItemSelected();
	}

	private MenuWidgetClient mClient;

	public void client(MenuWidgetClient client) {
		mClient = client;
	}
}
