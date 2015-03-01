package com.owo.ui.shape;

import android.graphics.Canvas;
import android.graphics.Paint;

public class ExitFullScreenShape extends HalfRectShape {
	/**
	 * <pre>
	 * 
	 *          x    x
	 *        y A    B y
	 *        
	 *        y C    D y
	 *          x    x
	 * </pre>
	 */
	@Override
	protected void drawImpl(Canvas canvas, Paint paint, float left, float top, float width,
			float height) {
		float xCenter = left + width / 2;
		float yCenter = top + height / 2;
		float delta = 22.0f;
		float xA = xCenter - delta;
		float yA = yCenter - delta;
		float xC = xA;
		float yC = yCenter + delta;
		float xB = xCenter + delta;
		float yB = yA;
		float xD = xB;
		float yD = yC;

		canvas.drawLine(xA, yA, xA - delta, yA, paint);
		canvas.drawLine(xA, yA, xA, yA - delta, paint);

		canvas.drawLine(xC, yC, xC - delta, yC, paint);
		canvas.drawLine(xC, yC, xC, yC + delta, paint);

		canvas.drawLine(xB, yB, xB, yB - delta, paint);
		canvas.drawLine(xB, yB, xB + delta, yB, paint);

		canvas.drawLine(xD, yD, xD + delta, yD, paint);
		canvas.drawLine(xD, yD, xD, yD + delta, paint);

		canvas.drawLine(xA, yA, left, top, paint);
		canvas.drawLine(xB, yB, left + width, top, paint);
		canvas.drawLine(xC, yC, left, top + height, paint);
		canvas.drawLine(xD, yD, left + width, top + height, paint);
	}
}
