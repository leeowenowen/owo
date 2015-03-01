package com.owo.ui.shape;

import android.graphics.Canvas;
import android.graphics.Paint;

import com.owo.base.util.MathHelper;

public class SearchShape extends HalfRectShape {
	@Override
	protected void drawImpl(Canvas canvas, Paint paint, float left, float top, float width,
			float height) {
		float xO = left + width / 3;
		float yO = top + height / 3;
		float rO = MathHelper.diag(width, height) / 3;
		float xB = left + width * 2 / 3;
		float yB = top + height * 2 / 3;
		float xC = left + width;
		float yC = top + height;
		canvas.drawCircle(xO, yO, rO, paint());
		canvas.drawLine(xB, yB, xC, yC, paint());
	}
}
