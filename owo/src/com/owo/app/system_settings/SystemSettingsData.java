package com.owo.app.system_settings;

import java.util.HashMap;
import java.util.Map;

import com.owo.base.util.TextHelper;

public class SystemSettingsData {
	Map<String, String> mMap = new HashMap<String, String>();

	private SystemSettingsData() {
		set(SystemSettingKeys.SupportedLanguage, "zh-CN##en-US");
		set(SystemSettingKeys.Language, "zh-CN");
	}

	public String get(String key) {
		return mMap.get(key);
	}

	public void set(String key, String value) {
		mMap.put(key, value);
	}

	public void append(String key, String value) {
		String old = get(key);
		if (!TextHelper.isEmptyOrSpaces(old)) {
			value = old + "##" + value;
		}
		set(key, value);
	}
	
	public static interface Observer
	{
		void onDataChanged(String key, String oldValue, String newValue);
	}
	
	//public 
}
