package com.owo.mediaplayer;

import android.content.Context;
import android.widget.FrameLayout;
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

		LinearLayout layout = new LinearLayout(getContext());
		layout.setOrientation(LinearLayout.VERTICAL);
		LinearLayout.LayoutParams lParams = new LinearLayout.LayoutParams(100,
				50);
		layout.addView(mPause, lParams);
		layout.addView(mResume, lParams);
		layout.addView(mStart, lParams);
		layout.addView(mStop, lParams);
		layout.addView(mPre, lParams);
		layout.addView(mNext, lParams);
		layout.addView(mFastForward, lParams);
		layout.addView(mFastBackward, lParams);
		layout.addView(mEndTime, lParams);
		layout.addView(mCurrentTime, lParams);
		layout.addView(mLoadingText, lParams);
		addView(layout);
	}

}
