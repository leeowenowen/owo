package com.owo.app;

import android.app.Activity;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;

import com.owo.app.util.BaseHandler;
import com.owo.mediaplayer.DefaultMediaPlayerWidget;
import com.owo.mediaplayer.MediaPlayerController;
import com.owo.mediastore.DefaultMediaProviderFactory;
import com.owo.mediastore.MediaList;
import com.owo.mediastore.MediaStore;
import com.owo.mediastore.MediaStoreController;
import com.owo.mediastore.MediaStoreWidget;
import com.owo.mediastore.interfaces.MediaType;
import com.owo.mediastore.interfaces.ScaleLevel;

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
		BaseHandler.initialize();

		// mMediaPlayerController = new MediaPlayerController()
		// .timeFormatter(new DefaultTimeFormatter());
		// mMediaPlayerWidget = new DefaultMediaPlayerWidget(this);
		// mMediaPlayerWidget.setMPController(mMediaPlayerController);
		// mMediaPlayerWidget.create(new Callback<SurfaceHolder>() {
		//
		// @Override
		// public void run(SurfaceHolder holder) {
		// mMediaPlayerController.create(DefaultMediaPlayActivity.this,
		// holder);
		// LocalPlayList playList = new LocalPlayList();
		// playList.add(new PlayItem().source(MediaUrls.sLocal));
		// playList.add(new PlayItem().source(MediaUrls.sNetAD));
		// mMediaPlayerController.playList(playList);
		// // mMediaPlayerController.uri(MediaUrls.sRealStream);
		// mMediaPlayerController.start();
		// }
		// });

		// setContentView(mMediaPlayerWidget);
		// setContentView(new TextView(this));
		// new Thread(new Runnable() {
		//
		// @Override
		// public void run() {
		// MediaScanner.start(DefaultMediaPlayActivity.this);
		// }
		// }).start();
		// setContentView(new MediaList(this));
//
//		MediaStore ms = new MediaStore(this);
//		MediaStoreController controller = new MediaStoreController(this,
//				ScaleLevel.sLevel0, MediaType.sIMage);
//		ms.setController(controller);
//		setContentView(ms);
		// TabTest tabTest = new TabTest(this);
		// tabTest.setBackgroundColor(Color.RED);
		// setContentView(tabTest);

		// FrameLayout container = new FrameLayout(this);
		// LinearLayout layout = new LinearLayout(this);
		// layout.setBackgroundColor(Color.BLACK);
		// container.addView(layout,540,960);
		// setContentView(container);
		
		MediaStoreWidget widget = new MediaStoreWidget(this);
		setContentView(widget);
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
