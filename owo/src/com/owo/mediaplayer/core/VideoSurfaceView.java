package com.owo.mediaplayer.core;

import android.content.Context;
import android.content.res.Configuration;
import android.util.Log;
import android.view.SurfaceView;

public class VideoSurfaceView extends SurfaceView {
	private static final String TAG = "VideoSurfaceView";
	protected int mVideoWidth;
	protected int mVideoHeight;

	public VideoSurfaceView(Context context) {
		super(context);
	}

	public void setSize(int w, int h) {
		mVideoWidth = w;
		mVideoHeight = h;
		if (mVideoWidth != 0 && mVideoHeight != 0) {
			//getHolder().setFixedSize(mVideoWidth, mVideoHeight);
			requestLayout();
		}
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {

		} else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {

		}
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int width = getDefaultSize(mVideoWidth, widthMeasureSpec);
		int height = getDefaultSize(mVideoHeight, heightMeasureSpec);
		if (mVideoWidth > 0 && mVideoHeight > 0) {

			int widthSpecMode = MeasureSpec.getMode(widthMeasureSpec);
			int widthSpecSize = MeasureSpec.getSize(widthMeasureSpec);
			int heightSpecMode = MeasureSpec.getMode(heightMeasureSpec);
			int heightSpecSize = MeasureSpec.getSize(heightMeasureSpec);

			if (widthSpecMode == MeasureSpec.EXACTLY
					&& heightSpecMode == MeasureSpec.EXACTLY) {
				// the size is fixed
				width = widthSpecSize;
				height = heightSpecSize;

				// for compatibility, we adjust size based on aspect ratio
				if (mVideoWidth * height < width * mVideoHeight) {
					// Log.i("@@@", "image too wide, correcting");
					width = height * mVideoWidth / mVideoHeight;
				} else if (mVideoWidth * height > width * mVideoHeight) {
					// Log.i("@@@", "image too tall, correcting");
					height = width * mVideoHeight / mVideoWidth;
				}
			} else if (widthSpecMode == MeasureSpec.EXACTLY) {
				// only the width is fixed, adjust the height to match aspect
				// ratio if possible
				width = widthSpecSize;
				height = width * mVideoHeight / mVideoWidth;
				if (heightSpecMode == MeasureSpec.AT_MOST
						&& height > heightSpecSize) {
					// couldn't match aspect ratio within the constraints
					height = heightSpecSize;
				}
			} else if (heightSpecMode == MeasureSpec.EXACTLY) {
				// only the height is fixed, adjust the width to match aspect
				// ratio if possible
				height = heightSpecSize;
				width = height * mVideoWidth / mVideoHeight;
				if (widthSpecMode == MeasureSpec.AT_MOST
						&& width > widthSpecSize) {
					// couldn't match aspect ratio within the constraints
					width = widthSpecSize;
				}
			} else {
				// neither the width nor the height are fixed, try to use actual
				// video size
				width = mVideoWidth;
				height = mVideoHeight;
				if (heightSpecMode == MeasureSpec.AT_MOST
						&& height > heightSpecSize) {
					// too tall, decrease both width and height
					height = heightSpecSize;
					width = height * mVideoWidth / mVideoHeight;
				}
				if (widthSpecMode == MeasureSpec.AT_MOST
						&& width > widthSpecSize) {
					// too wide, decrease both width and height
					width = widthSpecSize;
					height = width * mVideoHeight / mVideoWidth;
				}
			}
		} else {
			// no size yet, just adopt the given spec sizes
		}
		Log.v(TAG, "onMeasure:[width:" + width + "][height:" + height + "]");
		setMeasuredDimension(width, height);
	}
}
