package com.owo.ui.shape;

import android.graphics.Paint;
import android.graphics.drawable.shapes.RectShape;

import com.owo.app.theme.Theme;
import com.owo.base.pattern.Singleton;

public class ThemeRectShape extends RectShape {
	protected Paint paint() {
		return Singleton.of(Theme.class).paint();
	}

	protected Paint fillPaint() {
		return Singleton.of(Theme.class).fillPaint();
	}
}
