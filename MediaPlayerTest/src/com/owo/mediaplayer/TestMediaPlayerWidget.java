package com.owo.mediaplayer;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.widget.FrameLayout;
import android.widget.GridLayout;
import android.widget.LinearLayout;

public class TestMediaPlayerWidget extends AbsMediaPlayerWidget {

	public TestMediaPlayerWidget(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void initUIComponents() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void setupLayout() {
		addView(mSurfaceView, new FrameLayout.LayoutParams(
				FrameLayout.LayoutParams.MATCH_PARENT,
				FrameLayout.LayoutParams.MATCH_PARENT));
		mPause.setText("Pause");
		mResume.setText("Resume");
		// bottom control bar
		LinearLayout progressLinearLayout = new LinearLayout(getContext());
		progressLinearLayout.setGravity(Gravity.CENTER);
		progressLinearLayout.addView(mCurrentTime);
		progressLinearLayout.addView(mSeekBar, new LinearLayout.LayoutParams(0,
				LinearLayout.LayoutParams.WRAP_CONTENT, 1));
		progressLinearLayout.addView(mEndTime);
		LinearLayout bottomControlBar = new LinearLayout(getContext());
		bottomControlBar.setOrientation(LinearLayout.VERTICAL);
		LinearLayout.LayoutParams startStopLP = new LinearLayout.LayoutParams(
				100, 100);
		FrameLayout.LayoutParams matchParent = new FrameLayout.LayoutParams(
				FrameLayout.LayoutParams.MATCH_PARENT,
				FrameLayout.LayoutParams.MATCH_PARENT);

		FrameLayout startStopFrameLayout = new FrameLayout(getContext());
		startStopFrameLayout.addView(mPause, matchParent);
		startStopFrameLayout.addView(mResume, matchParent);

		bottomControlBar.addView(progressLinearLayout,
				new LinearLayout.LayoutParams(
						LinearLayout.LayoutParams.MATCH_PARENT,
						LinearLayout.LayoutParams.WRAP_CONTENT));
		bottomControlBar.addView(startStopFrameLayout, startStopLP);
		addView(bottomControlBar, new FrameLayout.LayoutParams(
				FrameLayout.LayoutParams.MATCH_PARENT,
				FrameLayout.LayoutParams.WRAP_CONTENT, Gravity.BOTTOM
						| Gravity.CENTER));
		bottomControlBar.setBackgroundColor(Color.RED);

		// LinearLayout layout = new LinearLayout(getContext());
		// layout.setOrientation(LinearLayout.VERTICAL);
		// LinearLayout.LayoutParams lParams = new
		// LinearLayout.LayoutParams(100,
		// 50);
		// layout.addView(mPause, lParams);
		// layout.addView(mResume, lParams);
		// layout.addView(mStart, lParams);
		// layout.addView(mStop, lParams);
		// layout.addView(mPre, lParams);
		// layout.addView(mNext, lParams);
		// layout.addView(mFastForward, lParams);
		// layout.addView(mFastBackward, lParams);
		// layout.addView(mEndTime, lParams);
		// layout.addView(mCurrentTime, lParams);
		// layout.addView(mLoadingText, lParams);
		// addView(layout);
	}

}