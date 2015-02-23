package com.owo.media.interfaces;

public interface IAbsMediaProviderFactory {
	ICursorTransformer createCursorTransformer(int level, int mediaType);

	IMediaQueryer createMediaQueryer(int mediaType);
}
