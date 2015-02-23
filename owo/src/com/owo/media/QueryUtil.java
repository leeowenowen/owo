package com.owo.media;

import android.annotation.SuppressLint;
import android.database.Cursor;

public class QueryUtil {
	@SuppressLint("NewApi")
	public static String getColumn(Cursor cursor, String column) {
		int columnIndex = cursor.getColumnIndex(column);
		switch (cursor.getType(columnIndex)) {
		case Cursor.FIELD_TYPE_STRING:
			return cursor.getString(columnIndex);
		case Cursor.FIELD_TYPE_FLOAT:
			return Float.toString(cursor.getFloat(columnIndex));
		case Cursor.FIELD_TYPE_INTEGER:
			return Integer.toString(cursor.getInt(columnIndex));
		default:
			return null;
		}
	}
}
