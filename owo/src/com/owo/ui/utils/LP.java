package com.owo.ui.utils;

import android.view.Gravity;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

public class LP {
	public static final FrameLayout.LayoutParams FMM = new FrameLayout.LayoutParams(
			FrameLayout.LayoutParams.MATCH_PARENT,
			FrameLayout.LayoutParams.MATCH_PARENT);

	public static final FrameLayout.LayoutParams FWWC = new FrameLayout.LayoutParams(
			FrameLayout.LayoutParams.WRAP_CONTENT,
			FrameLayout.LayoutParams.WRAP_CONTENT, Gravity.CENTER);
	public static final LinearLayout.LayoutParams LMW = new LinearLayout.LayoutParams(
			LinearLayout.LayoutParams.MATCH_PARENT,
			LinearLayout.LayoutParams.WRAP_CONTENT);

	public static final LinearLayout.LayoutParams LWM = new LinearLayout.LayoutParams(
			LinearLayout.LayoutParams.WRAP_CONTENT,
			LinearLayout.LayoutParams.MATCH_PARENT);

	public static final LinearLayout.LayoutParams L0W1 = new LinearLayout.LayoutParams(
			0, LinearLayout.LayoutParams.WRAP_CONTENT, 1);
	public static final LinearLayout.LayoutParams LW01 = new LinearLayout.LayoutParams(
			LinearLayout.LayoutParams.MATCH_PARENT, 0, 1);
}
