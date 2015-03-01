package com.owo.ui.shape;

import android.graphics.Canvas;
import android.graphics.Paint;

public class StopShape extends HalfRectShape {
	@Override
	protected void drawImpl(Canvas canvas, Paint paint, float left, float top, float width,
			float height) {
		canvas.drawRect(left, top, left + width, top + height, paint);
	}
}
