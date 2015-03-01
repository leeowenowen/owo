package com.owo.app.main.ui;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.owo.app.common.ContextManager;
import com.owo.app.language.Language;
import com.owo.app.language.LanguageObserver;
import com.owo.app.language.LanguageResourceKeys;
import com.owo.app.main.ui.MenuWidget.MenuWidgetClient;
import com.owo.app.theme.Theme;
import com.owo.app.theme.ThemeObserver;
import com.owo.base.pattern.Singleton;
import com.owo.base.util.DimensionUtil;
import com.owo.media.MediaData;
import com.owo.ui.ConfigurablePopupWindow;
import com.owo.ui.shape.VF;
import com.owo.ui.utils.LP;

public class SearchWidget extends LinearLayout implements LanguageObserver, ThemeObserver {
	private TextView mTitle;
	private EditText mSearchEdit;
	private View mSearchView;
	private View mMenuView;

	public SearchWidget(Context context) {
		super(context);

		initComponents(context);
		setupListener();
		onLanguageChanged();
		onThemeChanged();
	}

	private void initComponents(Context context) {
		mTitle = new TextView(context);
		mSearchEdit = new EditText(context);
		mSearchView = VF.of(context, VF.ViewID.Search);
		mMenuView = VF.of(context, VF.ViewID.Menu);

		setGravity(Gravity.CENTER_VERTICAL);
		LinearLayout.LayoutParams titleLayoutParams = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT);
		titleLayoutParams.setMargins(10, 0, 10, 0);
		addView(mTitle);
		addView(mSearchEdit, LP.L0W1);
		int size = DimensionUtil.rowHeight();
		LinearLayout.LayoutParams itemLayoutParams = new LinearLayout.LayoutParams(size, size);
		addView(mSearchView, itemLayoutParams);
		addView(mMenuView, itemLayoutParams);
	}

	private void setupListener() {
		mMenuView.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				final ConfigurablePopupWindow popup = new ConfigurablePopupWindow();
				popup.setOutsideTouchable(false);
				popup.setFocusable(true);
				popup.setBackgroundDrawable(new ColorDrawable(Color.argb(100, 100, 0, 0)));
				MenuWidget menuWidget = new MenuWidget(ContextManager.context());
				menuWidget.client(new MenuWidgetClient() {
					@Override
					public void onItemSelected() {
						popup.dismiss();
					}
				});
				popup.setContentView(menuWidget);
				popup.setWidth(DimensionUtil.w(300));
				popup.setHeight(DimensionUtil.h(500));

				popup.showAsDropDown(mMenuView);
			}
		});

		mSearchEdit.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				// TODO Auto-generated method stub

			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
				// TODO Auto-generated method stub

			}

			@Override
			public void afterTextChanged(Editable s) {
				Singleton.of(MediaData.class).searchText(mSearchEdit.getText().toString());
			}
		});

		mSearchView.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {

			}
		});
	}

	@Override
	public void onLanguageChanged() {
		mSearchEdit.setHint(Singleton.of(Language.class).get(LanguageResourceKeys.Search));
	}

	@Override
	public void onThemeChanged() {
		mSearchView.invalidate();
		mMenuView.invalidate();
		int textColor = Singleton.of(Theme.class).textColor();
		mSearchEdit.setTextColor(textColor);
		mSearchEdit.setHintTextColor(Color.argb(100, Color.red(textColor), Color.green(textColor),
				Color.blue(textColor)));
		int bgColor = Singleton.of(Theme.class).bgColor();
		mSearchEdit.setBackgroundColor(bgColor);
		setBackgroundColor(bgColor);
	}
}
