package com.owo.app.test;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Display;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

public class Activity1 extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		String title = getIntent().getStringExtra("title");
		int x = getIntent().getIntExtra("x", -1);
		int y = getIntent().getIntExtra("y", -1);
		int w = getIntent().getIntExtra("w", -1);
		int h = getIntent().getIntExtra("h", -1);
		TextView tView = new TextView(this);
		tView.setBackgroundColor(Color.GREEN);
		tView.setText(title);
		WindowManager.LayoutParams lp = getWindow().getAttributes();
		lp.x = x;
		lp.y = y;
		lp.width = w;
		lp.height = h;
		lp.gravity = Gravity.TOP | Gravity.LEFT;
		getWindow().setAttributes(lp);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN,
				WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN);
		Display display = null;

		setContentView(tView);

	}
}