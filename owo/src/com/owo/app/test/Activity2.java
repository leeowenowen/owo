package com.owo.app.test;

import android.R;
import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

public class Activity2 extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		String title = getIntent().getStringExtra("title2");
		TextView tView = new TextView(this);
		tView.setBackgroundColor(Color.RED);
		tView.setText(title);
		int x = getIntent().getIntExtra("x", -1);
		int y = getIntent().getIntExtra("y", -1);
		int w = getIntent().getIntExtra("w", -1);
		int h = getIntent().getIntExtra("h", -1);
		WindowManager.LayoutParams lp = getWindow().getAttributes();
		lp.x = x;
		lp.y = y;
		lp.width = w;
		lp.height = h;
		lp.gravity = Gravity.BOTTOM | Gravity.RIGHT;
		getWindow().setAttributes(lp);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		// getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN,
		// WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND,
				WindowManager.LayoutParams.FLAG_BLUR_BEHIND);
		getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
		getWindow().setLayout(w, h);
		//setContentView(R.layout.translucent_background);

	}
}