package com.owo.app.system_settings;

import java.util.HashMap;
import java.util.Map;

public class SystemSettingsData {
	Map<String, String> mMap = new HashMap<String, String>();

	public String get(String key) {
		return mMap.get(key);
	}

	public void put(String key, String value) {
		mMap.put(key, value);
	}
}
