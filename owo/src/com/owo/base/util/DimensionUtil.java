package com.owo.base.util;

import android.util.DisplayMetrics;

import com.owo.app.common.ContextManager;

public class DimensionUtil {
	/*
	 * Screen info
	 */
	private static DisplayMetrics sDisplayMetrics = null;

	public static DisplayMetrics displayMetrics() {
		if (sDisplayMetrics == null) {
			sDisplayMetrics = new DisplayMetrics();
			ContextManager.activity().getWindowManager().getDefaultDisplay().getMetrics(sDisplayMetrics);
		}
		return sDisplayMetrics;
	}

	public static int screenWidth() {
		return sDisplayMetrics.widthPixels;
	}

	public static int screenHeight() {
		return sDisplayMetrics.heightPixels;
	}

	public static int dip2Pixel(int dip) {
		return (int) displayMetrics().density * dip;
	}

	public static int sp2Pixel(int sp) {
		return (int) (sp * displayMetrics().scaledDensity);
	}

	public static float pointToPixel(float point) {
		double xdpi = displayMetrics().xdpi;
		return (float) (point * xdpi * (1.0f / 72));
	}

	public static float millimeterToPixel(float mm) {
		double xdpi = displayMetrics().xdpi;
		return (float) (mm * xdpi * (1.0f / 25.4f));
	}
}
