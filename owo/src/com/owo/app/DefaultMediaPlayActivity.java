package com.owo.app;

import android.app.Activity;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.SurfaceHolder;
import android.view.Window;
import android.view.WindowManager;

import com.owo.mediaplayer.DefaultMediaPlayerWidget;
import com.owo.mediaplayer.DefaultTimeFormatter;
import com.owo.mediaplayer.LocalPlayList;
import com.owo.mediaplayer.MediaPlayerController;
import com.owo.mediaplayer.PlayItem;
import com.owo.mediaplayer.interfaces.Callback;

public class DefaultMediaPlayActivity extends Activity {
	private DefaultMediaPlayerWidget mMediaPlayerWidget;
	private MediaPlayerController mMediaPlayerController;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);

		// initialize
		ContextManager.init(this);

		mMediaPlayerController = new MediaPlayerController()
				.timeFormatter(new DefaultTimeFormatter());
		mMediaPlayerWidget = new DefaultMediaPlayerWidget(this);
		mMediaPlayerWidget.setMPController(mMediaPlayerController);
		mMediaPlayerWidget.create(new Callback<SurfaceHolder>() {

			@Override
			public void run(SurfaceHolder holder) {
				mMediaPlayerController.create(DefaultMediaPlayActivity.this,
						holder);
				LocalPlayList playList = new LocalPlayList();
				playList.add(new PlayItem().source(MediaUrls.sLocal));
				playList.add(new PlayItem().source(MediaUrls.sNetAD));
				mMediaPlayerController.playList(playList);
				// mMediaPlayerController.uri(MediaUrls.sRealStream);
				mMediaPlayerController.start();
			}
		});

		setContentView(mMediaPlayerWidget);
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		if (mMediaPlayerController == null) {
			return;
		}
		mMediaPlayerController.onConfigurationChanged(newConfig);

	}
}
