package com.owo.base.mvc.interfaces;

import java.util.HashSet;
import java.util.Set;

import android.database.DataSetObserver;

public class ViewModel {
	Set<DataSetObserver> mObservers = new HashSet<DataSetObserver>();

	public void add(DataSetObserver observer) {
		mObservers.add(observer);
	}

	public void remove(DataSetObserver observer) {
		mObservers.remove(observer);
	}
}