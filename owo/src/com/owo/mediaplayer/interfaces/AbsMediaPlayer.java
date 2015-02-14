package com.owo.mediaplayer.interfaces;

import java.util.HashSet;
import java.util.Set;

public abstract class AbsMediaPlayer implements IMediaPlayer {

	private Set<Listener> mObservers = new HashSet<Listener>();

	@Override
	public void addListener(Listener observer) {
		mObservers.add(observer);
	}

	@Override
	public void removeListener(Listener observer) {
		mObservers.remove(observer);
	}

	protected Set<Listener> observers() {
		return mObservers;
	}
}
