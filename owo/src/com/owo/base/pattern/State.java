package com.owo.base.pattern;

import com.owo.base.interfaces.mvc.IMessageHandler;

public interface State extends IMessageHandler {
	void onSwitchIn();
}
