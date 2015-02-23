package com.owo.app.main.widget;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.owo.app.common.ContextManager;
import com.owo.base.pattern.Instance;
import com.owo.media.MediaStoreData;
import com.owo.mediaplayer.view.shape.VF;
import com.owo.view.utils.LP;
import com.owo.widget.interfaces.IConfigurable;

public class SearchWidget extends LinearLayout implements IConfigurable {
	private TextView mTitle;
	private EditText mSearchEdit;
	private View mSearchView;
	private View mMenuView;

	public SearchWidget(Context context) {
		super(context);

		initComponents(context);
		setupListener();
		updateLanguage();
		updateTheme();
	}

	private void initComponents(Context context) {
		mTitle = new TextView(context);
		mSearchEdit = new EditText(context);
		mSearchView = VF.of(context, VF.ViewID.Search);
		mMenuView = VF.of(context, VF.ViewID.Menu);

		setGravity(Gravity.CENTER_VERTICAL);
		LinearLayout.LayoutParams titleLayoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT);
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
				PopupWindow popup = new PopupWindow();
				popup.setOutsideTouchable(false);
				popup.setFocusable(true);
				popup.setBackgroundDrawable(new ColorDrawable(Color.argb(100, 100, 0, 0)));
				popup.setContentView(new MenuWidget(ContextManager.context()));
				popup.setWidth(600);
				popup.setHeight(600);

				popup.showAsDropDown(mMenuView);
			}
		});

		// mSearchEdit.addTextChangedListener(mTextWatcher);
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
	public void updateLanguage() {
		// mTitle.setText("视频播放器");
		mSearchEdit.setHint("搜索");
	}

	@Override
	public void updateTheme() {
		// TODO Auto-generated method stub

	}
}
