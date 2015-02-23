package com.owo.mediaplayer.view.shape;

import java.util.HashSet;
import java.util.Set;

import android.graphics.Color;
import android.graphics.Paint;

public class Theme {

	private static Paint mPaint;
	private static Paint mFillPaint;
	private static int mTextColor;
	private static boolean mInited;

	private static void ensureInited() {
		if (!mInited) {
			mPaint = new Paint();
			mPaint.setColor(Color.WHITE);
			mPaint.setStrokeWidth(5);
			mPaint.setStyle(Paint.Style.STROKE);
			mPaint.setAntiAlias(true);
			mTextColor = Color.WHITE;
			
			mFillPaint = new Paint();
			mFillPaint.setColor(Color.WHITE);
			mFillPaint.setStyle(Paint.Style.FILL);
			mFillPaint.setAntiAlias(true);
		}
	}

	public static Paint paint() {
		ensureInited();
		return mPaint;
	}
	
	public static Paint fillPaint() {
		ensureInited();
		return mFillPaint;
	}

	public static void textColor(int color) {
		mTextColor = color;
	}

	public static int textColor() {
		ensureInited();
		return mTextColor;
	}

	public static interface Observer {
		void onChanged();
	}

	private static Set<Observer> mObservers = new HashSet<Observer>();

	public static void addObserver(Observer observer) {
		mObservers.add(observer);
	}

	public static void removeObserver(Observer observer) {
		mObservers.remove(observer);
	}

}
