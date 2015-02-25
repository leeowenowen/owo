package com.owo.media.video;

import android.content.Context;
import android.widget.LinearLayout;

import com.owo.base.util.DimensionUtil;
import com.owo.base.util.SysInfoHelper;
import com.owo.ui.utils.LP;

class VideoItemTransformerLevel1 extends AbsVideoItemTransformer {
	@Override
	protected AbsVideoItemView createItemView(Context context) {
		return new VideoItemViewLevel1(context);
	}

	private class VideoItemViewLevel1 extends AbsVideoItemView {
		private static final int sTitleSize = 15;
		private static final int sContentSize = 12;

		public VideoItemViewLevel1(Context context) {
			super(context);
		}

		@Override
		protected void setupLayout(Context context) {
			mThumbnaiWidth = DimensionUtil.screenWidth();
			mThumbnailHeight = DimensionUtil.screenHeight();
			mTitle.setTextSize(sTitleSize * 2);
			mSize.setTextSize(sContentSize * 2);
			mDuration.setTextSize(sContentSize);
			mResolution.setTextSize(sContentSize);
			mPath.setTextSize(sContentSize);

			LinearLayout attributes = new LinearLayout(context);
			attributes.addView(mSize, LP.L0W1);
			attributes.addView(mDuration, LP.L0W1);
			attributes.addView(mResolution, LP.L0W1);

			setOrientation(LinearLayout.VERTICAL);
			setPadding(20, 10, 20, 10);
			addView(mThumbnailLayout);
			addView(mTitle);
			addView(mPath);
			addView(attributes);
		}
	}
}