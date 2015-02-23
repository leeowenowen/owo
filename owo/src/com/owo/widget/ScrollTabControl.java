package com.owo.widget;

import android.content.Context;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.TranslateAnimation;

public class ScrollTabControl extends owo_TabHost {
	private static final String TAG = "ScrollTabControl";
	private GestureDetector mGestureDetector;

	@SuppressWarnings("deprecation")
	public ScrollTabControl(Context context) {
		super(context);
		mGestureDetector = new GestureDetector(mOnGestureListener);
	}

	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		if (mGestureDetector.onTouchEvent(ev)) {
			return true;
		}
		return super.onInterceptTouchEvent(ev);
	}

	private GestureDetector.OnGestureListener mOnGestureListener = new GestureDetector.SimpleOnGestureListener() {
		public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
			Log.v(TAG, "onScroll" + distanceX);
			float xOff = Math.abs(distanceX);
			if (xOff <= 10) {
				return false;
			}
			if (distanceX > 0 && mCurrentTab < mTabSpecs.size() - 1) {
				switchToNext();
				return true;
			}
			if (distanceX < 0 && mCurrentTab > 0) {
				switchToPre();
				return true;
			}
			return false;
		}

		public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
			Log.v(TAG, "onFling");
			return true;
		}
	};

	private void switchToPre() {
		Log.v(TAG, "switchToPre");
		View pre = getContentViewAt(mCurrentTab - 1);
		View cur = getContentViewAt(mCurrentTab);
		TranslateAnimation in = new TranslateAnimation(-mTabContent.getWidth(), 0, 0, 0);
		in.setDuration(200);
		TranslateAnimation out = new TranslateAnimation(0, mTabContent.getWidth(), 0, 0);
		out.setDuration(200);
		pre.startAnimation(in);
		cur.startAnimation(out);
		in.setAnimationListener(new AnimationListener() {

			@Override
			public void onAnimationStart(Animation animation) {
			}

			@Override
			public void onAnimationRepeat(Animation animation) {
			}

			@Override
			public void onAnimationEnd(Animation animation) {
				setCurrentTab(mCurrentTab - 1);
			}
		});
	}

	private void switchToNext() {
		Log.v(TAG, "switchToPre");
		View next = getContentViewAt(mCurrentTab + 1);
		View cur = getContentViewAt(mCurrentTab);
		TranslateAnimation in = new TranslateAnimation(mTabContent.getWidth(), 0, 0, 0);
		in.setDuration(200);
		TranslateAnimation out = new TranslateAnimation(0, -mTabContent.getWidth(), 0, 0);
		out.setDuration(200);
		cur.startAnimation(out);
		next.startAnimation(in);
		in.setAnimationListener(new AnimationListener() {

			@Override
			public void onAnimationStart(Animation animation) {
			}

			@Override
			public void onAnimationRepeat(Animation animation) {
			}

			@Override
			public void onAnimationEnd(Animation animation) {
				setCurrentTab(mCurrentTab + 1);
			}
		});
	}

}
