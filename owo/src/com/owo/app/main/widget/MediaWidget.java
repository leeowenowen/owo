package com.owo.app.main.widget;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.owo.app.language.Language;
import com.owo.app.language.LanguageObserver;
import com.owo.app.language.LanguageResourceKeys;
import com.owo.app.theme.ThemeObserver;
import com.owo.base.pattern.Instance;
import com.owo.media.audio.LocalAudioView;
import com.owo.media.image.LocalImageView;
import com.owo.media.video.LocalVideoView;
import com.owo.widget.ScrollTabControl;
import com.owo.widget.owo_TabHost.TabContentFactory;

public class MediaWidget extends FrameLayout implements ThemeObserver, LanguageObserver {
	private ScrollTabControl mTabHost;

	private TextView[] mTextViews;

	public MediaWidget(Context context) {
		super(context);

		mTabHost = new ScrollTabControl(context);

		mTextViews = new TextView[3];
		for (int i = 0; i < 3; ++i) {
			mTextViews[i] = new TextView(context);
			mTextViews[i].setLayoutParams(new LinearLayout.LayoutParams(0, 100, 1));
			mTextViews[i].setGravity(Gravity.CENTER);
		}

		mTabHost.addTab(mTabHost.newTabSpec("video").setIndicator(mTextViews[0]).setContent(mTabContentFactory));
		mTabHost.addTab(mTabHost.newTabSpec("audio").setIndicator(mTextViews[1]).setContent(mTabContentFactory));
		mTabHost.addTab(mTabHost.newTabSpec("image").setIndicator(mTextViews[2]).setContent(mTabContentFactory));
		addView(mTabHost);
	}

	private TabContentFactory mTabContentFactory = new TabContentFactory() {
		@Override
		public View createTabContent(String tag) {
			if (tag.equals("image")) {
				return new LocalImageView(getContext());
			} else if (tag.equals("video")) {
				return new LocalVideoView(getContext());
			} else if (tag.equals("audio")) {
				return new LocalAudioView(getContext());
			}
			return null;
		}
	};

	@Override
	public void onLanguageChanged() {
		for (int i = 0; i < 3; ++i) {
			String text = "";
			switch (i) {
			case 0:
				text = Instance.of(Language.class).get(LanguageResourceKeys.Video);
				break;
			case 1:
				text = Instance.of(Language.class).get(LanguageResourceKeys.Audio);
				break;
			case 2:
				text = Instance.of(Language.class).get(LanguageResourceKeys.Image);
				break;
			}
			mTextViews[i].setText(text);
		}

	}

	@Override
	public void onThemeChanged() {
		// TODO Auto-generated method stub

	}
}
