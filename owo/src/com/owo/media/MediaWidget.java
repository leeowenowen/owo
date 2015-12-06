package com.owo.media;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.owo.app.language.Language;
import com.owo.app.language.LanguageObserver;
import com.owo.app.language.LanguageResourceKeys;
import com.owo.app.theme.Theme;
import com.owo.app.theme.ThemeObserver;
import com.owo.base.pattern.Singleton;
import com.owo.base.util.DimensionUtil;
import com.owo.media.audio.LocalAudioView;
import com.owo.media.image.LocalImageListViewProvider;
import com.owo.media.interfaces.ListViewType;
import com.owo.media.interfaces.MediaType;
import com.owo.media.video.LocalVideoView;
import com.owo.mediaplayer.interfaces.ListViewConfig;
import com.owo.ui.ScrollTabControl;
import com.owo.ui.ScrollTabControl.IndicatorView;
import com.owo.ui.ScrollTabControl.OnTabChangeListener;
import com.owo.ui.ScrollTabControl.TabContentFactory;

public class MediaWidget extends FrameLayout implements ThemeObserver,
		LanguageObserver {
	private ScrollTabControl mScrollTabControl;

	private TextView[] mTextViews;

	public MediaWidget(Context context) {
		super(context);

		mScrollTabControl = new ScrollTabControl(context);
		mScrollTabControl.setOnTabChangedListener(new OnTabChangeListener() {
			@Override
			public void onTabChanged(String tabId) {
				Singleton.of(MediaData.class).type(tabId);
			}
		});

		mTextViews = new TextView[3];
		for (int i = 0; i < 3; ++i) {
			mTextViews[i] = new TextView(context);
			mTextViews[i].setLayoutParams(new LinearLayout.LayoutParams(0,
					DimensionUtil.h(100), 1));
			mTextViews[i].setGravity(Gravity.CENTER);
		}

		mScrollTabControl.addTab(mScrollTabControl.newTabSpec(MediaType.VIDEO)
				.setIndicator(new TextViewIndicator(mTextViews[0]))
				.setContent(mTabContentFactory));
		mScrollTabControl.addTab(mScrollTabControl.newTabSpec(MediaType.AUDIO)
				.setIndicator(new TextViewIndicator(mTextViews[1]))
				.setContent(mTabContentFactory));
		mScrollTabControl.addTab(mScrollTabControl.newTabSpec(MediaType.IMAGE)
				.setIndicator(new TextViewIndicator(mTextViews[2]))
				.setContent(mTabContentFactory));
		addView(mScrollTabControl);
		onLanguageChanged();
		onThemeChanged();
	}

	private class TextViewIndicator implements IndicatorView {
		private TextView mTextView;
		private float mTextSize = 14.0f;

		public TextViewIndicator(TextView textView) {
			mTextView = textView;
			mTextView.setTextSize(mTextSize);
		}

		@Override
		public View getView() {
			return mTextView;
		}

		@Override
		public void onTabClosed() {
			mTextView.setTextSize(mTextSize);
		}

		@Override
		public void onSetAsCurrentTab() {
			mTextView.setTextSize(mTextSize * 1.5f);
		}
	}

	private TabContentFactory mTabContentFactory = new TabContentFactory() {
		@Override
		public View createTabContent(String tag) {
			if (tag.equals(MediaType.IMAGE)) {
				ListViewConfig config = new ListViewConfig()
						.type(ListViewType.sAndroidGridView);
				return new LocalImageListViewProvider(getContext(), config)
						.get();
			} else if (tag.equals(MediaType.VIDEO)) {
				return new LocalVideoView(getContext());
			} else if (tag.equals(MediaType.AUDIO)) {
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
				text = Singleton.of(Language.class).get(
						LanguageResourceKeys.Video);
				break;
			case 1:
				text = Singleton.of(Language.class).get(
						LanguageResourceKeys.Audio);
				break;
			case 2:
				text = Singleton.of(Language.class).get(
						LanguageResourceKeys.Image);
				break;
			}
			mTextViews[i].setText(text);
		}

	}

	@Override
	public void onThemeChanged() {
		setBackgroundColor(Singleton.of(Theme.class).bgColor());
		for (int i = 0; i < 3; ++i) {
			mTextViews[i].setTextColor(Singleton.of(Theme.class).textColor());
		}
	}
}
