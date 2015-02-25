package com.owo.ui.shape;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;

public class UnLockShape extends HalfRectShape {
	private Path mPath = new Path();
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

	@Override
	protected void drawImpl(Canvas canvas, Paint paint, float left, float top, float width, float height) {
		float xA = left;
		float yA = top + height / 2;
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
		// sfloat yJ = yI;
		mPath.reset();
		mPath.moveTo(xA, yA);
		mPath.lineTo(xB, yB);
		mPath.lineTo(xI, yI);
		mArgRectF.set(xI, top, xJ, top + xJ - xI);
		mPath.arcTo(mArgRectF, -180, 180);
		mPath.moveTo(xC, yC);
		mPath.lineTo(xD, yD);
		mPath.lineTo(xF, yF);
		mPath.lineTo(xE, yE);
		mPath.lineTo(xA, yA);
		canvas.drawPath(mPath, paint());
	}
}
