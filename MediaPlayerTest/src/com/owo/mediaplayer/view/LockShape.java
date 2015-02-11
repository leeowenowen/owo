package com.owo.mediaplayer.view;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;

public class LockShape extends CustomRectShape {
	private Path mPath = new Path();

	@Override
	public void draw(Canvas canvas, Paint paint) {
		RectF rectF = rect();
		float left = rectF.left + rectF.width() / 4;
		float top = rectF.top + rectF.height() / 4;
		drawLock(canvas, left, top, rectF.width() / 2, rectF.height() / 2);
	}

	/**
	 * <pre>
	 *      O
	 *    I    J
	 * 
	 * A  B    C  D
	 * 
	 * 
	 * 
	 * 
	 * 
	 * E          F
	 * </pre>
	 */
	private RectF mArgRectF = new RectF();

	private void drawLock(Canvas canvas, float left, float top, float width,
			float height) {
		float xA = left;
		float yA = top + height * 1 / 3;
		float xB = left + width / 4;
		float yB = yA;
		float xC = xB + width / 2;
		float yC = yA;
		float xD = left + width;
		float yD = yA;
		float xE = left;
		float yE = top + height;
		float xF = xD;
		float yF = yE;
		// float xO = left + width / 2;
		// float yO = top;
		float radius = (xC - xB) / 2;
		float xI = xB;
		float yI = top + radius;
		float xJ = xC;
		// float yJ = yI;
		mPath.reset();
		mPath.moveTo(xA, yA);
		mPath.lineTo(xB, yB);
		mPath.lineTo(xI, yI);
		mArgRectF.set(xI, top, xJ, top + xJ - xI);
		mPath.arcTo(mArgRectF, -180, 180);
		mPath.lineTo(xC, yC);
		mPath.lineTo(xD, yD);
		mPath.lineTo(xF, yF);
		mPath.lineTo(xE, yE);
		mPath.close();
		canvas.drawPath(mPath, paint());
	}
}
