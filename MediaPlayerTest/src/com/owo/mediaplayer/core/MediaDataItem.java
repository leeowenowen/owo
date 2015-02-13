package com.owo.mediaplayer.core;

public class MediaDataItem {

	private int mVideoWidth;
	private int mVideoHeight;

	public int height() {
		return mVideoHeight;
	}

	public int width() {
		return mVideoWidth;
	}

	public void height(int height) {
		mVideoHeight = height;
	}

	public void width(int width) {
		mVideoWidth = width;
	}
}
