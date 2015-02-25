package com.owo.app.theme;

import android.graphics.Color;

public class DefaultThemeProvider implements IThemeProvider {

	private int mPaintColor;
	private int mTextColor;
	private int mBgColor;

	public DefaultThemeProvider() {
		mPaintColor = Color.BLACK;
		mTextColor = Color.BLACK;
		mBgColor = Color.WHITE;
	}

	public DefaultThemeProvider paintColor(int color) {
		mPaintColor = color;
		return this;
	}

	public DefaultThemeProvider textColor(int color) {
		mTextColor = color;
		return this;
	}

	public DefaultThemeProvider bgColor(int color) {
		mBgColor = color;
		return this;
	}

	@Override
	public int paintColor() {
		return mPaintColor;
	}

	@Override
	public int textColor() {
		return mTextColor;
	}

	@Override
	public int bgColor() {
		return mBgColor;
	}

}
