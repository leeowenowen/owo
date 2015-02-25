package com.owo.app.base;

import android.app.Activity;
import android.view.View;

import com.owo.app.language.Language;
import com.owo.app.language.LanguageObserver;
import com.owo.app.theme.Theme;
import com.owo.app.theme.ThemeObserver;
import com.owo.base.pattern.Instance;

public class ConfigurableActivity extends Activity {
	@Override
	protected void onDestroy() {
		mInterceptedContentView = null;
		super.onDestroy();
	}

	private View mInterceptedContentView;

	@Override
	public void setContentView(View view) {
		mInterceptedContentView = view;
		super.setContentView(view);
		onContentViewSetted();
	}

	private void onContentViewSetted() {
		Instance.of(Language.class).addObserver(new LanguageObserver() {
			@Override
			public void onLanguageChanged() {
				Language.notifyChanged(mInterceptedContentView);
			}
		});
		Instance.of(Theme.class).addObserver(new ThemeObserver() {

			@Override
			public void onThemeChanged() {
				Theme.notifyChanged(mInterceptedContentView);
			}
		});
	}
}
