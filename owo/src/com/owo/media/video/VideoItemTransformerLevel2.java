package com.owo.media.video;

import android.content.Context;
import android.view.Gravity;
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
			mThumbnaiWidth = DimensionUtil.w(300);
			mThumbnailHeight = DimensionUtil.h(200);
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
			right.setPadding(DimensionUtil.w(10), 0, 0, 0);

			setGravity(Gravity.CENTER_VERTICAL);
			setPadding(DimensionUtil.w(20), DimensionUtil.h(10), DimensionUtil.w(20),
					DimensionUtil.h(10));
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