package com.owo.mediaplayer.ui;

import com.owo.ui.utils.LP;

import android.content.Context;
import android.view.View;
import android.widget.FrameLayout;

public class SwitchView extends FrameLayout {

	public SwitchView(Context context) {
		super(context);
	}

	public SwitchView of(View one, View theOther) {
		addView(one, LP.FMM);
		addView(theOther, LP.FMM);
		return this;
	}
}