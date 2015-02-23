package com.owo.app.widget;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.owo.app.ParamKey;
import com.owo.app.mvc.MessageId;
import com.owo.base.common.Param;
import com.owo.base.mvc.interfaces.IMessageHandler;
import com.owo.base.pattern.Instance;
import com.owo.media.MediaStoreData;
import com.owo.mediaplayer.view.shape.VF;
import com.owo.mediaplayer.view.utils.LP;
import com.owo.widget.interfaces.IConfigurable;

public class SearchWidget extends LinearLayout implements IConfigurable {
	private TextView mTitle;
	private EditText mSearchEdit;
	private View mSearchView;
	private View mMenuView;
	private IMessageHandler mMessageHandler;

	public SearchWidget(Context context, IMessageHandler messageHandler) {
		super(context);
		mMessageHandler = messageHandler;
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

		addView(mTitle, LP.L0W1);
		addView(mSearchEdit, LP.L0W1);
		LinearLayout.LayoutParams itemLayoutParams = new LinearLayout.LayoutParams(150, 150);
		addView(mSearchView, itemLayoutParams);
		addView(mMenuView, itemLayoutParams);
	}

	private TextWatcher mTextWatcher = new TextWatcher() {

		@Override
		public void onTextChanged(CharSequence s, int start, int before, int count) {
		}

		@Override
		public void beforeTextChanged(CharSequence s, int start, int count, int after) {
		}

		@Override
		public void afterTextChanged(Editable s) {
			Param param = Param.obtain().put(ParamKey.Value, s.toString());
			mMessageHandler.handleMessage(MessageId.SearchVideo, param, null);
			param.recycle();
		}
	};

	private void setupListener() {
		mMenuView.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Param param = Param.obtain().put(ParamKey.View, mMenuView);
				mMessageHandler.handleMessage(MessageId.ShowMenuWidget, param, null);
				param.recycle();
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
		mSearchEdit.setHint("搜索");
	}

	@Override
	public void updateTheme() {
		// TODO Auto-generated method stub

	}
}
