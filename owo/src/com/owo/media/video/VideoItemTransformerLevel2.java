package com.owo.media.video;

import android.content.Context;
import android.widget.LinearLayout;

import com.owo.app.theme.Theme;
import com.owo.base.util.DimensionUtil;
import com.owo.ui.utils.LP;

class VideoItemTransformerLevel2 extends AbsVideoItemTransformer {
	@Override
	protected AbsVideoItemView createItemView(Context context) {
		return new VideoItemViewLevel2(context);
	}

	private class VideoItemViewLevel2 extends AbsVideoItemView {
		private static final int sTitleSize = 18;
		private static final int sContentSize = 12;

		public VideoItemViewLevel2(Context context) {
			super(context);
			onThemeChanged();
		}

		@Override
		protected void setupLayout(Context context) {
			mThumbnaiWidth = DimensionUtil.dip2Pixel(300);
			mThumbnailHeight = DimensionUtil.dip2Pixel(200);
			mTitle.setTextSize(sTitleSize);
			mSize.setTextSize(sContentSize);
			mDuration.setTextSize(sContentSize);
			mResolution.setTextSize(sContentSize);
			mPath.setTextSize(sContentSize);

			LinearLayout attributes = new LinearLayout(context);
			attributes.addView(mSize, LP.L0W1);
			attributes.addView(mDuration, LP.L0W1);
			attributes.addView(mResolution, LP.L0W1);

			LinearLayout right = new LinearLayout(context);
			right.setOrientation(LinearLayout.VERTICAL);
			right.addView(mTitle);
			right.addView(mPath);
			right.addView(attributes);
			right.setPadding(10, 0, 0, 0);

			setPadding(20, 10, 20, 10);
			addView(mThumbnailLayout);
			addView(right);
		}

		@Override
		public void onThemeChanged() {
			Theme.updateTheme(this);
		}
	}
}