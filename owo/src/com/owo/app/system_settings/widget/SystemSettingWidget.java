package com.owo.app.system_settings.widget;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.owo.app.language.LanguageResourceKeys;
import com.owo.app.language.LanguageResourceManager;
import com.owo.base.pattern.Instance;
import com.owo.widget.interfaces.IConfigurable;

public class SystemSettingWidget extends LinearLayout implements IConfigurable {
	private TextView mChangeLanguageTextView;

	public SystemSettingWidget(Context context) {
		super(context);

		initComponents(context);
		setupListener();
		updateLanguage();
		updateTheme();
	}

	private void initComponents(Context context) {
		mChangeLanguageTextView = new TextView(context);

		setOrientation(LinearLayout.VERTICAL);
		addView(mChangeLanguageTextView);
	}

	private void setupListener() {
		mChangeLanguageTextView.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
			}
		});
	}

	@Override
	public void updateLanguage() {
		mChangeLanguageTextView.setText(Instance.of(LanguageResourceManager.class).get(LanguageResourceKeys.ChangeLanguage));
	}

	@Override
	public void updateTheme() {
		setBackgroundColor(Color.BLACK);
	}
}
