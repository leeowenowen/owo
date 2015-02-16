package com.owo.app.util;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Rect;
import android.media.MediaMetadataRetriever;

public class VideoUtil {
	/**
	 * Create a video thumbnail for a video. May return null if the video is
	 * corrupt or the format is not supported.
	 * 
	 * @param filePath
	 *            the path of video file
	 * @param kind
	 *            could be MINI_KIND or MICRO_KIND
	 */
	@SuppressLint("NewApi")
	public static Bitmap createVideoThumbnail(String filePath, int targetWidth,
			int targetHeight, Rect outPaddings, Config config) {
		Bitmap bitmap = null;
		MediaMetadataRetriever retriever = new MediaMetadataRetriever();
		try {
			retriever.setDataSource(filePath);
			bitmap = retriever.getFrameAtTime(-1);
		} catch (IllegalArgumentException ex) {
			// Assume this is a corrupt video file
		} catch (RuntimeException ex) {
			// Assume this is a corrupt video file.
		} finally {
			try {
				retriever.release();
			} catch (RuntimeException ex) {
				// Ignore failures while cleaning up.
			}
		}

		if (bitmap == null)
			return null;

		// Scale down the bitmap if it's too large.
		int width = bitmap.getWidth();
		int height = bitmap.getHeight();
		float whratio = (float) width / (float) height;
		targetHeight = (int) (targetHeight / whratio);

		if (width != targetWidth || height != targetHeight) {
			bitmap = Bitmap.createScaledBitmap(bitmap, targetWidth,
					targetHeight, true);
		}
		return bitmap;
	}
}
