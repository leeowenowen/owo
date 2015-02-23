package com.owo.base.interfaces.mvc;

import com.owo.base.common.Param;

public interface ICommandProcessor {
	boolean processCommand(int id, Param in, Param out);
}
