package com.owo.mediaplayer.ui;

import android.content.Context;
import android.view.View;
import android.widget.FrameLayout;

import com.owo.app.theme.Theme;
import com.owo.app.theme.ThemeObserver;
import com.owo.base.pattern.Instance;
import com.owo.ui.utils.LP;

public class SwitchView extends FrameLayout implements ThemeObserver {

	public SwitchView(Context context) {
		super(context);
		onThemeChanged();
	}

	public SwitchView of(View one, View theOther) {
		addView(one, LP.FMM);
		addView(theOther, LP.FMM);
		return this;
	}

	@Override
	public void onThemeChanged() {
		setBackgroundColor(Instance.of(Theme.class).bgColor());
	}
}