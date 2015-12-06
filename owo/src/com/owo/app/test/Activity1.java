package com.owo.app.test;

import java.util.Random;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.owo.app.base.ConfigurableActivity;
import com.owo.app.common.ContextManager;
import com.owo.base.util.DimensionUtil;

public class Activity1 extends ConfigurableActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// String title = getIntent().getStringExtra("title");
		// int x = getIntent().getIntExtra("x", -1);
		// int y = getIntent().getIntExtra("y", -1);
		// int w = getIntent().getIntExtra("w", -1);
		// int h = getIntent().getIntExtra("h", -1);
		// TextView tView = new TextView(this);
		// tView.setBackgroundColor(Color.GREEN);
		// tView.setText(title);
		// WindowManager.LayoutParams lp = getWindow().getAttributes();
		// lp.x = x;
		// lp.y = y;
		// lp.width = w;
		// lp.height = h;
		// lp.gravity = Gravity.TOP | Gravity.LEFT;
		// getWindow().setAttributes(lp);
		// requestWindowFeature(Window.FEATURE_NO_TITLE);
		// getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN,
		// WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN);
		// Display display = null;
		GridView gridView = new GridView(this);
		gridView.setNumColumns(3);
//		gridView.setHorizontalSpacing(0);
//		gridView.setVerticalSpacing(0);
		gridView.setAdapter(new GridAdapter());
		setContentView(gridView);

	}

	private class GridAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return 500;
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ImageView view = null;
			if (convertView != null && convertView instanceof ImageView) {
				view = (ImageView) convertView;
			} else {
				view = new ImageView(ContextManager.context());
			}
			AbsListView.LayoutParams lp = new AbsListView.LayoutParams(
					DimensionUtil.displayMetrics().widthPixels / 3,
					DimensionUtil.displayMetrics().widthPixels / 3);
			view.setLayoutParams(lp);
			view.setBackgroundColor(new Random().nextInt());
			return view;
		}

	}
}