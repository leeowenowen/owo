package com.owo.media.image;

import com.owo.media.interfaces.ICursorTransformer;
import com.owo.media.interfaces.ScaleLevel;

public class ImageCursorTransformerFactory {
	public static ICursorTransformer create(int level) {
		switch (level) {
		case ScaleLevel.sLevel0: // all width
			return new ImageItemTransformerLevel0();
		case ScaleLevel.sLevel1:// half width
			return new ImageItemTransformerLevel1();
		case ScaleLevel.sLevel2:// half width
			return new ImageItemTransformerLevel0();
		case ScaleLevel.sLevel3:// half width
			return new ImageItemTransformerLevel0();
		default:
			return null;
		}
	}
}
