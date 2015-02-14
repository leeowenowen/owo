package com.owo.mediaplayer;

import java.util.Formatter;
import java.util.Locale;

import com.owo.mediaplayer.interfaces.ITimeFormatter;

public class DefaultTimeFormatter implements ITimeFormatter {
	StringBuilder mFormatBuilder;
	Formatter mFormatter;

	public DefaultTimeFormatter() {
		mFormatBuilder = new StringBuilder();
		mFormatter = new Formatter(mFormatBuilder, Locale.getDefault());
	}

	@Override
	public String format(int timeMs) {
		int totalSeconds = timeMs / 1000;

		int seconds = totalSeconds % 60;
		int minutes = (totalSeconds / 60) % 60;
		int hours = totalSeconds / 3600;

		mFormatBuilder.setLength(0);
		if (hours > 0) {
			return mFormatter.format("%d:%02d:%02d", hours, minutes, seconds)
					.toString();
		} else {
			return mFormatter.format("%02d:%02d", minutes, seconds).toString();
		}
	}

}
