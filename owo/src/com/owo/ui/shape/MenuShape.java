package com.owo.ui.shape;

import android.graphics.Canvas;
import android.graphics.Paint;

public class MenuShape extends HalfRectShape {
	@Override
	protected void drawImpl(Canvas canvas, Paint paint, float left, float top, float width, float height) {
		float x = left + width / 2;
		float y2 = top + height / 2;
		float y1 = y2 - 30;
		float y3 = y2 + 30;
		float r = 10;
		canvas.drawCircle(x, y1, r, fillPaint());
		canvas.drawCircle(x, y2, r, fillPaint());
		canvas.drawCircle(x, y3, r, fillPaint());
	}
}
