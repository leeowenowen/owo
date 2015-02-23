package com.owo.media.video;

import android.content.Context;
import android.widget.LinearLayout;

import com.owo.base.util.SysInfoHelper;
import com.owo.mediaplayer.view.utils.LP;

class VideoItemTransformerLevel0 extends AbsVideoItemTransformer {
	@Override
	protected AbsVideoItemView createItemView(Context context) {
		return new VideoItemViewLevel0(context);
	}

	class VideoItemViewLevel0 extends AbsVideoItemView {
		private static final int sTitleSize = 15;
		private static final int sContentSize = 12;

		public VideoItemViewLevel0(Context context) {
			super(context);
		}

		@Override
		protected void setupLayout(Context context) {
			mThumbnaiWidth = SysInfoHelper.displayMetrics().widthPixels;
			mThumbnailHeight = SysInfoHelper.displayMetrics().heightPixels;
			mTitle.setTextSize(sTitleSize);
			mSize.setTextSize(sContentSize);
			mDuration.setTextSize(sContentSize);
			mResolution.setTextSize(sContentSize);
			mPath.setTextSize(sContentSize);

			LinearLayout attributes = new LinearLayout(context);
			attributes.addView(mSize, LP.L0W1);
			attributes.addView(mDuration, LP.L0W1);
			attributes.addView(mResolution, LP.L0W1);

			setOrientation(LinearLayout.VERTICAL);
			addView(mTitle);
			addView(mPath);
			addView(attributes);
			addView(mThumbnailLayout);
		}
	}
}