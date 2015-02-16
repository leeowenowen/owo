package com.owo.mediastore;

import com.owo.mediastore.interfaces.IAdapterViewFactory;

import android.widget.Adapter;
import android.widget.AdapterView;

public class AdapterViewFactory implements IAdapterViewFactory {

	@Override
	public <T extends Adapter> AdapterView<T> create() {
		return null;
	}

}
