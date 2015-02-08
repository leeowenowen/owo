package com.owo.mediaplayer.android;

import com.owo.mediaplayer.interfaces.IMetaInfo;

public class SysMetaInfo implements IMetaInfo {
	private int mWidth;
	private int mHeight;

	@Override
	public int width() {
		return mWidth;
	}

	@Override
	public int height() {
		return mHeight;
	}

	public void width(int width) {
		mWidth = width;
	}

	public void height(int height) {
		mHeight = height;
	}
}
