package com.owo.ui.utils;

import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

public class ViewUtil {
	public static void removeFromParent(View v) {
		ViewParent vParent = v.getParent();
		if (vParent != null && vParent instanceof ViewGroup) {
			((ViewGroup) vParent).removeView(v);
		}
	}
}
