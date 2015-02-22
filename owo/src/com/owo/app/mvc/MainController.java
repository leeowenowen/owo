package com.owo.app.mvc;

import com.owo.base.common.Param;
import com.owo.base.mvc.interfaces.IMessageHandler;

public class MainController implements IMessageHandler {
	private MainUiManager mUiManager = new MainUiManager();

	@Override
	public boolean handleMessage(int id, Param in, Param out) {
		switch (id) {
		case MessageId.ShowMenuWidget:
		case MessageId.HideMenuWidget:
			return mUiManager.handleMessage(id, in, out);
		default:
			return false;
		}
	}
}
