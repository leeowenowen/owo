package com.owo.app.theme;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.owo.app.system_settings.SystemSettingKeys;
import com.owo.app.system_settings.SystemSettingsData;
import com.owo.app.system_settings.SystemSettingsData.Observer;
import com.owo.base.pattern.Instance;
import com.owo.base.util.DimensionUtil;

/**
 * <pre>
 * SystemSettingsData --> Theme(reload)-->Activity --> ContentView
 * </pre>
 * 
 * @author leeowenowen@gmail.com
 * 
 */

public class Theme {
	private Paint mPaint;
	private Paint mFillPaint;

	private IThemeProvider mThemeProvider;
	private final Map<String, IThemeProvider> mThemeProviders = new HashMap<String, IThemeProvider>();

	private Theme() {
		// 1) initialize provider
		mThemeProviders.put(String.valueOf(Color.WHITE), new DefaultThemeProvider());
		mThemeProviders.put(
				String.valueOf(Color.BLACK),
				new DefaultThemeProvider().textColor(Color.WHITE).paintColor(Color.WHITE)
						.bgColor(Color.BLACK));
		mThemeProviders.put(String.valueOf(Color.RED),
				new DefaultThemeProvider().textColor(Color.YELLOW).paintColor(Color.YELLOW)
						.bgColor(Color.RED));
		mThemeProviders.put(
				String.valueOf(Color.BLUE),
				new DefaultThemeProvider().textColor(Color.WHITE).paintColor(Color.WHITE)
						.bgColor(Color.BLUE));
		mThemeProviders.put(
				String.valueOf(Color.GREEN),
				new DefaultThemeProvider().textColor(Color.WHITE).paintColor(Color.WHITE)
						.bgColor(Color.GREEN));
		initProvider();
		// 2) initialize paint
		mPaint = new Paint();
		mPaint.setColor(mThemeProvider.paintColor());
		mPaint.setStrokeWidth(DimensionUtil.w(5));
		mPaint.setStyle(Paint.Style.STROKE);
		mPaint.setAntiAlias(true);

		mFillPaint = new Paint();
		mFillPaint.setColor(mThemeProvider.paintColor());
		mFillPaint.setStyle(Paint.Style.FILL);
		mFillPaint.setAntiAlias(true);

		// 3) register observer
		Instance.of(SystemSettingsData.class).addObserver(SystemSettingKeys.Theme, new Observer() {
			@Override
			public void onDataChanged(String key, String oldValue, String newValue) {
				mThemeProvider = mThemeProviders.get(newValue);
				assert (mThemeProvider != null);
				mPaint.setColor(mThemeProvider.paintColor());
				mFillPaint.setColor(mThemeProvider.paintColor());
				for (ThemeObserver observer : mObservers) {
					observer.onThemeChanged();
				}
			}
		});
	}

	private void initProvider() {
		mThemeProvider = mThemeProviders.get(String.valueOf(Color.WHITE));
	}

	public Paint paint() {
		return mPaint;
	}

	public Paint fillPaint() {
		return mFillPaint;
	}

	public int textColor() {
		return mThemeProvider.textColor();
	}

	public int bgColor() {
		// int bgColor = mThemeProvider.bgColor();
		// return Color.argb(100, Color.red(bgColor), Color.green(bgColor),
		// Color.blue(bgColor));
		return mThemeProvider.bgColor();
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

	public static void updateTheme(View v) {
		int textColor = Instance.of(Theme.class).textColor();
		int bgColor = Instance.of(Theme.class).bgColor();
		if (!(v instanceof ImageView)) {
			v.setBackgroundColor(bgColor);
		}
		if (v instanceof TextView) {
			TextView tView = (TextView) v;
			tView.setTextColor(textColor);
		}
		if (v instanceof ViewGroup) {
			ViewGroup vg = (ViewGroup) v;
			for (int i = 0; i < vg.getChildCount(); ++i) {
				updateTheme(vg.getChildAt(i));
			}
		}
	}
}
