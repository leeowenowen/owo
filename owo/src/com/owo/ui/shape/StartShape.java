package com.owo.ui.shape;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;

public class StartShape extends ThemeRectShape {
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
	public void draw(Canvas canvas, Paint paint) {
		RectF rectF = rect();
		float halfHeight = rectF.height() / 2;
		float halfWidth = rectF.width() / 2;
		float quarterHeight = rectF.height() / 4;
		float quarterWidth = rectF.width() / 4;
		float xA = rectF.left + quarterWidth;
		float yA = rectF.top + halfHeight;
		float xB = xA + halfWidth;
		float yB = yA - quarterHeight;
		float xC = xB;
		float yC = yA + quarterHeight;
		mPath.reset();
		mPath.moveTo(xA, yA);
		mPath.lineTo(xB, yB);
		mPath.lineTo(xC, yC);
		mPath.close();

		canvas.translate(rectF.width(), rectF.height());
		canvas.rotate(180);
		canvas.drawPath(mPath, paint());
	}
}
