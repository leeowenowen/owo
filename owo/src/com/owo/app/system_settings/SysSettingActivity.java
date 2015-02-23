package com.owo.app.system_settings;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.owo.app.system_settings.widget.SysSettingWidget;

public class SysSettingActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

		setContentView(new SysSettingWidget(this));
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	};
}
