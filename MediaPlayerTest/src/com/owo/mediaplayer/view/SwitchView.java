package com.owo.mediaplayer.view;

import com.owo.mediaplayer.view.utils.LP;

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