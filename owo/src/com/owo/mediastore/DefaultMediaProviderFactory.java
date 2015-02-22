package com.owo.mediastore;

import java.io.File;
import java.util.Random;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.owo.app.ContextManager;
import com.owo.base.util.BaseHandler;
import com.owo.base.util.MediaUtil;
import com.owo.base.util.SysInfoHelper;
import com.owo.mediaplayer.view.utils.LP;
import com.owo.mediastore.interfaces.IAbsMediaProviderFactory;
import com.owo.mediastore.interfaces.ICursorTransformer;
import com.owo.mediastore.interfaces.IMediaQueryer;
import com.owo.mediastore.interfaces.MediaType;
import com.owo.mediastore.interfaces.ScaleLevel;

public class DefaultMediaProviderFactory implements IAbsMediaProviderFactory {
	private Context mContext;

	public DefaultMediaProviderFactory(Context context) {
		mContext = context;
	}

	@Override
	public IMediaQueryer createMediaQueryer(int mediaType) {
		ContentResolver contentResolver = mContext.getContentResolver();
		switch (mediaType) {
		case MediaType.sVideo:
			return new VideoQueryer(contentResolver);
		case MediaType.sAudio:
			return new AudioQueryer(contentResolver);
		case MediaType.sIMage:
			return new ImageQueryer(contentResolver);
		default:
			return null;
		}
	}

	@Override
	public ICursorTransformer createCursorTransformer(int level, int mediaType) {
		switch (mediaType) {
		case MediaType.sVideo:
			return createVideoCursorTransformer(level);
		case MediaType.sAudio:
			return createAudioCursorTransformer(level);
		case MediaType.sIMage:
			return createImageCursorTransformer(level);
		default:
			return null;
		}
	}

	private ICursorTransformer createVideoCursorTransformer(int level) {
		switch (level) {
		case ScaleLevel.sLevel0: // all width
			return new VideoItemTransformerLevel0();
		case ScaleLevel.sLevel1:// half width
			return new VideoItemTransformerLevel1();
		case ScaleLevel.sLevel2:// half width
			return new VideoItemTransformerLevel2();
		case ScaleLevel.sLevel3:// half width
			return new VideoItemTransformerLevel3();
		default:
			return null;
		}
	}

	private ICursorTransformer createAudioCursorTransformer(int level) {
		switch (level) {
		case ScaleLevel.sLevel0: // all width
			return new AudioItemTransformerLevel0();
		case ScaleLevel.sLevel1:// half width
			return new AudioItemTransformerLevel0();
		case ScaleLevel.sLevel2:// half width
			return new AudioItemTransformerLevel0();
		case ScaleLevel.sLevel3:// half width
			return new AudioItemTransformerLevel0();
		default:
			return null;
		}
	}

	private ICursorTransformer createImageCursorTransformer(int level) {
		switch (level) {
		case ScaleLevel.sLevel0: // all width
			return new ImageItemTransformerLevel0();
		case ScaleLevel.sLevel1:// half width
			return new ImageItemTransformerLevel0();
		case ScaleLevel.sLevel2:// half width
			return new ImageItemTransformerLevel0();
		case ScaleLevel.sLevel3:// half width
			return new ImageItemTransformerLevel0();
		default:
			return null;
		}
	}

	/*
	 * Video Transformer
	 */
	private abstract class VideoItemTransformer implements ICursorTransformer {
		@Override
		public View transform(View convertView, Cursor cursor) {
			AbsVideoItemView view = null;
			if (convertView != null && convertView instanceof AbsVideoItemView) {
				view = (AbsVideoItemView) convertView;
			} else {
				view = createItemView(ContextManager.context());
			}
			view.update(cursor);
			return view;
		}

		protected abstract AbsVideoItemView createItemView(Context context);
	}

	private class VideoItemTransformerLevel0 extends VideoItemTransformer {
		@Override
		protected AbsVideoItemView createItemView(Context context) {
			return new VideoItemViewLevel0(context);
		}
	}

	private class VideoItemTransformerLevel1 extends VideoItemTransformer {
		@Override
		protected AbsVideoItemView createItemView(Context context) {
			return new VideoItemViewLevel1(context);
		}
	}

	private class VideoItemTransformerLevel2 extends VideoItemTransformer {
		@Override
		protected AbsVideoItemView createItemView(Context context) {
			return new VideoItemViewLevel2(context);
		}
	}

	private class VideoItemTransformerLevel3 extends VideoItemTransformer {
		@Override
		protected AbsVideoItemView createItemView(Context context) {
			return new VideoItemViewLevel3(context);
		}
	}

	private abstract class AbsVideoItemView extends LinearLayout {
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
			mTitle.setText(QueryUtil.getColumn(cursor,
					MediaStore.Video.Media.TITLE));
			String size = QueryUtil.getColumn(cursor,
					MediaStore.Video.Media.SIZE);
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
			mThumbnailLayout.setLayoutParams(new LinearLayout.LayoutParams(
					width, height));
		}

		protected abstract void setupLayout(Context context);
	}

	// TODO: configure it
	private static final int sTitleSize = 15;
	private static final int sContentSize = 12;

	private class VideoItemViewLevel0 extends AbsVideoItemView {
		public VideoItemViewLevel0(Context context) {
			super(context);
		}

		@Override
		protected void setupLayout(Context context) {
			mThumbnaiWidth = SysInfoHelper.displayMetrics().widthPixels;
			mThumbnailHeight = SysInfoHelper.displayMetrics().heightPixels;
			mTitle.setTextSize(sTitleSize);
			mSize.setTextSize(sContentSize);
			mDuration.setTextSize(sContentSize);
			mResolution.setTextSize(sContentSize);
			mPath.setTextSize(sContentSize);

			LinearLayout attributes = new LinearLayout(context);
			attributes.addView(mSize, LP.L0W1);
			attributes.addView(mDuration, LP.L0W1);
			attributes.addView(mResolution, LP.L0W1);

			setOrientation(LinearLayout.VERTICAL);
			addView(mTitle);
			addView(mPath);
			addView(attributes);
			addView(mThumbnailLayout);
		}
	}

	private class VideoItemViewLevel1 extends AbsVideoItemView {
		public VideoItemViewLevel1(Context context) {
			super(context);
		}

		@Override
		protected void setupLayout(Context context) {
			mThumbnaiWidth = SysInfoHelper.displayMetrics().widthPixels;
			mThumbnailHeight = SysInfoHelper.displayMetrics().heightPixels;
			mTitle.setTextSize(sTitleSize * 2);
			mSize.setTextSize(sContentSize * 2);
			mDuration.setTextSize(sContentSize);
			mResolution.setTextSize(sContentSize);
			mPath.setTextSize(sContentSize);

			LinearLayout attributes = new LinearLayout(context);
			attributes.addView(mSize, LP.L0W1);
			attributes.addView(mDuration, LP.L0W1);
			attributes.addView(mResolution, LP.L0W1);

			setOrientation(LinearLayout.VERTICAL);
			setPadding(20, 10, 20, 10);
			addView(mThumbnailLayout);
			addView(mTitle);
			addView(mPath);
			addView(attributes);
		}
	}

	private class VideoItemViewLevel2 extends AbsVideoItemView {
		public VideoItemViewLevel2(Context context) {
			super(context);
		}

		@Override
		protected void setupLayout(Context context) {
			mThumbnaiWidth = SysInfoHelper.displayMetrics().widthPixels / 2;
			mThumbnailHeight = SysInfoHelper.displayMetrics().heightPixels;
			mTitle.setTextSize(sTitleSize);
			mSize.setTextSize(sContentSize);
			mDuration.setTextSize(sContentSize);
			mResolution.setTextSize(sContentSize);
			mPath.setTextSize(sContentSize);

			LinearLayout attributes = new LinearLayout(context);
			attributes.addView(mSize, LP.L0W1);
			attributes.addView(mDuration, LP.L0W1);
			attributes.addView(mResolution, LP.L0W1);

			LinearLayout right = new LinearLayout(context);
			right.setOrientation(LinearLayout.VERTICAL);
			right.addView(mTitle);
			right.addView(mPath);
			right.addView(attributes);

			setPadding(20, 10, 20, 10);
			addView(mThumbnailLayout);
			addView(right);
		}
	}

	Random mRandom = new Random(System.currentTimeMillis());

	int randomInt() {
		return mRandom.nextInt();
	}

	private class VideoItemViewLevel3 extends AbsVideoItemView {
		public VideoItemViewLevel3(Context context) {
			super(context);
		}

		@Override
		protected void setupLayout(Context context) {
			mThumbnaiWidth = SysInfoHelper.displayMetrics().widthPixels;
			mThumbnailHeight = SysInfoHelper.displayMetrics().heightPixels;
			mTitle.setTextSize(sTitleSize * 2);
			mSize.setTextSize(sContentSize);
			mDuration.setTextSize(sContentSize);
			mResolution.setTextSize(sContentSize);
			mPath.setTextSize(sContentSize * 2 / 3);

			mSize.setTextColor(Color.WHITE);
			mDuration.setTextColor(Color.WHITE);
			mResolution.setTextColor(Color.WHITE);

			LinearLayout attributes = new LinearLayout(context);
			attributes.setOrientation(LinearLayout.VERTICAL);
			attributes.setBackgroundColor(Color.argb(100, 100, 0, 0));
			attributes.addView(mSize);
			attributes.addView(mDuration);
			attributes.addView(mResolution);

			setOrientation(LinearLayout.VERTICAL);

			mThumbnailLayout.addView(attributes, new FrameLayout.LayoutParams(
					FrameLayout.LayoutParams.WRAP_CONTENT,
					FrameLayout.LayoutParams.WRAP_CONTENT, Gravity.LEFT
							| Gravity.BOTTOM));
			addView(mThumbnailLayout);
			addView(mPath);
			addView(mTitle);
			setPadding(20, 10, 20, 0);
		}
	}

	/*
	 * Audio Transformer
	 */
	private abstract class AbsAudioItemView extends LinearLayout {
		protected TextView mTitle;
		protected TextView mSize;
		protected TextView mDuration;
		protected TextView mPath;

		public AbsAudioItemView(Context context) {
			super(context);

			initComponents(context);
			setupLayout(context);
		}

		private void initComponents(Context context) {
			mTitle = new TextView(context);
			mSize = new TextView(context);
			mDuration = new TextView(context);
			mPath = new TextView(context);
		}

		@SuppressLint("NewApi")
		public void update(Cursor cursor) {
			int duration = cursor.getInt(cursor
					.getColumnIndex(MediaStore.Audio.Media.DURATION));
			mDuration.setText(MediaUtil.format(duration));
			final String path = QueryUtil.getColumn(cursor,
					MediaStore.Audio.Media.DATA);
			mPath.setText(path);
			Log.v("xxx", "audio duration of " + path + "  " + duration);
			mTitle.setText(QueryUtil.getColumn(cursor,
					MediaStore.Audio.Media.TITLE));
			String size = QueryUtil.getColumn(cursor,
					MediaStore.Audio.Media.SIZE);
			if (size == null) {
				size = String.valueOf(new File(path).length());
			}

			mSize.setText(MediaUtil.size(Long.parseLong(size.trim())));
		}

		protected abstract void setupLayout(Context context);
	}

	private abstract class AudioItemTransformer implements ICursorTransformer {
		@Override
		public View transform(View convertView, Cursor cursor) {
			AbsAudioItemView view = null;
			if (convertView != null && convertView instanceof AbsAudioItemView) {
				view = (AbsAudioItemView) convertView;
			} else {
				view = createItemView(ContextManager.context());
			}
			view.update(cursor);
			return view;
		}

		protected abstract AbsAudioItemView createItemView(Context context);
	}

	private class AudioItemViewLevel0 extends AbsAudioItemView {
		public AudioItemViewLevel0(Context context) {
			super(context);
		}

		@Override
		protected void setupLayout(Context context) {
			mTitle.setTextSize(sTitleSize);
			mSize.setTextSize(sContentSize);
			mDuration.setTextSize(sContentSize);
			mPath.setTextSize(sContentSize);

			LinearLayout attributes = new LinearLayout(context);
			attributes.addView(mSize, LP.L0W1);
			attributes.addView(mDuration, LP.L0W1);

			setOrientation(LinearLayout.VERTICAL);
			addView(mTitle);
			addView(mPath);
			addView(attributes);
		}
	}

	private class AudioItemTransformerLevel0 extends AudioItemTransformer {
		@Override
		protected AbsAudioItemView createItemView(Context context) {
			return new AudioItemViewLevel0(context);
		}
	}

	/*
	 * Image Transformer
	 */
	private abstract class AbsImageItemView extends LinearLayout {
		protected TextView mTitle;
		protected TextView mSize;
		protected TextView mWH;
		protected TextView mPath;
		protected ImageView mThumbnail;
		protected ProgressBar mProgressBar;

		protected FrameLayout mThumbnailLayout;

		protected int mThumbnaiWidth;
		protected int mThumbnailHeight;
		private long mMark;

		public AbsImageItemView(Context context) {
			super(context);

			initComponents(context);
			setupLayout(context);
		}

		private void initComponents(Context context) {
			mTitle = new TextView(context);
			mSize = new TextView(context);
			mWH = new TextView(context);
			mPath = new TextView(context);

			mThumbnail = new ImageView(context);
			mProgressBar = new ProgressBar(context);
			mThumbnailLayout = new FrameLayout(context);

			mThumbnailLayout.addView(mProgressBar, LP.FWWC);
			mThumbnailLayout.addView(mThumbnail, LP.FMM);
		}

		@SuppressLint("NewApi")
		public void update(Cursor cursor) {
			String width = QueryUtil.getColumn(cursor,
					MediaStore.Images.Media.WIDTH);
			String height = QueryUtil.getColumn(cursor,
					MediaStore.Images.Media.HEIGHT);
			mWH.setText(width + "x" + height);
			final String path = QueryUtil.getColumn(cursor,
					MediaStore.Images.Media.DATA);
			mPath.setText(path);
			mTitle.setText(QueryUtil.getColumn(cursor,
					MediaStore.Images.Media.TITLE));
			String size = QueryUtil.getColumn(cursor,
					MediaStore.Images.Media.SIZE);
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
						final Bitmap bmp = MediaUtil.createImageThumbnail(path,
								mThumbnaiWidth, mThumbnailHeight, null, null);
						if(bmp == null)
						{
							Log.v("xxx", "error:" + path);
						}
						BaseHandler.post(new Runnable() {

							@SuppressWarnings("deprecation")
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
			int width = flag ? mThumbnaiWidth : (bmp == null ? 0 : bmp.getWidth());
			int height = flag ? mThumbnailHeight : (bmp == null ? 0 : bmp.getHeight());
			mThumbnailLayout.setLayoutParams(new LinearLayout.LayoutParams(
					width, height));
		}

		protected abstract void setupLayout(Context context);
	}

	private abstract class ImageItemTransformer implements ICursorTransformer {
		@Override
		public View transform(View convertView, Cursor cursor) {
			AbsImageItemView view = null;
			if (convertView != null && convertView instanceof AbsImageItemView) {
				view = (AbsImageItemView) convertView;
			} else {
				view = createItemView(ContextManager.context());
			}
			view.update(cursor);
			return view;
		}

		protected abstract AbsImageItemView createItemView(Context context);
	}

	private class ImageItemViewLevel0 extends AbsImageItemView {
		public ImageItemViewLevel0(Context context) {
			super(context);
		}

		@Override
		protected void setupLayout(Context context) {
			mThumbnaiWidth = 300;//SysInfoHelper.displayMetrics().widthPixels;
			mThumbnailHeight = SysInfoHelper.displayMetrics().heightPixels;
			mTitle.setTextSize(sTitleSize);
			mSize.setTextSize(sContentSize);
			mWH.setTextSize(sContentSize);
			mPath.setTextSize(sContentSize);

			LinearLayout attributes = new LinearLayout(context);
			attributes.addView(mSize, LP.L0W1);
			attributes.addView(mWH, LP.L0W1);

			setOrientation(LinearLayout.VERTICAL);
			addView(mTitle);
			addView(mPath);
			addView(attributes);
			addView(mThumbnailLayout);
			
//			mThumbnaiWidth = SysInfoHelper.displayMetrics().widthPixels;
//			mThumbnailHeight = SysInfoHelper.displayMetrics().heightPixels;
//			mTitle.setTextSize(sTitleSize);
//			mSize.setTextSize(sContentSize);
//			mDuration.setTextSize(sContentSize);
//			mResolution.setTextSize(sContentSize);
//			mPath.setTextSize(sContentSize);
//
//			LinearLayout attributes = new LinearLayout(context);
//			attributes.addView(mSize, LP.L0W1);
//			attributes.addView(mDuration, LP.L0W1);
//			attributes.addView(mResolution, LP.L0W1);
//
//			setOrientation(LinearLayout.VERTICAL);
//			addView(mTitle);
//			addView(mPath);
//			addView(attributes);
//			addView(mThumbnailLayout);
		}
	}

	private class ImageItemTransformerLevel0 extends ImageItemTransformer {
		@Override
		protected AbsImageItemView createItemView(Context context) {
			return new ImageItemViewLevel0(context);
		}
	}

}
