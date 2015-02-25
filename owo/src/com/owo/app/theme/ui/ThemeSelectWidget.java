package com.owo.app.theme.ui;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ShapeDrawable;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;

import com.owo.app.language.LanguageObserver;
import com.owo.app.theme.Theme;
import com.owo.app.theme.ThemeObserver;
import com.owo.base.pattern.Instance;
import com.owo.ui.shape.CircleShape;

public class ThemeSelectWidget extends LinearLayout implements ThemeObserver, LanguageObserver {
	private static final int SIZE = 5;
	private View[] mViews;
	private CircleShape[] mCircleShapes;

	@SuppressWarnings("deprecation")
	public ThemeSelectWidget(Context context) {
		super(context);
		setGravity(Gravity.CENTER);

		mViews = new View[SIZE];
		mCircleShapes = new CircleShape[SIZE];
		LinearLayout.LayoutParams lParams = new LinearLayout.LayoutParams(200, 200);
		for (int i = 0; i < SIZE; ++i) {
			mViews[i] = new View(context);
			mCircleShapes[i] = new CircleShape();
			mViews[i].setBackgroundDrawable(new ShapeDrawable(mCircleShapes[i]));
			final int index = i;
			mViews[i].setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					assert (mClient != null);
					int color = mCircleShapes[index].color();
					mClient.onThemeSelected(String.valueOf(color));
				}
			});
			addView(mViews[i], lParams);
		}
		mCircleShapes[0].color(Color.WHITE);
		mCircleShapes[1].color(Color.BLACK);
		mCircleShapes[2].color(Color.RED);
		mCircleShapes[3].color(Color.BLUE);
		mCircleShapes[4].color(Color.GREEN);
	}

	public static interface Client {
		void onThemeSelected(String themeColor);
	}

	private Client mClient;

	public void client(Client client) {
		mClient = client;
	}

	@Override
	public void onLanguageChanged() {
	}

	@Override
	public void onThemeChanged() {
		int color = Instance.of(Theme.class).bgColor();
		setBackgroundColor(Color.argb(100, Color.red(color), Color.green(color), Color.blue(color)));
	}

}
