package com.owo.app;

import android.app.Activity;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Debug;
import android.util.Log;
import android.view.KeyEvent;
import android.view.SurfaceHolder;
import android.view.Window;
import android.view.WindowManager;

import com.owo.base.util.BaseHandler;
import com.owo.mediaplayer.DefaultMediaPlayerWidget;
import com.owo.mediaplayer.DefaultPlayList;
import com.owo.mediaplayer.MediaPlayerController;
import com.owo.mediaplayer.PlayItem;
import com.owo.mediaplayer.interfaces.Callback;
import com.owo.mediaplayer.interfaces.IPlayList;

public class MediaPlayActivity extends Activity {
	private static final String TAG = "MediaPlayActivity";
	private MediaPlayerController mController;
	private IPlayList mPlayList;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

		// initialize
		ContextManager.init(this);
		BaseHandler.initialize();
		Debug.waitForDebugger();

		mController = new MediaPlayerController();
		DefaultMediaPlayerWidget widget = new DefaultMediaPlayerWidget(this);
		widget.setMPController(mController);
		String path = getIntent().getStringExtra("path");
		mPlayList = new DefaultPlayList();
		mPlayList.add(new PlayItem().source(path));

		widget.create(new Callback<SurfaceHolder>() {
			@Override
			public void run(SurfaceHolder surface) {
				mController.create(MediaPlayActivity.this, surface);
				mController.playList(mPlayList);
				mController.start();
			}
		});
		setContentView(widget);
	}

	@Override
	protected void onStart() {
		super.onStart();
	}

	@Override
	protected void onStop() {
		super.onStop();
	}

	@Override
	protected void onPause() {
		super.onPause();
	}

	@Override
	protected void onResume() {
		super.onResume();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		Log.v(TAG, "onDestroy");
		BaseHandler.destroy();
		ContextManager.destroy();
		mController.destroy();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
	}

	@Override
	public boolean dispatchKeyEvent(KeyEvent event) {
		// TODO Auto-generated method stub
		return super.dispatchKeyEvent(event);
	}
}