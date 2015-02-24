package com.owo.mediaplayer.view.shape;

import com.owo.app.theme.Theme;

import android.graphics.Paint;
import android.graphics.drawable.shapes.RectShape;

public class CustomRectShape extends RectShape {
	protected Paint paint() {
		return Theme.paint();
	}

	protected Paint fillPaint() {
		return Theme.fillPaint();
	}
}
