package com.owo.mediaplayer.interfaces;

import java.util.Observable;

import android.os.Bundle;

public class ListViewConfig extends Observable {
	private Bundle mData;
	private int mType;

	public Bundle getData() {
		return mData;
	}

	public ListViewConfig data(Bundle data) {
		this.mData = data;
		return this;
	}

	public int getType() {
		return mType;
	}

	public ListViewConfig type(int type) {
		this.mType = type;
		return this;
	}
}
