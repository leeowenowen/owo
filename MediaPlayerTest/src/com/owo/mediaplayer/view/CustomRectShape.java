package com.owo.mediaplayer.view;

import android.graphics.Paint;
import android.graphics.drawable.shapes.RectShape;

public class CustomRectShape extends RectShape {
	protected Paint paint() {
		return Theme.paint();
	}
}
