package com.owo.app.main;

import android.app.Activity;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Window;
import android.view.WindowManager;

import com.owo.app.common.BaseHandler;
import com.owo.app.common.ContextManager;
import com.owo.app.main.widget.MainFrame;
import com.owo.app.test.Data;
import com.owo.app.test.Data.Observer;
import com.owo.base.pattern.Instance;

public class MainActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

		// initialize
		ContextManager.init(this);
		BaseHandler.initialize();

		MainFrame mainFrame = new MainFrame(this);
		setContentView(mainFrame);
		Instance.of(Data.class).observer(new Observer() {
			
			@Override
			public void onDataChanged(String msg) {
				Log.v("xxx", msg);
			}
		});
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		BaseHandler.destroy();
		ContextManager.destroy();

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
