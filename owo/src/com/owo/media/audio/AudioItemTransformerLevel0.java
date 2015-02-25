package com.owo.media.audio;

import android.content.Context;
import android.widget.LinearLayout;

import com.owo.app.theme.Theme;
import com.owo.ui.utils.LP;

class AudioItemTransformerLevel0 extends AbsAudioItemTransformer {
	@Override
	protected AbsAudioItemView createItemView(Context context) {
		return new AudioItemViewLevel0(context);
	}

	private class AudioItemViewLevel0 extends AbsAudioItemView {
		private static final int sTitleSize = 15;
		private static final int sContentSize = 12;

		public AudioItemViewLevel0(Context context) {
			super(context);
			onThemeChanged();
		}

		@Override
		protected void setupLayout(Context context) {
			mTitle.setTextSize(sTitleSize);
			mSize.setTextSize(sContentSize);
			mDuration.setTextSize(sContentSize);
			mPath.setTextSize(sContentSize);

			LinearLayout attributes = new LinearLayout(context);
			attributes.addView(mSize, LP.L0W1);
			attributes.addView(mDuration, LP.L0W1);

			setOrientation(LinearLayout.VERTICAL);
			addView(mTitle);
			addView(mPath);
			addView(attributes);
		}

		@Override
		public void onThemeChanged() {
			Theme.updateTheme(this);
		}
	}

}