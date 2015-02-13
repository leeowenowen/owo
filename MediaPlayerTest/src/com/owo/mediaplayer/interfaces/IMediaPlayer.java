package com.owo.mediaplayer.interfaces;

import android.content.Context;
import android.view.SurfaceHolder;

public interface IMediaPlayer {
	void create(Context context, SurfaceHolder surface);

	void destroy();

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

	void addListener(Listener observer);

	void removeListener(Listener observer);

	public interface Listener {
		void onStart();

		void onStop();

		void onPause();

		void onResume();

		void onProgressChanged(int progress);

		void onLoadStart(String msg);

		void onLoadStop();

		void onSizeChanged(int w, int h);

		void onError(int error);

		void onReset();

		void onMetaInfo(IMetaInfo metaInfo);

		void onComplete();

		void onBufferingUpdate(int percent);
	}
}
