package com.owo.media.image;

import java.util.Random;

import android.content.Context;
import android.view.View;
import android.widget.AbsListView;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.ImageView.ScaleType;

import com.owo.app.theme.Theme;
import com.owo.base.util.DimensionUtil;
import com.owo.ui.utils.LP;
import com.owo.ui.utils.LU;
import com.owo.ui.utils.ViewUtil;

class ImageItemTransformerLevel1 extends AbsImageItemTransformer {
	@Override
	protected AbsImageItemView createItemView(Context context) {
		return new ImageItemViewLevel1(context);
	}

	private class ImageItemViewLevel1 extends AbsImageItemView {
		private static final int sTitleSize = 15;
		private static final int sContentSize = 12;
		private CheckBox mCheckBox;
		private FrameLayout mContainer;

		public ImageItemViewLevel1(Context context) {
			super(context);
			onThemeChanged();
		}

		@Override
		protected void setupLayout(Context context) {
			if (mContainer == null) {
				mThumbnail.setScaleType(ScaleType.CENTER_CROP);
				mCheckBox = new CheckBox(context);
				mCheckBox.setVisibility(View.GONE);
				mContainer = new FrameLayout(context);
				ViewUtil.removeFromParent(mThumbnail);
				mContainer.addView(mThumbnail, LP.FMM);
				mContainer.addView(mCheckBox, LP.FWWTR);
				addView(mContainer, LP.LMM());
				LU.setMargin(mCheckBox, 0, 20, 20, 0);
				LU.setMargin(mThumbnail, 10, 10, 10, 10);
				AbsListView.LayoutParams lp = new AbsListView.LayoutParams(
						DimensionUtil.displayMetrics().widthPixels / 3,
						DimensionUtil.displayMetrics().widthPixels / 3);
				setLayoutParams(lp);
			}

			// mThumbnaiWidth = DimensionUtil.w(300);
			// mThumbnailHeight = DimensionUtil.h(200);
			// mTitle.setTextSize(sTitleSize);
			// mSize.setTextSize(sContentSize);
			// mWH.setTextSize(sContentSize);
			// mPath.setTextSize(sContentSize);
			//
			// LinearLayout attributes = new LinearLayout(context);
			// attributes.addView(mSize, LP.L0W1);
			// attributes.addView(mWH, LP.L0W1);
			//
			// LinearLayout right = new LinearLayout(context);
			// right.setOrientation(LinearLayout.VERTICAL);
			// right.addView(mTitle);
			// right.addView(mPath);
			// right.addView(attributes);
			// right.setPadding(10, 0, 0, 0);
			//
			// setGravity(Gravity.CENTER_VERTICAL);
			// setPadding(20, 10, 20, 10);
			// LinearLayout.LayoutParams thumbnailLayoutParams = new
			// LinearLayout.LayoutParams(
			// mThumbnaiWidth, mThumbnailHeight);
			//
			// addView(mThumbnailLayout, thumbnailLayoutParams);
			// addView(right);
		}

		@Override
		public void onThemeChanged() {
			Theme.updateTheme(this);
		}

		@Override
		protected int thumbnailWidth() {
			return DimensionUtil.displayMetrics().widthPixels / 3;
		}

		@Override
		protected int thumbnailHeight() {
			return DimensionUtil.displayMetrics().widthPixels / 3;
		}
	}
}