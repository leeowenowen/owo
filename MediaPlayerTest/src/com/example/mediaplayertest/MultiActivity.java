package com.example.mediaplayertest;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MultiActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Button btn = new Button(this);
		btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MultiActivity.this,
						com.example.mediaplayertest.Activity1.class);
				intent.putExtra("title", "title");
				intent.putExtra("x", 0);
				intent.putExtra("y", 0);
				intent.putExtra("w", 200);
				intent.putExtra("h", 200);

				startActivity(intent);

				Intent intent2 = new Intent(MultiActivity.this,
						com.example.mediaplayertest.Activity2.class);
				intent2.putExtra("title", "title");
				intent2.putExtra("x", 200);
				intent2.putExtra("y", 200);
				intent2.putExtra("w", 200);
				intent2.putExtra("h", 200);

				startActivity(intent2);
			}
		});
		setContentView(btn);
	}
}
