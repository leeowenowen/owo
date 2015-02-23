package com.owo.media.base;

import com.owo.base.mvc.interfaces.IMessageHandler;

import android.content.Context;
import android.widget.ListView;

public class MediaListView extends ListView {
	protected IMessageHandler mMessageHandler;

	public MediaListView(Context context, IMessageHandler messageHandler) {
		super(context);
		mMessageHandler = messageHandler;
	}

}
