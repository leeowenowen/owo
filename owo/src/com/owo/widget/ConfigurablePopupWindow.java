package com.owo.widget;

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
		mInterceptedContentView = contentView;
		super.setContentView(contentView);
		onContentViewSetted();
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
	}

}
