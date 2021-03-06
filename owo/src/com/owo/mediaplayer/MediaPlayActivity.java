package com.owo.mediaplayer;

import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.SurfaceHolder;

import com.owo.app.base.ConfigurableActivity;
import com.owo.app.common.BaseHandler;
import com.owo.app.common.ContextManager;
import com.owo.base.common.Callback;
import com.owo.base.pattern.Singleton;
import com.owo.mediaplayer.interfaces.IPlayList;
import com.owo.mediaplayer.ui.DefaultMediaPlayerWidget;

public class MediaPlayActivity extends ConfigurableActivity {
	private static final String TAG = "MediaPlayActivity";
	private MediaPlayerController mController;
	private DefaultMediaPlayerWidget mMediaPlayerWidget;
	private IPlayList mPlayList;
	private boolean mIsPaused;

	// private int mLastPosition;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.v(TAG, "onCreate");

		// Debug.waitForDebugger();
		mMediaPlayerWidget = new DefaultMediaPlayerWidget(
				MediaPlayActivity.this);
		mMediaPlayerWidget.createSurfaceView(new Callback<SurfaceHolder>() {
			@Override
			public void run(SurfaceHolder surface) {
				mController = new MediaPlayerController();
				mController.create();
				mMediaPlayerWidget.setMPController(mController);
				final String pathString = getIntent().getStringExtra(
						"path_title");
				String[] items = pathString.split("####");
				mPlayList = new DefaultPlayList();
				for (String item : items) {
					Log.v("xxx", "############################" + item);
					String[] path_title = item.split("@@@@");
					mPlayList.add(new PlayItem().source(path_title[0]).title(
							path_title[1]));
				}
				int index = getIntent().getIntExtra("index", 0);
				mController.playList(mPlayList, index);
				mController.start();
			}
		});
		// WindowManager wm = (WindowManager)getSystemService(WINDOW_SERVICE);
		// WindowManager.LayoutParams lParams = new
		// WindowManager.LayoutParams(300, 300, LayoutParams.TYPE_PHONE, 0, 0);
		// wm.addView(mMediaPlayerWidget, lParams);
		// TextView tView = new TextView(this);
		// tView.setBackgroundColor(Color.RED);
		// setContentView(tView);
		// setVisible(false);
		setContentView(mMediaPlayerWidget);
	}

	@Override
	protected void onStart() {
		super.onStart();
		Log.v(TAG, "onStart");
	}

	@Override
	protected void onStop() {
		super.onStop();
		Log.v(TAG, "onStop");
	}

	@Override
	protected void onPause() {
		if (mController != null) {
			mIsPaused = true;
			// mLastPosition = mController.current();
			// mController.destroy();
			mController.pause();
			// BaseHandler.clear();
		}
		super.onPause();
		Log.v(TAG, "onPause");
	}

	@Override
	protected void onResume() {
		if (mController != null && mIsPaused) {
			mController.resume();
			// mController.start();
			// mController.seek(mLastPosition);
			// mMediaPlayerWidget.createSurfaceView(new
			// Callback<SurfaceHolder>() {
			// @Override
			// public void run(SurfaceHolder surface) {
			// mController.create();
			// mController.playList(mPlayList);
			// mController.start();
			// mController.seek(mLastPosition);
			// }
			// });
		}
		mIsPaused = false;
		super.onResume();
		Log.v(TAG, "onResume");
	}

	/**
	 * Has destroy {@link #mController} in {@link #onPause()} so there's no need
	 * to re-destroy again
	 */
	@Override
	protected void onDestroy() {
		super.onDestroy();
		Log.v(TAG, "onDestroy");
		super.onDestroy();
		BaseHandler.destroy();
		ContextManager.destroy();
		Singleton.destroy();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		if (mController != null) {
			mController.onConfigurationChanged(newConfig);
		}
	}

	@Override
	public boolean dispatchKeyEvent(KeyEvent event) {
		return super.dispatchKeyEvent(event);
	}
}
