package com.owo.ui.shape;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;

public class NextShape extends HalfRectShape {
	private Path mPath = new Path();

	/**
	 * <pre>
	 * 
	 *               b
	 * a              
	 *               c
	 * </pre>
	 */
	@Override
	protected void drawImpl(Canvas canvas, Paint paint, float left, float top, float width,
			float height) {
		float xA = left;
		float yA = top + height / 2;
		float xB = xA + width;
		float yB = top;
		float xC = xB;
		float yC = top + height;
		mPath.reset();
		mPath.moveTo(xB, yB);
		mPath.lineTo(xC, yC);
		mPath.lineTo(xA, yA);
		mPath.lineTo(xB, yB);
		mPath.moveTo(xA, yB);
		mPath.lineTo(xA, yC);

		canvas.translate(width, height);
		canvas.rotate(180);
		canvas.drawPath(mPath, paint());
	}
}
