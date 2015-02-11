package com.owo.mediaplayer.view;

import java.util.HashSet;
import java.util.Set;

import android.graphics.Color;
import android.graphics.Paint;

public class Theme {

	private static final Paint mPaint = new Paint();

	public static Paint paint() {
		mPaint.setColor(Color.RED);
		mPaint.setStrokeWidth(5);
		mPaint.setStyle(Paint.Style.STROKE);
		return mPaint;
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
