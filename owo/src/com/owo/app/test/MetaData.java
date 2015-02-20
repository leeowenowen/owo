package com.owo.app.test;

import android.media.MediaPlayer;

public class MetaData {
	private  Object mMetaData;

	public MetaData(MediaPlayer mp) {
		//mMetaData = ReflectHelper.invoke(mp, "getMetadata", false, true);
	}
	
	public boolean exists() {
		return mMetaData != null;
	}

}
