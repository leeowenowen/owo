package com.owo.mediastore.interfaces;

import android.database.Cursor;
import android.view.View;

public interface ICursorTransformer {
	View transform(View convertView, Cursor cursor);
}
