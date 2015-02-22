package com.owo.mediaplayer;

import com.owo.base.util.MediaUtil;
import com.owo.mediaplayer.interfaces.ITimeFormatter;

public class DefaultTimeFormatter implements ITimeFormatter {
	@Override
	public String format(int timeMs) {
		return MediaUtil.format(timeMs);
	}

}
