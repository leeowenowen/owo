package com.owo.base.mvc.interfaces;

import com.owo.base.common.Param;

public interface IMessageHandler {
	boolean handleMessage(int id, Param in, Param out);
}
