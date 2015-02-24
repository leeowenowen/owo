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

	private boolean mIntercepted;
	private boolean mIsMoveNext;
	private float mXDown;
	private Set<View> mAnimatedViews = new HashSet<View>();

	@Override
	public boolean dispatchTouchEvent(MotionEvent event) {
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			mXDown = event.getX();
			mIntercepted = false;
			Log.v(TAG, "dispatchTouchEvent,ACTION_DOWN");
			break;
		case MotionEvent.ACTION_MOVE:
			float xCur = event.getX();
			if (Math.abs(xCur - mXDown) > 10) {
				if (xCur > mXDown && mCurrentTab > 0) {
					mIntercepted = true;
					movePre(xCur - mXDown);
				} else if (xCur < mXDown && mCurrentTab < mTabSpecs.size() - 1) {
					mIntercepted = true;
					moveNext(mXDown - xCur);
				}
			}
			Log.v(TAG, "dispatchTouchEvent,ACTION_MOVE");
			break;
		case MotionEvent.ACTION_CANCEL:
			Log.v(TAG, "dispatchTouchEvent,ACTION_CANCEL");
			break;
		case MotionEvent.ACTION_UP:
			if (mIntercepted) {
				// finish tab switch
				if (mIsMoveNext) {
					switchToNext();
				} else {
					switchToPre();
				}
			}
			Log.v(TAG, "dispatchTouchEvent,ACTION_UP");
			break;
		default:
			Log.v(TAG, "ACTION " + event.getAction());
			break;
		}
		if (!mIntercepted) {
			super.dispatchTouchEvent(event);
		}
		return true;
	}

	private void movePre(float offset) {
		mIsMoveNext = false;
		View pre = getContentViewAt(mCurrentTab - 1);
		View cur = getContentViewAt(mCurrentTab);
		int width = mTabContent.getWidth();
		pre.setTranslationX(offset - width);
		cur.setTranslationX(offset);
		mAnimatedViews.add(pre);
		mAnimatedViews.add(cur);
	}

	private void moveNext(float offset) {
		mIsMoveNext = true;
		View next = getContentViewAt(mCurrentTab + 1);
		View cur = getContentViewAt(mCurrentTab);
		int width = mTabContent.getWidth();
		next.setTranslationX(width - offset);
		cur.setTranslationX(-offset);
		mAnimatedViews.add(next);
		mAnimatedViews.add(cur);
	}

	private abstract class AnimatorListenerAdapter implements AnimatorListener {

		@Override
		public void onAnimationStart(Animator animation) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onAnimationCancel(Animator animation) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onAnimationRepeat(Animator animation) {
			// TODO Auto-generated method stub

		}
	}

	private void resetAnimatedView() {
		for (View v : mAnimatedViews) {
			v.setTranslationX(0);
		}
	}

	private void switchToPre() {
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

	private void switchToNext() {
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
