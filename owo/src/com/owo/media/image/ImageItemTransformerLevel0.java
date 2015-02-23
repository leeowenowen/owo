package com.owo.media.image;

import android.content.Context;
import android.widget.LinearLayout;

import com.owo.base.util.DimensionUtil;
import com.owo.base.util.SysInfoHelper;
import com.owo.view.utils.LP;

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
		}

		@Override
		protected void setupLayout(Context context) {
			mThumbnaiWidth = 300;// SysInfoHelper.displayMetrics().widthPixels;
			mThumbnailHeight = DimensionUtil.screenHeight();
			mTitle.setTextSize(sTitleSize);
			mSize.setTextSize(sContentSize);
			mWH.setTextSize(sContentSize);
			mPath.setTextSize(sContentSize);

			LinearLayout attributes = new LinearLayout(context);
			attributes.addView(mSize, LP.L0W1);
			attributes.addView(mWH, LP.L0W1);

			setOrientation(LinearLayout.VERTICAL);
			addView(mTitle);
			addView(mPath);
			addView(attributes);
			addView(mThumbnailLayout);

			// mThumbnaiWidth = SysInfoHelper.displayMetrics().widthPixels;
			// mThumbnailHeight = SysInfoHelper.displayMetrics().heightPixels;
			// mTitle.setTextSize(sTitleSize);
			// mSize.setTextSize(sContentSize);
			// mDuration.setTextSize(sContentSize);
			// mResolution.setTextSize(sContentSize);
			// mPath.setTextSize(sContentSize);
			//
			// LinearLayout attributes = new LinearLayout(context);
			// attributes.addView(mSize, LP.L0W1);
			// attributes.addView(mDuration, LP.L0W1);
			// attributes.addView(mResolution, LP.L0W1);
			//
			// setOrientation(LinearLayout.VERTICAL);
			// addView(mTitle);
			// addView(mPath);
			// addView(attributes);
			// addView(mThumbnailLayout);
		}
	}
}