package com.owo.widget;

import java.util.HashSet;
import java.util.Set;

import android.animation.Animator;
import android.animation.Animator.AnimatorListener;
import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.content.Context;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

public class ScrollTabControl extends owo_TabHost {
	private static final String TAG = "ScrollTabControl";

	public ScrollTabControl(Context context) {
		super(context);
	}

	private static final int STATE_NONE = 0;
	private static final int STATE_MOVE_PRE = 1;
	private static final int STATE_MOVE_NEXT = 2;
	private static final int STATE_FLING_TO_PRE = 3;
	private static final int STATE_FLING_TO_NEXT = 4;

	private int mState = STATE_NONE;

	private void switchState(int state) {
		switch (state) {
		case STATE_MOVE_NEXT:
			if (mState == STATE_MOVE_PRE) {
				movePre(0);
			}
			moveNext(mXDown - mXCur);
			break;
		case STATE_MOVE_PRE:
			if (mState == STATE_MOVE_NEXT) {
				moveNext(0);
			}
			movePre(mXCur - mXDown);
			break;
		case STATE_FLING_TO_NEXT:
			flingToNext();
			break;
		case STATE_FLING_TO_PRE:
			flingToPre();
			break;
		default:
			break;
		}
		mState = state;
	}

	private float mXDown, mYDown, mXCur, mYCur;
	private Set<View> mAnimatedViews = new HashSet<View>();

	@Override
	public boolean dispatchTouchEvent(MotionEvent event) {
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			mXDown = event.getX();
			mYDown = event.getY();
			switchState(STATE_NONE);
			Log.v(TAG, "down:" + mXDown);
			break;
		case MotionEvent.ACTION_MOVE:
			mXCur = event.getX();
			mYCur = event.getY();
			Log.v(TAG, "move:" + mXCur);
			Log.v(TAG, "move:" + mXDown);
			float xOffset = mXCur - mXDown;
			float yOffset = mYCur - mYDown;
			Log.v(TAG, "move: [xoffset:" + xOffset + "][yoffset:" + yOffset + "]");
			float absXOffset = Math.abs(xOffset);
			float absYOffset = Math.abs(yOffset);
			if (absXOffset > absYOffset && absXOffset > 10) {
				if (xOffset > 0 && mCurrentTab > 0) {
					switchState(STATE_MOVE_PRE);
				} else if (xOffset < 0 && mCurrentTab < mTabSpecs.size() - 1) {
					switchState(STATE_MOVE_NEXT);
				}
			}
			break;
		case MotionEvent.ACTION_CANCEL:
			Log.v(TAG, "dispatchTouchEvent,ACTION_CANCEL");
			break;
		case MotionEvent.ACTION_UP:
			if (mState == STATE_MOVE_NEXT) {
				switchState(STATE_FLING_TO_NEXT);
			} else if (mState == STATE_MOVE_PRE) {
				switchState(STATE_FLING_TO_PRE);
			}
			Log.v(TAG, "dispatchTouchEvent,ACTION_UP");
			break;
		default:
			Log.v(TAG, "ACTION " + event.getAction());
			break;
		}
		if (mState == STATE_NONE) {
			super.dispatchTouchEvent(event);
		}
		return true;
	}

	private void movePre(float offset) {
		Log.v(TAG, "move_pre:" + offset);
		View pre = getContentViewAt(mCurrentTab - 1);
		View cur = getContentViewAt(mCurrentTab);
		int width = mTabContent.getWidth();
		pre.setTranslationX(offset - width);
		cur.setTranslationX(offset);
		mAnimatedViews.add(pre);
		mAnimatedViews.add(cur);
	}

	private void moveNext(float offset) {
		Log.v(TAG, "move_next:" + offset);
		View next = getContentViewAt(mCurrentTab + 1);
		View cur = getContentViewAt(mCurrentTab);
		if(next == null || cur == null)
		{
			Log.v(TAG, "invalid param");
		}
		int width = mTabContent.getWidth();
		next.setTranslationX(width - offset);
		cur.setTranslationX(-offset);
		mAnimatedViews.add(next);
		mAnimatedViews.add(cur);
	}

	private abstract class AnimatorListenerAdapter implements AnimatorListener {

		@Override
		public void onAnimationStart(Animator animation) {
		}

		@Override
		public void onAnimationCancel(Animator animation) {
		}

		@Override
		public void onAnimationRepeat(Animator animation) {
		}
	}

	private void resetAnimatedView() {
		for (View v : mAnimatedViews) {
			v.setTranslationX(0);
		}
	}

	private void flingToPre() {
		final View pre = getContentViewAt(mCurrentTab - 1);
		final View cur = getContentViewAt(mCurrentTab);
		Log.v(TAG, "switchToPre:" + cur.getTranslationX());

		ValueAnimator animator = ValueAnimator.ofFloat(cur.getTranslationX(), mTabContent.getWidth()).setDuration(200);
		animator.addUpdateListener(new AnimatorUpdateListener() {
			@Override
			public void onAnimationUpdate(ValueAnimator animation) {
				float translationValue = (Float) animation.getAnimatedValue();
				Log.v(TAG, "onAnimationUpdate, value:" + translationValue);
				pre.setTranslationX(translationValue - mTabContent.getWidth());
				cur.setTranslationX(translationValue);
			}
		});
		animator.addListener(new AnimatorListenerAdapter() {
			@Override
			public void onAnimationEnd(Animator animation) {
				setCurrentTab(mCurrentTab - 1);
				resetAnimatedView();
			}
		});
		animator.start();
		mAnimatedViews.add(pre);
		mAnimatedViews.add(cur);
	}

	private void flingToNext() {
		final View next = getContentViewAt(mCurrentTab + 1);
		final View cur = getContentViewAt(mCurrentTab);
		Log.v(TAG, "switchToNext:" + next.getTranslationX());

		ValueAnimator animator = ValueAnimator.ofFloat(next.getTranslationX(), 0).setDuration(200);
		animator.addUpdateListener(new AnimatorUpdateListener() {
			@Override
			public void onAnimationUpdate(ValueAnimator animation) {
				float translationValue = (Float) animation.getAnimatedValue();
				Log.v(TAG, "onAnimationUpdate, value:" + translationValue);
				cur.setTranslationX(translationValue - mTabContent.getWidth());
				next.setTranslationX(translationValue);
			}
		});
		animator.addListener(new AnimatorListenerAdapter() {
			@Override
			public void onAnimationEnd(Animator animation) {
				setCurrentTab(mCurrentTab + 1);
				resetAnimatedView();
			}
		});
		animator.start();
		mAnimatedViews.add(next);
		mAnimatedViews.add(cur);
	}

}
