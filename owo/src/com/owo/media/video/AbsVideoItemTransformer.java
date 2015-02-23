package com.owo.media.video;

import android.content.Context;
import android.database.Cursor;
import android.view.View;

import com.owo.app.common.ContextManager;
import com.owo.media.interfaces.ICursorTransformer;

abstract class AbsVideoItemTransformer implements ICursorTransformer {
	@Override
	public View transform(View convertView, Cursor cursor) {
		AbsVideoItemView view = null;
		if (convertView != null && convertView instanceof AbsVideoItemView) {
			view = (AbsVideoItemView) convertView;
		} else {
			view = createItemView(ContextManager.context());
		}
		view.update(cursor);
		return view;
	}

	protected abstract AbsVideoItemView createItemView(Context context);
}
