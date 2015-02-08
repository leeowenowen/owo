package com.example.mediaplayertest;

import android.media.MediaPlayer;

public class MetaData {
	private final Object mMetaData;

	public MetaData(MediaPlayer mp) {
		mMetaData = ReflectHelper.invoke(mp, "getMetadata", false, true);
	}
	
	public boolean exists() {
		return mMetaData != null;
	}

}
