package com.owo.app.mvc;

import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.PopupWindow;

import com.owo.app.ContextManager;
import com.owo.app.ParamKey;
import com.owo.app.widget.MenuWidget;
import com.owo.base.common.Param;
import com.owo.base.mvc.interfaces.IMessageHandler;
import com.owo.widget.owo_PopupWindow;

public class MainUiManager implements IMessageHandler {
	private IMessageHandler mMessageHandler;
	private owo_PopupWindow mMenuPopup;

	public void showMenuPopup(Param params) {
		View anchorView = params.get(ParamKey.View);
		if (mMenuPopup == null) {
			mMenuPopup = new owo_PopupWindow(ContextManager.context());
			mMenuPopup.setContentView(new MenuWidget(ContextManager.context(),
					this));
			mMenuPopup.setWidth(600);
			mMenuPopup.setHeight(600);
		}
		if (mMenuPopup.isShowing()) {
			mMenuPopup.update();
		} else {
			mMenuPopup.showAsDropDown(anchorView);
			//mMenuPopup.showAsDropDown(anchorView);
		}
	}

	public void hideMenuPopup(Param params) {
		mMenuPopup.dismiss();
	}

	@Override
	public boolean handleMessage(int id, Param in, Param out) {
		switch (id) {
		case MessageId.ShowMenuWidget:
			showMenuPopup(in);
			return true;
		case MessageId.HideMenuWidget:
			hideMenuPopup(in);
			return true;
		default:
			return false;
		}
	}
}
