package com.owo.media;

import java.util.HashSet;
import java.util.Set;

import android.text.TextUtils;

public class MediaStoreData {
	private int mType;
	private int mLevel;
	private String mSearchText;

	public MediaStoreData type(int type) {
		mType = type;
		return this;
	}

	public MediaStoreData level(int level) {
		mLevel = level;
		return this;
	}

	public void searchText(String searchText) {
		if (TextUtils.equals(mSearchText, searchText)) {
			return;
		}
		mSearchText = searchText;
		for (Listener listener : mListeners) {
			listener.onSearchTextChanged(mSearchText);
		}
	}

	public static interface Listener {
		void onTypeChanged(int type);

		void onLevelChanged(int level);

		void onSearchTextChanged(String text);
	}

	private Set<Listener> mListeners = new HashSet<Listener>();

	public void addListener(Listener l) {
		mListeners.add(l);
	}

	public void removeListener(Listener l) {
		mListeners.remove(l);
	}
}
