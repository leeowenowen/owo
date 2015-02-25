package com.owo.app.theme;

import java.util.HashSet;
import java.util.Set;

import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;
import android.view.ViewGroup;

import com.owo.app.system_settings.SystemSettingKeys;
import com.owo.app.system_settings.SystemSettingsData;
import com.owo.app.system_settings.SystemSettingsData.Observer;
import com.owo.base.pattern.Instance;

/**
 * <pre>
 * SystemSettingsData --> Theme(reload)-->Activity --> ContentView
 * </pre>
 * 
 * @author leeowenowen@gmail.com
 * 
 */

public class Theme {
	private Theme() {
		Instance.of(SystemSettingsData.class).addObserver(SystemSettingKeys.Theme, new Observer() {
			@Override
			public void onDataChanged(String key, String oldValue, String newValue) {
				for (ThemeObserver observer : mObservers) {
					observer.onThemeChanged();
				}
			}
		});
	}

	private Paint mPaint;
	private Paint mFillPaint;
	private int mTextColor;
	private boolean mInited;

	private void ensureInited() {
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

	public Paint paint() {
		ensureInited();
		return mPaint;
	}

	public Paint fillPaint() {
		ensureInited();
		return mFillPaint;
	}

	public void textColor(int color) {
		mTextColor = color;
	}

	public int textColor() {
		ensureInited();
		return mTextColor;
	}

	private Set<ThemeObserver> mObservers = new HashSet<ThemeObserver>();

	public void addObserver(ThemeObserver observer) {
		mObservers.add(observer);
	}

	public void removeObserver(ThemeObserver observer) {
		mObservers.remove(observer);
	}

	public static void notifyChanged(View v) {
		if (v instanceof ThemeObserver) {
			((ThemeObserver) v).onThemeChanged();
		}
		if (v instanceof ViewGroup) {
			ViewGroup vg = (ViewGroup) v;
			for (int i = 0; i < vg.getChildCount(); ++i) {
				notifyChanged(vg.getChildAt(i));
			}
		}
	}
}
