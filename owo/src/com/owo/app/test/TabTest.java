package com.owo.app.test;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.owo.ui.owo_TabHost;
import com.owo.ui.owo_TabHost.TabContentFactory;

public class TabTest extends FrameLayout {
	public TabTest(Context context) {
		super(context);
		TabContentFactory factory = new TabContentFactory() {
			@Override
			public View createTabContent(String tag) {
				TextView tView = new TextView(getContext());
				tView.setText("content_tag:" + tag);
				return tView;
			}
		};
		// TabWidget tabWidget = new TabWidget(context);
		TextView[] textViews = new TextView[3];
		for (int i = 0; i < 3; ++i) {
			textViews[i] = new TextView(context);
			textViews[i].setText("tview");
			int color = 0;
			switch (i) {
			case 0:
				color = Color.RED;
				break;
			case 1:
				color = Color.BLUE;
				break;
			case 2:
				color = Color.GREEN;
				break;
			}
			textViews[i].setBackgroundColor(color);
		}


		owo_TabHost tabHost = new owo_TabHost(context);
		tabHost.addTab(tabHost.newTabSpec("tab1").setIndicator(textViews[0])
				.setContent(factory));
		tabHost.addTab(tabHost.newTabSpec("tab2").setIndicator(textViews[1])
				.setContent(factory));
		tabHost.addTab(tabHost.newTabSpec("tab3").setIndicator(textViews[2])
				.setContent(factory));
		addView(tabHost);
	}
}
