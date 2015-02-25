package com.owo.media.audio;

import java.io.File;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.owo.app.theme.ThemeObserver;
import com.owo.base.util.MediaUtil;
import com.owo.media.QueryUtil;

abstract class AbsAudioItemView extends LinearLayout implements ThemeObserver {
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
		int duration = cursor.getInt(cursor.getColumnIndex(MediaStore.Audio.Media.DURATION));
		mDuration.setText(MediaUtil.format(duration));
		final String path = QueryUtil.getColumn(cursor, MediaStore.Audio.Media.DATA);
		mPath.setText(path);
		Log.v("xxx", "audio duration of " + path + "  " + duration);
		mTitle.setText(QueryUtil.getColumn(cursor, MediaStore.Audio.Media.TITLE));
		String size = QueryUtil.getColumn(cursor, MediaStore.Audio.Media.SIZE);
		if (size == null) {
			size = String.valueOf(new File(path).length());
		}

		mSize.setText(MediaUtil.size(Long.parseLong(size.trim())));
	}

	protected abstract void setupLayout(Context context);
}
