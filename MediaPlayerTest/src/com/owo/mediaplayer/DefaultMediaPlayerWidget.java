package com.owo.mediaplayer;

import com.owo.mediaplayer.view.SwitchView;
import com.owo.mediaplayer.view.utils.LP;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

public class DefaultMediaPlayerWidget extends AbsMediaPlayerWidget {

	public DefaultMediaPlayerWidget(Context context) {
		super(context);
	}

	@SuppressLint("NewApi")
	@Override
	protected void setupLayout() {
		// surface view
		addView(mSurfaceView, new FrameLayout.LayoutParams(
				FrameLayout.LayoutParams.MATCH_PARENT,
				FrameLayout.LayoutParams.MATCH_PARENT));

		// progress bar
		LinearLayout progressLinearLayout = new LinearLayout(getContext());
		progressLinearLayout.setGravity(Gravity.CENTER);
		LinearLayout.LayoutParams timeLP = new LinearLayout.LayoutParams(150,
				LinearLayout.LayoutParams.WRAP_CONTENT);
		progressLinearLayout.addView(mCurrentTime, timeLP);
		progressLinearLayout.addView(mSeekBar, new LinearLayout.LayoutParams(0,
				LinearLayout.LayoutParams.WRAP_CONTENT, 1));
		progressLinearLayout.addView(mEndTime, timeLP);

		// control bar
		SwitchView startStop = new SwitchView(getContext());
		startStop.of(mPause, mResume);

		SwitchView fullScreen = new SwitchView(getContext());
		fullScreen.of(mEnterFullScreen, mExitFullScreen);

		LinearLayout controlBar = new LinearLayout(getContext());
		controlBar.setGravity(Gravity.CENTER);
		LinearLayout.LayoutParams iconLP = new LinearLayout.LayoutParams(200,
				200);
		controlBar.addView(mPre, iconLP);
		controlBar.addView(startStop, iconLP);
		controlBar.addView(mNext, iconLP);
		controlBar.addView(fullScreen, iconLP);

		// bottom bar
		LinearLayout bottomBar = new LinearLayout(getContext());
		bottomBar.setOrientation(LinearLayout.VERTICAL);
		bottomBar.setGravity(Gravity.CENTER);
		bottomBar.addView(progressLinearLayout, LP.LMW);
		bottomBar.addView(controlBar, LP.LMW);

		addView(bottomBar, new FrameLayout.LayoutParams(
				FrameLayout.LayoutParams.MATCH_PARENT,
				FrameLayout.LayoutParams.WRAP_CONTENT, Gravity.BOTTOM));
		bottomBar.setBackgroundColor(Color.argb(100, 0, 0, 0));
	}

}
