package com.owo.media.video;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.owo.app.theme.Theme;
import com.owo.base.pattern.Instance;
import com.owo.base.util.DimensionUtil;

class VideoItemTransformerLevel3 extends AbsVideoItemTransformer {
	@Override
	protected AbsVideoItemView createItemView(Context context) {
		return new VideoItemViewLevel3(context);
	}

	private class VideoItemViewLevel3 extends AbsVideoItemView {
		private static final int sTitleSize = 15;
		private static final int sContentSize = 12;

		public VideoItemViewLevel3(Context context) {
			super(context);
		}

		@Override
		protected void setupLayout(Context context) {
			mThumbnaiWidth = DimensionUtil.screenWidth();
			mThumbnailHeight = DimensionUtil.screenHeight();
			mTitle.setTextSize(sTitleSize * 2);
			mSize.setTextSize(sContentSize);
			mDuration.setTextSize(sContentSize);
			mResolution.setTextSize(sContentSize);
			mPath.setTextSize(sContentSize * 2 / 3);

			mSize.setTextColor(Color.WHITE);
			mDuration.setTextColor(Color.WHITE);
			mResolution.setTextColor(Color.WHITE);

			LinearLayout attributes = new LinearLayout(context);
			attributes.setOrientation(LinearLayout.VERTICAL);
			attributes.setBackgroundColor(Instance.of(Theme.class).bgColor());
			attributes.addView(mSize);
			attributes.addView(mDuration);
			attributes.addView(mResolution);

			setOrientation(LinearLayout.VERTICAL);

			mThumbnailLayout.addView(attributes, new FrameLayout.LayoutParams(
					FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT,
					Gravity.LEFT | Gravity.BOTTOM));
			addView(mThumbnailLayout);
			addView(mPath);
			addView(mTitle);
			setPadding(20, 10, 20, 0);
		}
	}
}