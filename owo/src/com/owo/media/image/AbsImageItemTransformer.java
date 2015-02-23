package com.owo.media.image;

import android.content.Context;
import android.database.Cursor;
import android.view.View;

import com.owo.app.ContextManager;
import com.owo.media.interfaces.ICursorTransformer;

abstract class AbsImageItemTransformer implements ICursorTransformer {
	@Override
	public View transform(View convertView, Cursor cursor) {
		AbsImageItemView view = null;
		if (convertView != null && convertView instanceof AbsImageItemView) {
			view = (AbsImageItemView) convertView;
		} else {
			view = createItemView(ContextManager.context());
		}
		view.update(cursor);
		return view;
	}

	protected abstract AbsImageItemView createItemView(Context context);
}
