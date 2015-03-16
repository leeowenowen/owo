package com.owo.media;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import android.graphics.Bitmap;
import android.os.AsyncTask;

import com.owo.app.common.Check;
import com.owo.app.common.ContextManager;
import com.owo.base.util.BitmapHelper;
import com.owo.base.util.MD5Util;

public class ThumbnailCache implements Serializable {
	private static final long serialVersionUID = 1L;
	private static final String FILE_NAME = "thumbnail_cache";
	private Map<String, CacheItem> mBmps = new HashMap<String, CacheItem>();

	private class CacheItem {
		public Bitmap bitmap;
		public String bmpPath;
		public String videoPath;
	}

	public void load() {
		try {
			ObjectInputStream stream = new ObjectInputStream(new FileInputStream(ContextManager
					.context().getFilesDir() + File.separator + FILE_NAME));
			int size = stream.readInt();
			for (int i = 0; i < size; ++i) {
				CacheItem item = new CacheItem();
				item.videoPath = stream.readUTF();
				item.bmpPath = stream.readUTF();
				mBmps.put(item.videoPath, item);
			}
			stream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void save() {

		try {
			ObjectOutputStream stream = new ObjectOutputStream(new FileOutputStream(ContextManager
					.context().getFilesDir() + File.separator + FILE_NAME));
			stream.writeInt(mBmps.size());
			for (CacheItem item : mBmps.values()) {
				stream.writeUTF(item.videoPath);
				stream.writeUTF(item.bmpPath);
			}
			stream.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void add(String key, final Bitmap bitmap) {
		CacheItem item = mBmps.get(key);
		Check.d(item == null);
		if (item != null) {
			item.bitmap = bitmap;
		} else {
			item = new CacheItem();
			item.bitmap = bitmap;
			item.videoPath = key;
			item.bmpPath = ContextManager.context().getFilesDir() + File.separator
					+ MD5Util.MD5(key);
			mBmps.put(key, item);
			new AsyncTask<CacheItem, Void, Void>() {
				@Override
				protected Void doInBackground(CacheItem... params) {
					BitmapHelper.saveBitmap(bitmap, params[0].bmpPath);
					return null;
				}
			}.execute(item);
		}
	}

	public void clear() {
		mBmps.clear();
	}

	public boolean contains(String key) {
		return mBmps.containsKey(key);
	}

	public Bitmap get(String key) {
		CacheItem item = mBmps.get(key);
		if (item == null) {
			return null;
		}
		if (item.bitmap != null) {
			return item.bitmap;
		}
		item.bitmap = BitmapHelper.loadBitmap(item.bmpPath);
		return item.bitmap;
	}

}
