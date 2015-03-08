package com.owo.mediaplayer.ui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.owo.app.common.BaseHandler;
import com.owo.app.theme.Theme;
import com.owo.base.pattern.Singleton;
import com.owo.base.util.DimensionUtil;
import com.owo.ui.utils.LP;

public class DefaultMediaPlayerWidget extends AbsMediaPlayerWidget {

	public DefaultMediaPlayerWidget(Context context) {
		super(context);
		setupGestureDetector();
		setupUIListeners();
	}

	@SuppressLint("NewApi")
	@Override
	protected void setupLayout() {
		int size = DimensionUtil.rowHeight();
		// center: loading or lock
		FrameLayout.LayoutParams lockLayoutParams = new FrameLayout.LayoutParams(size, size,
				Gravity.CENTER);
		mCenterFrameLayout = new FrameLayout(getContext());
		mCenterFrameLayout.addView(mLoadingBar, LP.FWWC);
		mCenterFrameLayout.addView(mLock, lockLayoutParams);
		// bottom: loading progress

		// 1) surface view has add as first child in parent
		// 2) progress bar
		LinearLayout playProgressLinearLayout = new LinearLayout(getContext());
		playProgressLinearLayout.setGravity(Gravity.CENTER);
		LinearLayout.LayoutParams timeLP = new LinearLayout.LayoutParams(DimensionUtil.w(150),
				LinearLayout.LayoutParams.WRAP_CONTENT);
		playProgressLinearLayout.addView(mCurrentTime, timeLP);
		playProgressLinearLayout.addView(mSeekBar, new LinearLayout.LayoutParams(0,
				LinearLayout.LayoutParams.WRAP_CONTENT, 1));
		mEndTime.setGravity(Gravity.RIGHT);
		playProgressLinearLayout.addView(mEndTime, timeLP);

		// control bar
		SwitchView startStop = new SwitchView(getContext());
		startStop.of(mPause, mResume);

		SwitchView fullScreen = new SwitchView(getContext());
		fullScreen.of(mEnterFullScreen, mExitFullScreen);

		LinearLayout controlBar = new LinearLayout(getContext());
		controlBar.setGravity(Gravity.CENTER);
		LinearLayout.LayoutParams iconLP = new LinearLayout.LayoutParams(size, size);
		controlBar.addView(mPre, iconLP);
		controlBar.addView(startStop, iconLP);
		controlBar.addView(mNext, iconLP);
		controlBar.addView(fullScreen, iconLP);

		// bottom bar
		LinearLayout bottomBar = new LinearLayout(getContext());
		bottomBar.setOrientation(LinearLayout.VERTICAL);
		bottomBar.setGravity(Gravity.BOTTOM);
		bottomBar.addView(playProgressLinearLayout, LP.LMW);
		bottomBar.addView(controlBar, LP.LMW);

		bottomBar.setBackgroundColor(Theme.alpha(Singleton.of(Theme.class).bgColor(), 100));

		mOverlayLayout = new LinearLayout(getContext());
		mOverlayLayout.setOrientation(LinearLayout.VERTICAL);
		mOverlayLayout.addView(mTitle, LP.LMW);
		mOverlayLayout.addView(mCenterFrameLayout, LP.LW01);
		mOverlayLayout.addView(bottomBar, LP.LMW);
		addView(mOverlayLayout);
		mUnLock.setVisibility(INVISIBLE);
		addView(mUnLock, lockLayoutParams);

		mLoadingBar.setVisibility(INVISIBLE);
	}

	private FrameLayout mCenterFrameLayout;
	private LinearLayout mOverlayLayout;

	private void setupGestureDetector() {
		mGestureDetector = new GestureDetector(getContext(), new OnGestureListener() {

			@Override
			public boolean onSingleTapUp(MotionEvent e) {
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public void onShowPress(MotionEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public void onLongPress(MotionEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public boolean onDown(MotionEvent e) {
				return false;
			}
		});
	}

	private GestureDetector mGestureDetector;

	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		mGestureDetector.onTouchEvent(ev);
		if (mHasOverlay || (mIsLocked && mUnLock.getVisibility() != VISIBLE)) {
			startCheck();
		}
		return super.onInterceptTouchEvent(ev);
	}

	private void setupUIListeners() {
		mLock.setOnClickListener(mOnClickListener);
		mUnLock.setOnClickListener(mOnClickListener);
		setOnClickListener(mOnClickListener);
		//switchToOverlayMode(true);
	}

	private OnClickListener mOnClickListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			if (v.equals(mLock)) {
				switchToLocked();
			} else if (v.equals(mUnLock)) {
				switchToUnLocked();
			} else if (v.equals(DefaultMediaPlayerWidget.this)) {
				// if current is no overlay mode,change to overlay mode
				if (mIsLocked) {
					mUnLock.setVisibility(VISIBLE);
				} else if (!mHasOverlay) {
					switchToOverlayMode(true);
				}
			}
		}
	};

	// start check to hide overlay
	private void startCheck() {
		BaseHandler.removeCallbacks(mCheckRunnable);
		BaseHandler.postDelayed(mCheckRunnable, 5000);
	}

	private void stopCheck() {
		BaseHandler.removeCallbacks(mCheckRunnable);
	}

	private Runnable mCheckRunnable = new Runnable() {
		@Override
		public void run() {
			if (mIsLocked) {
				mUnLock.setVisibility(INVISIBLE);
			} else if (mHasOverlay) {
				switchToNoOverlayMode(true);
			}
		}
	};

	private void switchToNoOverlayMode(boolean stopCheck) {
		mHasOverlay = false;
		mOverlayLayout.setVisibility(INVISIBLE);
		if (stopCheck) {
			stopCheck();
		}
	}

	private void switchToOverlayMode(boolean startCheck) {
		mHasOverlay = true;
		mOverlayLayout.setVisibility(VISIBLE);
		if (startCheck) {
			startCheck();
		}
	}

	private void switchToLocked() {
		mIsLocked = true;
		switchToNoOverlayMode(false);
		mUnLock.setVisibility(VISIBLE);
		startCheck();
	}

	private void switchToUnLocked() {
		mIsLocked = false;
		switchToOverlayMode(true);
		mUnLock.setVisibility(INVISIBLE);
	}

	/*
	 * double click
	 */
	private boolean mIsFloat;
	private boolean mHasOverlay;
	private boolean mIsLocked;
	private boolean mIsLoading;
}
