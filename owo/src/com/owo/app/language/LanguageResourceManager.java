package com.owo.app.language;

import java.util.HashMap;
import java.util.Map;

public class LanguageResourceManager implements ILanguageResourceProvider {
	private ILanguageResourceProvider mProvider;

	private LanguageResourceManager() {
	}

	public void setProvider(ILanguageResourceProvider provider) {
		mProvider = provider;
	}

	@Override
	public String get(LanguageResourceKeys key) {
		return mProvider.get(key);
	}

	public static LanguageResourceKeys toLanguageResourceKey(String language) {
		if ("zh-CN".equals(language)) {
			return LanguageResourceKeys.ZH_CN;
		} else if ("en-US".equals(language)) {
			return LanguageResourceKeys.EN_US;
		}
		return LanguageResourceKeys.None;
	}

	private Map<String, ILanguageResourceProvider> mProviders = new HashMap<String, ILanguageResourceProvider>();

	public void add(String key, ILanguageResourceProvider provider) {
		mProviders.put(key, provider);
	}

	public void remove(String key) {
		mProviders.remove(key);
	}
}
