package com.owo.mediaplayer.view.shape;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;

public class MenuShape extends CustomRectShape {
	@Override
	public void draw(Canvas canvas, Paint paint) {
		RectF rectF = rect();
		float left = rectF.left + rectF.width() / 4;
		float top = rectF.top + rectF.height() / 4;

		drawMenu(canvas, left, top, rectF.width() / 2, rectF.height() / 2);
	}

	private void drawMenu(Canvas canvas, float left, float top, float width,
			float height) {
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
