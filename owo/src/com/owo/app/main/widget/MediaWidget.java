package com.owo.app.main.widget;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.owo.media.audio.LocalAudioView;
import com.owo.media.image.LocalImageView;
import com.owo.media.video.LocalVideoView;
import com.owo.widget.ScrollTabControl;
import com.owo.widget.owo_TabHost.TabContentFactory;

public class MediaWidget extends FrameLayout {
	private ScrollTabControl mTabHost;

	public MediaWidget(Context context) {
		super(context);

		mTabHost = new ScrollTabControl(context);

		TextView[] textViews = new TextView[3];
		for (int i = 0; i < 3; ++i) {
			textViews[i] = new TextView(context);
			String text = "";
			switch (i) {
			case 0:
				text = "视频";
				break;
			case 1:
				text = "音乐";
				break;
			case 2:
				text = "图片";
				break;
			}
			textViews[i].setText(text);
			textViews[i].setLayoutParams(new LinearLayout.LayoutParams(0, 100, 1));
			textViews[i].setGravity(Gravity.CENTER);
		}

		mTabHost.addTab(mTabHost.newTabSpec("video").setIndicator(textViews[0]).setContent(mTabContentFactory));
		mTabHost.addTab(mTabHost.newTabSpec("audio").setIndicator(textViews[1]).setContent(mTabContentFactory));
		mTabHost.addTab(mTabHost.newTabSpec("image").setIndicator(textViews[2]).setContent(mTabContentFactory));
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
}
