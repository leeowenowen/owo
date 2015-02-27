package com.owo.ui;

import android.view.View;
import android.widget.PopupWindow;

import com.owo.app.language.Language;
import com.owo.app.language.LanguageObserver;
import com.owo.app.theme.Theme;
import com.owo.app.theme.ThemeObserver;
import com.owo.base.pattern.Instance;

public class ConfigurablePopupWindow extends PopupWindow implements ThemeObserver, LanguageObserver {
	private View mInterceptedContentView;

	@Override
	public void setContentView(View contentView) {
		// in constructor of PopupWindow, #setContentView will be invoked
		// some known existing bug in 2.x devices may lead to crash
		// TODO: read source code of #PopupWindow of SDK 2.X
		if (contentView == null) {
			return;
		}
		mInterceptedContentView = contentView;
		super.setContentView(contentView);
		onContentViewSetted();
		onThemeChanged();
	}

	private void onContentViewSetted() {
		Instance.of(Language.class).addObserver(new LanguageObserver() {
			@Override
			public void onLanguageChanged() {
				ConfigurablePopupWindow.this.onLanguageChanged();
				Language.notifyChanged(mInterceptedContentView);
			}
		});
		Instance.of(Theme.class).addObserver(new ThemeObserver() {

			@Override
			public void onThemeChanged() {
				ConfigurablePopupWindow.this.onThemeChanged();
				Theme.notifyChanged(mInterceptedContentView);
			}
		});
	}

	@Override
	public void onLanguageChanged() {
	}

	@Override
	public void onThemeChanged() {
		// int bgColor = Instance.of(Theme.class).bgColor();
		// setBackgroundDrawable(new ColorDrawable(Color.argb(255,
		// Color.red(bgColor) + 0,
		// Color.green(bgColor) + 0, Color.blue(bgColor) + 0)));
	}

}
