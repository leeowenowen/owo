package com.owo.media;

import android.annotation.SuppressLint;
import android.database.Cursor;

public class QueryUtil {
	@SuppressLint("NewApi")
	public static String getColumn(Cursor cursor, String column) {
		String ret = null;
		int columnIndex = cursor.getColumnIndex(column);
		switch (cursor.getType(columnIndex)) {
		case Cursor.FIELD_TYPE_STRING:
			ret = cursor.getString(columnIndex);
			break;
		case Cursor.FIELD_TYPE_FLOAT:
			ret = Float.toString(cursor.getFloat(columnIndex));
			break;
		case Cursor.FIELD_TYPE_INTEGER:
			ret = Integer.toString(cursor.getInt(columnIndex));
			break;
		default:
			break;
		}
		return ret == null ? "" : ret;
	}
}
