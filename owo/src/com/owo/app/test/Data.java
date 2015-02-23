package com.owo.app.test;

public class Data {
	public String msg;

	public void msg(String msg) {
		this.msg = msg;
		mObserver.onDataChanged(msg);
	}

	Observer mObserver;

	public void observer(Observer observer) {
		mObserver = observer;
	}

	public static interface Observer {
		void onDataChanged(String msg);
	}
}
