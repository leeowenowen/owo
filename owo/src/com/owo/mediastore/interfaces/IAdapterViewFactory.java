package com.owo.mediastore.interfaces;

import android.widget.Adapter;
import android.widget.AdapterView;

public interface IAdapterViewFactory {
	<T extends Adapter> AdapterView<T> create();
}
