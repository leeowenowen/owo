package com.owo.mediaplayer.view;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;

public class StopShape extends CustomRectShape {
	@Override
	public void draw(Canvas canvas, Paint paint) {
		RectF rectF = rect();
		float left = rectF.left + rectF.width() / 4;
		float top = rectF.top + rectF.height() / 4;

		canvas.drawRect(left, top, left + rectF.width() / 2,
				top + rectF.width() / 2, paint());

	}
}
