package com.owo.ui.shape;

import android.graphics.Paint;
import android.graphics.drawable.shapes.RectShape;

import com.owo.app.theme.Theme;
import com.owo.base.pattern.Instance;

public class ThemeRectShape extends RectShape {
	protected Paint paint() {
		return Instance.of(Theme.class).paint();
	}

	protected Paint fillPaint() {
		return Instance.of(Theme.class).fillPaint();
	}
}