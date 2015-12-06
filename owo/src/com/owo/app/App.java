package com.owo.app;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.util.Log;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.owo.app.common.BaseHandler;
import com.owo.app.common.ContextManager;
import com.owo.base.pattern.Singleton;
import com.owo.media.ThumbnailCache;

public class App extends Application {
	@Override
	public void onCreate() {
		super.onCreate();

		// initialize
		ContextManager.init(this);
		BaseHandler.initialize();
		Singleton.of(ThumbnailCache.class).load();
		registerActivityLifecycleCallbacks(new DefaultActivityLifecycleCallbacks());

		Thread.setDefaultUncaughtExceptionHandler(new UncaughtExceptionHandler());
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
				getApplicationContext()).memoryCacheSize(1024 * 1024 * 4)
				.build();
		ImageLoader.getInstance().init(config);

	}

	@Override
	public void onTerminate() {
		BaseHandler.destroy();
		ContextManager.destroy();
		Singleton.destroy();
		super.onTerminate();
	}

	class UncaughtExceptionHandler implements Thread.UncaughtExceptionHandler {
		public void uncaughtException(Thread t, Throwable e) {
			Log.e("Master",
					"Uncaught exception met from thread " + t.getName(), e);
			android.os.Process.killProcess(android.os.Process.myPid());
		}
	}

	private class DefaultActivityLifecycleCallbacks implements
			ActivityLifecycleCallbacks {

		@Override
		public void onActivityCreated(Activity arg0, Bundle arg1) {
		}

		@Override
		public void onActivityDestroyed(Activity arg0) {

		}

		@Override
		public void onActivityPaused(Activity arg0) {
			Singleton.of(ThumbnailCache.class).save();
		}

		@Override
		public void onActivityResumed(Activity arg0) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onActivitySaveInstanceState(Activity arg0, Bundle arg1) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onActivityStarted(Activity arg0) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onActivityStopped(Activity arg0) {

		}

	}
}
