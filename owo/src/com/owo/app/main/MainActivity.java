package com.owo.app.main;

import android.content.res.Configuration;
import android.os.Bundle;
import android.view.KeyEvent;

import com.owo.app.base.ConfigurableActivity;
import com.owo.app.main.ui.MainFrame;
import com.owo.base.pattern.Singleton;
import com.owo.media.ThumbnailCache;

public class MainActivity extends ConfigurableActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		MainFrame mainFrame = new MainFrame(this);
		setContentView(mainFrame);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
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
		if (event.getKeyCode() == KeyEvent.KEYCODE_BACK
				&& event.getAction() == KeyEvent.ACTION_UP) {
		}
		return super.dispatchKeyEvent(event);

	}
}
