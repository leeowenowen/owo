package com.owo.mediaplayer.view;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.drawable.shapes.RectShape;

public class StartShape extends RectShape {
	private Paint mPaint = new Paint();
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
		mPaint.setColor(Color.RED);
		mPaint.setStrokeWidth(5);
		mPaint.setStyle(Paint.Style.STROKE);
		RectF rectF = rect();
		float halfHeight = rectF.height() / 2;
		float harfWidth = rectF.width() / 2;
		float quarterHeight = rectF.height() / 4;
		float quarterWidth = rectF.width() / 4;
		float xA = rectF.left + quarterWidth;
		float yA = rectF.top + halfHeight;
		float xB = xA + harfWidth;
		float yB = yA - quarterHeight;
		float xC = xB;
		float yC = yA + quarterHeight;
		mPath.reset();
		mPath.moveTo(xA, yA);
		mPath.lineTo(xB, yB);
		mPath.lineTo(xC, yC);
		mPath.close();
		canvas.drawPath(mPath, mPaint);
	}
}
