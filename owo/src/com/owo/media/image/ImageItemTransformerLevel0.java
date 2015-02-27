package com.owo.media.image;

import android.content.Context;
import android.view.Gravity;
import android.widget.LinearLayout;

import com.owo.app.theme.Theme;
import com.owo.base.util.DimensionUtil;
import com.owo.ui.utils.LP;

class ImageItemTransformerLevel0 extends AbsImageItemTransformer {
	@Override
	protected AbsImageItemView createItemView(Context context) {
		return new ImageItemViewLevel0(context);
	}

	private class ImageItemViewLevel0 extends AbsImageItemView {
		private static final int sTitleSize = 15;
		private static final int sContentSize = 12;

		public ImageItemViewLevel0(Context context) {
			super(context);
			onThemeChanged();
		}

		@Override
		protected void setupLayout(Context context) {
			mThumbnaiWidth = DimensionUtil.w(300);
			mThumbnailHeight = DimensionUtil.h(200);
			mTitle.setTextSize(sTitleSize);
			mSize.setTextSize(sContentSize);
			mWH.setTextSize(sContentSize);
			mPath.setTextSize(sContentSize);

			LinearLayout attributes = new LinearLayout(context);
			attributes.addView(mSize, LP.L0W1);
			attributes.addView(mWH, LP.L0W1);

			LinearLayout right = new LinearLayout(context);
			right.setOrientation(LinearLayout.VERTICAL);
			right.addView(mTitle);
			right.addView(mPath);
			right.addView(attributes);
			right.setPadding(10, 0, 0, 0);

			setGravity(Gravity.CENTER_VERTICAL);
			setPadding(20, 10, 20, 10);
			LinearLayout.LayoutParams thumbnailLayoutParams = new LinearLayout.LayoutParams(
					mThumbnaiWidth, mThumbnailHeight);

			addView(mThumbnailLayout, thumbnailLayoutParams);
			addView(right);
		}

		@Override
		public void onThemeChanged() {
			Theme.updateTheme(this);
		}
	}
}