package com.owo.app;

import android.app.Activity;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Window;
import android.view.WindowManager;

import com.owo.app.mvc.MainController;
import com.owo.app.widget.MainFrame;
import com.owo.base.util.BaseHandler;

public class DefaultMediaPlayActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

		// initialize
		ContextManager.init(this);
		BaseHandler.initialize();

		MainController controller = new MainController();
		MainFrame mainFrame = new MainFrame(this, controller);
		setContentView(mainFrame);
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
		// TODO Auto-generated method stub
		return super.dispatchKeyEvent(event);
	}
}
