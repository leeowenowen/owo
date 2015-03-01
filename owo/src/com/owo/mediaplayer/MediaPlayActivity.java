package com.owo.mediaplayer;

import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.SurfaceHolder;
import android.view.Window;
import android.view.WindowManager;

import com.owo.app.base.ConfigurableActivity;
import com.owo.app.common.BaseHandler;
import com.owo.app.common.ContextManager;
import com.owo.base.pattern.Instance;
import com.owo.mediaplayer.interfaces.Callback;
import com.owo.mediaplayer.interfaces.IPlayList;
import com.owo.mediaplayer.ui.DefaultMediaPlayerWidget;

public class MediaPlayActivity extends ConfigurableActivity {
	private static final String TAG = "MediaPlayActivity";
	private MediaPlayerController mController;
	private DefaultMediaPlayerWidget mMediaPlayerWidget;
	private IPlayList mPlayList;
	private boolean mIsPaused;
	private int mLastPosition;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.v(TAG, "onCreate");
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);

		// initialize
		ContextManager.init(this);
		BaseHandler.initialize();
		// Debug.waitForDebugger();
		mMediaPlayerWidget = new DefaultMediaPlayerWidget(MediaPlayActivity.this);
		mMediaPlayerWidget.createSurfaceView(new Callback<SurfaceHolder>() {
			@Override
			public void run(SurfaceHolder surface) {
				mController = new MediaPlayerController();
				mMediaPlayerWidget.setMPController(mController);
				final String pathString = getIntent().getStringExtra("path");
				String[] items = pathString.split("##");
				mPlayList = new DefaultPlayList();
				for (String item : items) {
					mPlayList.add(new PlayItem().source(item));
				}
				int index = getIntent().getIntExtra("index", 0);
				mController.create(MediaPlayActivity.this, surface);
				mController.playList(mPlayList, index);
				mController.start();
			}
		});
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
			mLastPosition = mController.current();
			mController.destroy();
			// mController.pause();
			BaseHandler.clear();
		}
		super.onPause();
		Log.v(TAG, "onPause");
	}

	@Override
	protected void onResume() {
		if (mController != null && mIsPaused) {
			mMediaPlayerWidget.createSurfaceView(new Callback<SurfaceHolder>() {
				@Override
				public void run(SurfaceHolder surface) {
					mController.create(MediaPlayActivity.this, surface);
					mController.playList(mPlayList);
					mController.start();
					mController.seek(mLastPosition);
					// mController.resume();
				}
			});
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
		BaseHandler.destroy();
		ContextManager.destroy();
		Instance.destroy();
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
		// TODO Auto-generated method stub
		return super.dispatchKeyEvent(event);
	}
}
