package com.owo.app.main;

import android.content.res.Configuration;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Window;
import android.view.WindowManager;

import com.owo.app.base.ConfigurableActivity;
import com.owo.app.common.BaseHandler;
import com.owo.app.common.ContextManager;
import com.owo.app.main.ui.MainFrame;
import com.owo.base.pattern.Singleton;
import com.owo.media.ThumbnailCache;

public class MainActivity extends ConfigurableActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);

		// initialize
		ContextManager.init(this);
		BaseHandler.initialize();
		Singleton.of(ThumbnailCache.class).load();

		MainFrame mainFrame = new MainFrame(this);
		setContentView(mainFrame);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		BaseHandler.destroy();
		ContextManager.destroy();
		Singleton.destroy();
	}

	@Override
	protected void onPause() {
		super.onPause();
		Singleton.of(ThumbnailCache.class).save();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
	}

	@Override
	public boolean dispatchKeyEvent(KeyEvent event) {
		if (event.getKeyCode() == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP) {
		}
		return super.dispatchKeyEvent(event);

	}
}
