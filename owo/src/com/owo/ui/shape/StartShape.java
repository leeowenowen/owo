package com.owo.ui.shape;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;

public class StartShape extends HalfRectShape {
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
		float xA = 0;
		float yA = top + height / 2;
		float xB = xA + width;
		float yB = top;
		float xC = xB;
		float yC = top + height;
		mPath.reset();
		mPath.moveTo(xA, yA);
		mPath.lineTo(xB, yB);
		mPath.lineTo(xC, yC);
		mPath.close();

		canvas.translate(width, height);
		canvas.rotate(180);
		canvas.drawPath(mPath, paint);
	}
}
