package com.owo.media.audio;

import com.owo.media.interfaces.ICursorTransformer;
import com.owo.media.interfaces.ScaleLevel;

public class AudioCursorTransformerFactory {
	public static ICursorTransformer create(int level) {
		switch (level) {
		case ScaleLevel.sLevel0: // all width
			return new AudioItemTransformerLevel0();
		case ScaleLevel.sLevel1:// half width
			return new AudioItemTransformerLevel0();
		case ScaleLevel.sLevel2:// half width
			return new AudioItemTransformerLevel0();
		case ScaleLevel.sLevel3:// half width
			return new AudioItemTransformerLevel0();
		default:
			return null;
		}
	}
}
