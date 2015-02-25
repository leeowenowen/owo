package com.owo.app.main;

import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Window;
import android.view.WindowManager;

import com.owo.app.base.ConfigurableActivity;
import com.owo.app.common.BaseHandler;
import com.owo.app.common.ContextManager;
import com.owo.app.language.Language;
import com.owo.app.language.zh_CN_Provider;
import com.owo.app.main.widget.MainFrame;
import com.owo.base.pattern.Instance;

public class MainActivity extends ConfigurableActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

		// initialize
		ContextManager.init(this);
		BaseHandler.initialize();
		Instance.of(Language.class).setProvider(new zh_CN_Provider());

		MainFrame mainFrame = new MainFrame(this);
		setContentView(mainFrame);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		BaseHandler.destroy();
		ContextManager.destroy();
		Instance.destroy();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
	}

	@Override
	public boolean dispatchKeyEvent(KeyEvent event) {
		if (event.getKeyCode() == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP) {
			Log.v("xxx", "back");
		}
		return super.dispatchKeyEvent(event);

	}
}
