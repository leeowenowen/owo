package com.example.mediaplayertest;

import android.app.Activity;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.SurfaceHolder;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.owo.mediaplayer.MediaPlayerController;
import com.owo.mediaplayer.TestMediaPlayerWidget;
import com.owo.mediaplayer.interfaces.Callback;

public class TestMediaPlayerActivity extends Activity {
	private TestMediaPlayerWidget mMediaPlayerWidget;
	private MediaPlayerController mMediaPlayerController;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);

		// initialize
		ContextManager.init(this);

		mMediaPlayerController = new MediaPlayerController();
		mMediaPlayerWidget = new TestMediaPlayerWidget(this);
		mMediaPlayerWidget.setMPController(mMediaPlayerController);
		mMediaPlayerWidget.create(new Callback<SurfaceHolder>() {

			@Override
			public void run(SurfaceHolder holder) {
				mMediaPlayerController.create(TestMediaPlayerActivity.this,
						holder);
				mMediaPlayerController.uri(MediaUrls.sLocal);
				mMediaPlayerController.start();
			}
		});

		setContentView(mMediaPlayerWidget);
	}
}
