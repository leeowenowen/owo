package com.owo.media.audio;

import android.content.Context;
import android.database.Cursor;
import android.view.View;

import com.owo.app.ContextManager;
import com.owo.media.interfaces.ICursorTransformer;

abstract class AbsAudioItemTransformer implements ICursorTransformer {
	@Override
	public View transform(View convertView, Cursor cursor) {
		AbsAudioItemView view = null;
		if (convertView != null && convertView instanceof AbsAudioItemView) {
			view = (AbsAudioItemView) convertView;
		} else {
			view = createItemView(ContextManager.context());
		}
		view.update(cursor);
		return view;
	}

	protected abstract AbsAudioItemView createItemView(Context context);
}
