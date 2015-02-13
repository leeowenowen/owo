package com.owo.mediaplayer.interfaces;

public interface IPlayList {
	int size();

	IPlayItem at(int index);

	void remove(int index);

	void remove(IPlayItem item);

	void add(IPlayItem item);
}
