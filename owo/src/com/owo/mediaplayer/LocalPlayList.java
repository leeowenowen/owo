package com.owo.mediaplayer;

import java.util.ArrayList;
import java.util.List;

import com.owo.mediaplayer.interfaces.IPlayItem;
import com.owo.mediaplayer.interfaces.IPlayList;

public class LocalPlayList implements IPlayList {

	private List<IPlayItem> mItems = new ArrayList<IPlayItem>();

	@Override
	public int size() {
		return mItems.size();
	}

	@Override
	public IPlayItem at(int index) {
		return mItems.get(index);
	}

	@Override
	public void remove(int index) {
		mItems.remove(index);
	}

	@Override
	public void remove(IPlayItem item) {
		mItems.remove(item);
	}

	@Override
	public void add(IPlayItem item) {
		mItems.add(item);
	}

}
