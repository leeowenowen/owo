package com.owo.mediaplayer.interfaces;

import android.content.res.Configuration;
import android.view.SurfaceHolder;

public interface IMediaPlayerController {
	void create();

	void destroy();

	void attachSurface(SurfaceHolder surface);

	void detachSurface();

	void uri(String uri);

	void start();

	void stop();

	void pause();

	void resume();

	void reset();

	int width();

	int height();

	/**
	 * Length related operation, all in the unit of milliseconds
	 */
	int duration();

	int current();

	void seek(int position);

	void client(Client client);

	void pre();

	void next();

	void fastForward();

	void fastBackward();

	void seek(int progress, int max);

	void enterFullScreen();

	void exitFullScreen();

	void onConfigurationChanged(Configuration newConfig);

	void resize(int w, int h);

	public interface Client {
		void onStart();

		void onStop();

		void onPause();

		void onResume();

		void onPre();

		void onCanPre(boolean flag);

		void onNext();

		void onCanNext(boolean flag);

		void onFastFward();

		void onFastBackward();

		void onEnterFullScreen();

		void onExitFullScreen();

		void onProgressChanged(int progress);

		void onLoadStart(String msg);

		void onLoadStop();

		void onSizeChanged(int w, int h);

		void onError(int error);

		void onReset();

		void onMetaInfo(IMetaInfo metaInfo);

		void onReceivedTimeInfo(String total, String cur);

		void onComplete();
	}
}
