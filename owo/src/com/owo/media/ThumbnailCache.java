package com.owo.media;

import java.util.HashMap;
import java.util.Map;

import android.graphics.Bitmap;

public class ThumbnailCache {
	private static Map<String, Bitmap> mBmps = new HashMap<String, Bitmap>();

	public static void add(String key, Bitmap value) {
		mBmps.put(key, value);
	}

	public static void clear() {
		mBmps.clear();
	}

	public static Bitmap get(String key) {
		return mBmps.get(key);
	}
}
