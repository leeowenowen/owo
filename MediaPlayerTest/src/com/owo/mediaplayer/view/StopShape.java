package com.owo.mediaplayer.view;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.shapes.RectShape;

public class StopShape extends RectShape {
	private Paint mPaint = new Paint();

	@Override
	public void draw(Canvas canvas, Paint paint) {
		mPaint.setColor(Color.RED);
		mPaint.setStrokeWidth(5);
		mPaint.setStyle(Paint.Style.STROKE);
		RectF rectF = rect();
		float left = rectF.left + rectF.width() / 4;
		float top = rectF.top + rectF.height() / 4;

		canvas.drawRect(left, top, left + rectF.width() / 2,
				top + rectF.width() / 2, mPaint);

	}
}
