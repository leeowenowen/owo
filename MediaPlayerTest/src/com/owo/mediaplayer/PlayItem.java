package com.owo.mediaplayer;

import com.owo.mediaplayer.interfaces.IPlayItem;

public class PlayItem implements IPlayItem {

	private int mWidth;
	private int mHeight;
	private String mSource;

	@Override
	public int width() {
		return mWidth;
	}

	public PlayItem width(int width) {
		mWidth = width;
		return this;
	}

	@Override
	public int height() {
		return mHeight;
	}

	public PlayItem height(int height) {
		mHeight = height;
		return this;
	}

	@Override
	public String source() {
		return mSource;
	}

	public PlayItem source(String source) {
		mSource = source;
		return this;
	}

}
