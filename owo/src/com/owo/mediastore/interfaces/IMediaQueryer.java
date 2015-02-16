package com.owo.mediastore.interfaces;

import android.database.Cursor;
import android.database.DataSetObserver;

public interface IMediaQueryer {
	Cursor query(DataSetObserver observer);
}
