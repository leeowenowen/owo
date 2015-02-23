package com.owo.media.interfaces;

import android.database.Cursor;
import android.database.DataSetObserver;

public interface IMediaQueryer {
	Cursor query(DataSetObserver observer);
}
