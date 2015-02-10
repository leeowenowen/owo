package com.owo.mediaplayer.view;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.drawable.shapes.RectShape;

public class FullScreenShape extends RectShape {
	private Paint mPaint = new Paint();
	private Path mPath = new Path();

	@Override
	public void draw(Canvas canvas, Paint paint) {
		mPaint.setColor(Color.RED);

		RectF rectF = rect();
		float left = rectF.left + rectF.width() / 4;
		float top = rectF.top + rectF.height() / 4;

		// canvas.drawRect(left, top, left + rectF.width() / 2,
		// top + rectF.width() / 2, mPaint);

		drawFullScreen(canvas, left, top, rectF.width() / 2, rectF.height() / 2);
	}

	private RectF mArgRectF = new RectF();

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
	private void drawFullScreen(Canvas canvas, float left, float top,
			float width, float height) {
		float xA = left;
		float yA = top;
		float xC = left + width;
		float yC = top;
		float xB = left;
		float yB = top + height;
		float xD = xC;
		float yD = yB;
		float delta = 22.0f;
		mPaint.setStrokeWidth(5);
		mPaint.setStyle(Paint.Style.STROKE);
		canvas.drawLine(xA, yA, xA + delta, yA, mPaint);
		canvas.drawLine(xA, yA, xA, yA + delta, mPaint);

		canvas.drawLine(xC, yC, xC - delta, yC, mPaint);
		canvas.drawLine(xC, yC, xC, yC + delta, mPaint);

		canvas.drawLine(xB, yB, xB, yB - delta, mPaint);
		canvas.drawLine(xB, yB, xB + delta, yB, mPaint);

		canvas.drawLine(xD, yD, xD - delta, yD, mPaint);
		canvas.drawLine(xD, yD, xD, yD - delta, mPaint);

		canvas.drawLine(xA, yA, xD, yD, mPaint);
		canvas.drawLine(xB, yB, xC, yC, mPaint);
	}
}
