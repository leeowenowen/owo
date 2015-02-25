package com.owo.ui.shape;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class CircleShape extends HalfRectShape {
	private int mColor;

	public CircleShape() {
		mColor = Color.BLACK;
	}

	public CircleShape(int color) {
		mColor = color;
	}

	public void color(int color) {
		mColor = color;
	}

	public int color() {
		return mColor;
	}

	@Override
	protected void drawImpl(Canvas canvas, Paint paint, float left, float top, float width, float height) {
		float x = left + width / 2;
		float y = top + height / 2;
		float r = height / 2;
		Paint fillPaint = fillPaint();
		int color = fillPaint.getColor();
		fillPaint.setColor(mColor);
		canvas.drawCircle(x, y, r, fillPaint);
		fillPaint.setColor(color);
	}
}
