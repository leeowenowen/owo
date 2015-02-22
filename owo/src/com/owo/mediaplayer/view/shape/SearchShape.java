package com.owo.mediaplayer.view.shape;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;

import com.owo.base.util.MathHelper;

public class SearchShape extends CustomRectShape {
	@Override
	public void draw(Canvas canvas, Paint paint) {
		RectF rectF = rect();
		float left = rectF.left + rectF.width() / 4;
		float top = rectF.top + rectF.height() / 4;

		drawSearch(canvas, left, top, rectF.width() / 2, rectF.height() / 2);
	}

	private void drawSearch(Canvas canvas, float left, float top, float width,
			float height) {
		float xO = left + width / 3;
		float yO = top + height / 3;
		float rO = MathHelper.diag(width, height) / 3;
		float xB = left + width * 2 / 3;
		float yB = top + height * 2 / 3;
		float xC = left + width;
		float yC = top + height;
		canvas.drawCircle(xO, yO, rO, paint());
		canvas.drawLine(xB, yB, xC, yC, paint());
	}
}
