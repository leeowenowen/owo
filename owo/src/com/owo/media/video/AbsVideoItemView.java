package com.owo.media.video;

import java.io.File;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.owo.base.util.BaseHandler;
import com.owo.base.util.MediaUtil;
import com.owo.media.QueryUtil;
import com.owo.media.ThumbnailCache;
import com.owo.mediaplayer.view.utils.LP;

abstract class AbsVideoItemView extends LinearLayout {
	protected TextView mTitle;
	protected TextView mSize;
	protected TextView mDuration;
	protected TextView mResolution;
	protected TextView mPath;
	protected ImageView mThumbnail;
	protected ProgressBar mProgressBar;

	protected FrameLayout mThumbnailLayout;

	protected int mThumbnaiWidth;
	protected int mThumbnailHeight;
	private long mMark;

	public AbsVideoItemView(Context context) {
		super(context);

		initComponents(context);
		setupLayout(context);
	}

	private void initComponents(Context context) {
		mTitle = new TextView(context);
		mSize = new TextView(context);
		mDuration = new TextView(context);
		mResolution = new TextView(context);
		mPath = new TextView(context);
		mThumbnail = new ImageView(context);
		mProgressBar = new ProgressBar(context);
		mThumbnailLayout = new FrameLayout(context);

		mThumbnailLayout.addView(mProgressBar, LP.FWWC);
		mThumbnailLayout.addView(mThumbnail, LP.FMM);
	}

	@SuppressWarnings("deprecation")
	@SuppressLint("NewApi")
	public void update(Cursor cursor) {
		int duration = cursor.getInt(cursor
				.getColumnIndex(MediaStore.Video.Media.DURATION));
		mDuration.setText(MediaUtil.format(duration));
		mResolution.setText(QueryUtil.getColumn(cursor,
				MediaStore.Video.Media.RESOLUTION));
		final String path = QueryUtil.getColumn(cursor,
				MediaStore.Video.Media.DATA);
		mPath.setText(path);
		mTitle.setText(QueryUtil
				.getColumn(cursor, MediaStore.Video.Media.TITLE));
		String size = QueryUtil.getColumn(cursor, MediaStore.Video.Media.SIZE);
		if (size == null) {
			size = String.valueOf(new File(path).length());
		}

		mSize.setText(MediaUtil.size(Long.parseLong(size.trim())));

		Bitmap bmp = ThumbnailCache.get(path);
		if (bmp == null) {
			loading(true, bmp);
			AsyncTask.THREAD_POOL_EXECUTOR.execute(new Runnable() {
				private long mSelfMark;

				public Runnable mark(long mark) {
					mSelfMark = mark;
					return this;
				}

				@Override
				public void run() {
					final Bitmap bmp = MediaUtil.createVideoThumbnail(path,
							mThumbnaiWidth, mThumbnailHeight, null, null);
					BaseHandler.post(new Runnable() {

						@Override
						public void run() {
							ThumbnailCache.add(path, bmp);
							if (mSelfMark == mMark) {
								loading(false, bmp);
								mThumbnail
										.setBackgroundDrawable(new BitmapDrawable(
												bmp));
							}
						}
					});
				}
			}.mark(++mMark));
		} else {
			loading(false, bmp);
			mThumbnail.setBackgroundDrawable(new BitmapDrawable(bmp));
		}
	}

	private void loading(boolean flag, Bitmap bmp) {
		mThumbnail.setVisibility(flag ? INVISIBLE : VISIBLE);
		mProgressBar.setVisibility(flag ? VISIBLE : INVISIBLE);
		int width = flag ? mThumbnaiWidth : bmp.getWidth();
		int height = flag ? mThumbnailHeight : bmp.getHeight();
		mThumbnailLayout.setLayoutParams(new LinearLayout.LayoutParams(width,
				height));
	}

	protected abstract void setupLayout(Context context);
}