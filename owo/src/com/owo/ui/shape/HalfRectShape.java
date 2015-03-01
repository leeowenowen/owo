package com.owo.ui.shape;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;

public abstract class HalfRectShape extends ThemeRectShape {
	private Integer mBgColor;

	public void bgColor(int bgColor) {
		mBgColor = bgColor;
	}

	@Override
	public void draw(Canvas canvas, Paint paint) {
		RectF rectF = rect();
		float left = rectF.left + rectF.width() / 4;
		float top = rectF.top + rectF.height() / 4;
		if (mBgColor != null) {
			canvas.drawColor(mBgColor);
		}
		canvas.translate(left, top);
		drawImpl(canvas, paint(), 0, 0, rectF.width() / 2, rectF.height() / 2);
	}

	protected abstract void drawImpl(Canvas canvas, Paint paint, float left, float top,
			float width, float height);
}
