package com.owo.ui.shape;

import android.graphics.Canvas;
import android.graphics.Paint;

public class EnterFullScreenShape extends HalfRectShape {

	/**
	 * <pre>
	 * A x             x C
	 * y                 y
	 * 
	 * 
	 * 
	 * y                 y
	 * B x             x DD
	 */
	@Override
	protected void drawImpl(Canvas canvas, Paint paint, float left, float top, float width, float height) {
		float xA = left;
		float yA = top;
		float xC = left + width;
		float yC = top;
		float xB = left;
		float yB = top + height;
		float xD = xC;
		float yD = yB;
		float delta = 22.0f;
		canvas.drawLine(xA, yA, xA + delta, yA, paint);
		canvas.drawLine(xA, yA, xA, yA + delta, paint);

		canvas.drawLine(xC, yC, xC - delta, yC, paint);
		canvas.drawLine(xC, yC, xC, yC + delta, paint);

		canvas.drawLine(xB, yB, xB, yB - delta, paint);
		canvas.drawLine(xB, yB, xB + delta, yB, paint);

		canvas.drawLine(xD, yD, xD - delta, yD, paint);
		canvas.drawLine(xD, yD, xD, yD - delta, paint);

		canvas.drawLine(xA, yA, xD, yD, paint);
		canvas.drawLine(xB, yB, xC, yC, paint);
	}
}
