package com.owo.media.image;

import java.io.File;
import java.util.Random;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory.Options;
import android.graphics.drawable.BitmapDrawable;
import android.provider.MediaStore;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ImageView.ScaleType;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;
import com.owo.app.theme.ThemeObserver;
import com.owo.app.util.ImageLoadUtil;
import com.owo.base.util.MediaUtil;
import com.owo.media.QueryUtil;
import com.owo.ui.utils.LP;

abstract class AbsImageItemView extends LinearLayout implements ThemeObserver {
	protected TextView mTitle;
	protected TextView mSize;
	protected TextView mWH;
	protected TextView mPath;
	protected ImageView mThumbnail;
	// protected ProgressBar mProgressBar;

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
		mThumbnailLayout = new FrameLayout(context);

		mThumbnailLayout.addView(mThumbnail, LP.FMM);
		mPath.setVisibility(View.GONE);
	}

	private static Options sOptions = new Options();

	public void update(Cursor cursor) {
		String width = QueryUtil.getColumn(cursor, "width");// MediaStore.Images.Media.WIDTH
		String height = QueryUtil.getColumn(cursor, "height");// MediaStore.Images.Media.HEIGHT
		mWH.setText(width + "x" + height);
		String path = QueryUtil.getColumn(cursor, MediaStore.Images.Media.DATA);
		mPath.setText(path);
		mTitle.setText(QueryUtil.getColumn(cursor,
				MediaStore.Images.Media.TITLE));
		String size = QueryUtil.getColumn(cursor, MediaStore.Images.Media.SIZE);
		if (size == null) {
			size = String.valueOf(new File(path).length());
		}

		mSize.setText(MediaUtil.size(Long.parseLong(size.trim())));
		if (path.startsWith("/")) {
			path = "file://" + path;
		}
		mMark++;
		final long thisLoadMark = mMark;
		mThumbnail.setImageBitmap(null);
		sOptions.outHeight = thumbnailHeight();
		sOptions.outWidth = thumbnailWidth();
		DisplayImageOptions options = ImageLoadUtil.option(thumbnailWidth(),
				thumbnailHeight());
		ImageLoader.getInstance().loadImage(path, options,
				new SimpleImageLoadingListener() {
					@Override
					public void onLoadingComplete(String imageUri, View view,
							Bitmap loadedImage) {
						if (thisLoadMark == mMark) {
							mThumbnail.setImageBitmap(loadedImage);
						}
					}
				});
	}

	protected abstract void setupLayout(Context context);

	protected abstract int thumbnailWidth();

	protected abstract int thumbnailHeight();
}
