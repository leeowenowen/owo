package com.owo.app.main.ui;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.owo.app.common.ContextManager;
import com.owo.app.language.Language;
import com.owo.app.language.LanguageObserver;
import com.owo.app.language.LanguageResourceKeys;
import com.owo.app.theme.ThemeObserver;
import com.owo.base.pattern.Instance;
import com.owo.media.MediaStoreData;
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
		LinearLayout.LayoutParams itemLayoutParams = new LinearLayout.LayoutParams(150, 150);
		addView(mSearchView, itemLayoutParams);
		addView(mMenuView, itemLayoutParams);
	}

	private void setupListener() {
		mMenuView.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				ConfigurablePopupWindow popup = new ConfigurablePopupWindow();
				popup.setOutsideTouchable(false);
				popup.setFocusable(true);
				popup.setBackgroundDrawable(new ColorDrawable(Color.argb(100, 100, 0, 0)));
				popup.setContentView(new MenuWidget(ContextManager.context()));
				popup.setWidth(300);
				popup.setHeight(300);

				popup.showAsDropDown(mMenuView);
			}
		});

		mSearchView.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Instance.of(MediaStoreData.class).searchText(mSearchEdit.getText().toString());
			}
		});
	}

	@Override
	public boolean dispatchKeyEvent(KeyEvent event) {
		// TODO Auto-generated method stub
		return super.dispatchKeyEvent(event);
	}

	@Override
	public void onLanguageChanged() {
		mSearchEdit.setHint(Instance.of(Language.class).get(LanguageResourceKeys.Search));
	}

	@Override
	public void onThemeChanged() {
		mSearchView.invalidate();
		mMenuView.invalidate();
	}
}
