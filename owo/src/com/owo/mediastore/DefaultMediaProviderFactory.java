package com.owo.mediastore;

import com.owo.mediastore.interfaces.IAbsMediaProviderFactory;
import com.owo.mediastore.interfaces.ICursorTransformer;
import com.owo.mediastore.interfaces.IMediaQueryer;

public class DefaultMediaProviderFactory implements IAbsMediaProviderFactory {

	@Override
	public ICursorTransformer createCursorTransformer(int level, int mediaType) {
		return null;
	}

	@Override
	public IMediaQueryer createMediaQueryer(int mediaType) {
		return null;
	}

}
