package com.owo.ui.shape;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;

public abstract class HalfRectShape extends ThemeRectShape {
	@Override
	public void draw(Canvas canvas, Paint paint) {
		RectF rectF = rect();
		float left = rectF.left + rectF.width() / 4;
		float top = rectF.top + rectF.height() / 4;

		drawImpl(canvas, paint(), left, top, rectF.width() / 2, rectF.height() / 2);
	}

	protected abstract void drawImpl(Canvas canvas, Paint paint, float left, float top, float width, float height);
}
