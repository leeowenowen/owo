package com.owo.media.video;

import com.owo.media.interfaces.ICursorTransformer;
import com.owo.media.interfaces.ScaleLevel;

public class VideoCursorTransformerFactory {
	public static ICursorTransformer create(int level) {
		switch (level) {
		case ScaleLevel.sLevel0: // all width
			return new VideoItemTransformerLevel0();
		case ScaleLevel.sLevel1:// half width
			return new VideoItemTransformerLevel1();
		case ScaleLevel.sLevel2:// half width
			return new VideoItemTransformerLevel2();
		case ScaleLevel.sLevel3:// half width
			return new VideoItemTransformerLevel3();
		default:
			return null;
		}
	}
}
