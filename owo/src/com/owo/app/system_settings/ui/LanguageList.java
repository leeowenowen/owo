package com.owo.app.system_settings.ui;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.owo.app.language.LanguageObserver;
import com.owo.app.system_settings.SystemSettingKeys;
import com.owo.app.system_settings.SystemSettingsData;
import com.owo.app.theme.Theme;
import com.owo.app.theme.ThemeObserver;
import com.owo.base.pattern.Instance;

public class LanguageList extends ListView {
	public LanguageList(Context context) {
		super(context);
		setAdapter(new LanguageListAdapter());
	}

	private class LanguageListAdapter extends BaseAdapter {
		private String[] mSupportedLanguages;

		LanguageListAdapter() {
			String supportedLanguages = Instance.of(SystemSettingsData.class).get(
					SystemSettingKeys.SupportedLanguage);
			mSupportedLanguages = supportedLanguages.split("##");
		}

		@Override
		public int getCount() {
			return mSupportedLanguages.length;
		}

		@Override
		public Object getItem(int position) {
			return mSupportedLanguages[position];
		}

		@Override
		public long getItemId(int position) {
			return 0;
		}

		@Override
		public View getView(final int position, View convertView, ViewGroup parent) {
			ItemView view = null;
			if (convertView != null) {
				view = (ItemView) convertView;
				setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						String language = mSupportedLanguages[position];
						Instance.of(SystemSettingsData.class).set(SystemSettingKeys.Language,
								language);
					}
				});
			}
			view.updateData(mSupportedLanguages[position]);
			return view;
		}

		private class ItemView extends LinearLayout implements ThemeObserver, LanguageObserver {
			private TextView mLanguage;
			private ImageView mSelectedMark;

			public ItemView(Context context) {
				super(context);

				mLanguage = new TextView(context);
				mSelectedMark = new ImageView(context);

				LinearLayout.LayoutParams lParams = new LinearLayout.LayoutParams(
						LinearLayout.LayoutParams.WRAP_CONTENT,
						LinearLayout.LayoutParams.MATCH_PARENT);
				lParams.gravity = Gravity.LEFT;
				LinearLayout.LayoutParams rParams = new LinearLayout.LayoutParams(
						LinearLayout.LayoutParams.WRAP_CONTENT,
						LinearLayout.LayoutParams.MATCH_PARENT);
				rParams.gravity = Gravity.RIGHT;
				addView(mLanguage, lParams);
				addView(mSelectedMark, rParams);
			}

			public void updateData(String language) {
				String curLanguage = Instance.of(SystemSettingsData.class).get(
						SystemSettingKeys.Language);
				mSelectedMark.setVisibility(language.equals(curLanguage) ? VISIBLE : GONE);
				onThemeChanged();
			}

			@Override
			public void onLanguageChanged() {
				// TODO Auto-generated method stub

			}

			@Override
			public void onThemeChanged() {
				mLanguage.setTextColor(Instance.of(Theme.class).textColor());
			}
		}
	}

}
