package com.owo.mediastore.interfaces;

public interface IAbsMediaProviderFactory {
	ICursorTransformer createCursorTransformer(int level, int mediaType);

	IMediaQueryer createMediaQueryer(int mediaType);
}
