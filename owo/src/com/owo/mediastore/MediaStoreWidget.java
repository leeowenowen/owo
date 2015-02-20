package com.owo.mediastore;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.owo.mediastore.interfaces.MediaType;
import com.owo.mediastore.interfaces.ScaleLevel;
import com.owo.widget.owo_TabHost;
import com.owo.widget.owo_TabHost.TabContentFactory;

public class MediaStoreWidget extends FrameLayout {
	private owo_TabHost mTabHost;
	private TabContentFactory mTabContentFactory = new TabContentFactory() {
		@Override
		public View createTabContent(String tag) {
			int level = ScaleLevel.sLevel0;
			int mediaType;
			if (tag.equals("video")) {
				mediaType = MediaType.sVideo;
			} else if (tag.equals("audio")) {
				mediaType = MediaType.sAudio;
			} else {
				mediaType = MediaType.sIMage;
			}

			MediaStore ms = new MediaStore(getContext());
			MediaStoreController controller = new MediaStoreController(
					getContext(), level, mediaType);
			ms.setController(controller);
			return ms;
		}
	};

	public MediaStoreWidget(Context context) {
		super(context);

		mTabHost = new owo_TabHost(context);

		TextView[] textViews = new TextView[3];
		for (int i = 0; i < 3; ++i) {
			textViews[i] = new TextView(context);
			String text = "";
			int color = 0;
			switch (i) {
			case 0:
				color = Color.RED;
				text = "video";
				break;
			case 1:
				color = Color.BLUE;
				text = "audio";
				break;
			case 2:
				color = Color.GREEN;
				text = "image";
				break;
			}
			textViews[i].setText(text);
			textViews[i].setBackgroundColor(color);
			textViews[i].setLayoutParams(new LinearLayout.LayoutParams(0, 100,
					1));
		}

		mTabHost.addTab(mTabHost.newTabSpec("video").setIndicator(textViews[0])
				.setContent(mTabContentFactory));
		mTabHost.addTab(mTabHost.newTabSpec("audio").setIndicator(textViews[1])
				.setContent(mTabContentFactory));
		mTabHost.addTab(mTabHost.newTabSpec("image").setIndicator(textViews[2])
				.setContent(mTabContentFactory));
		addView(mTabHost);
	}

}
