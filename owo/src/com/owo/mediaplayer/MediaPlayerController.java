package com.owo.mediaplayer;

import android.content.Context;
import android.content.res.Configuration;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.SurfaceHolder;

import com.owo.app.ContextManager;
import com.owo.mediaplayer.android.SysMediaPlayer;
import com.owo.mediaplayer.interfaces.IMediaPlayer;
import com.owo.mediaplayer.interfaces.IMediaPlayerController;
import com.owo.mediaplayer.interfaces.IMetaInfo;
import com.owo.mediaplayer.interfaces.IPlayItem;
import com.owo.mediaplayer.interfaces.IPlayList;
import com.owo.mediaplayer.interfaces.ITimeFormatter;

public class MediaPlayerController implements IMediaPlayerController,
		IMediaPlayer.Listener {
	private static final String TAG = "MediaPlayerController";
	private IMediaPlayer mMediaPlayer;

	private enum State {
		Error("Error"), //
		Init("Init"), //
		Preparing("Preparing"), //
		Prepared("Prepared"), //
		Playing("Playing"), //
		Paused("Paused"), //
		Finished("Finished"); //

		private final String mMsg;

		State(String msg) {
			mMsg = msg;
		}

		public String toString() {
			return mMsg;
		}
	};

	private State mState = State.Init;

	private void switchState(State state) {
		mState = state;
	}

	@Override
	public void create(Context context, SurfaceHolder surface) {
		mMediaPlayer = new SysMediaPlayer();
		mMediaPlayer.addListener(this);
		mMediaPlayer.create(context, surface);
	}

	@Override
	public void destroy() {
		mMediaPlayer.destroy();
	}

	@Override
	public void stop() {
		// 1) check state
		if (mState != State.Playing) {
			Log.e(TAG, "Invalid state: stop on " + mState);
			return;
		}
		mMediaPlayer.stop();
		switchState(State.Paused);
	}

	@Override
	public void pause() {
		// 1) check state
		if (mState != State.Playing) {
			Log.e(TAG, "Invalid state: pause on " + mState);
			return;
		}
		mMediaPlayer.pause();
		switchState(State.Paused);
	}

	@Override
	public void resume() {
		// 1) check state
		if (mState != State.Paused && mState != State.Finished) {
			Log.e(TAG, "Invalid resume: resume on " + mState);
			return;
		}
		// if (mState == State.Paused) {
		mMediaPlayer.resume();
		switchState(State.Playing);
		// }
		// else if (mState == State.Finished) {
		// start();
		// }

	}

	@Override
	public int width() {
		return mMediaPlayer.width();
	}

	@Override
	public int height() {
		return mMediaPlayer.height();
	}

	@Override
	public int duration() {
		return mMediaPlayer.duration();
	}

	@Override
	public int current() {
		return mMediaPlayer.current();
	}

	@Override
	public void seek(int position) {
		mMediaPlayer.seek(position);
	}

	private IPlayList mPlayList;
	private int mCurIndex;

	public void updatePlayList(IPlayList list) {
		mPlayList = list;
		if (mPlayList.size() > 0) {
			setCurPlayItem(mCurIndex);
		}
	}

	public void setCurPlayItem(int index) {
		mCurIndex = index;
		mMediaPlayer.reset();
		IPlayItem item = mPlayList.at(mCurIndex);
		uri(item.source());
		start();
		checkPreNextState();
	}

	private void checkPreNextState() {
		if (mCurIndex == 0) {

		}
	}

	@Override
	public void pre() {
		setCurPlayItem(--mCurIndex);
		checkPreNextState();
	}

	@Override
	public void next() {
		setCurPlayItem(++mCurIndex);
		checkPreNextState();
	}

	@Override
	public void fastForward() {
		// TODO Auto-generated method stub

	}

	@Override
	public void fastBackward() {
		// TODO Auto-generated method stub

	}

	private DisplayMetrics mDisplayMetrics;

	private void updateDisplayMetric() {
		mDisplayMetrics = new DisplayMetrics();
		ContextManager.activity().getWindowManager().getDefaultDisplay()
				.getMetrics(mDisplayMetrics);
	}

	private int mLastWidth;
	private int mLastHeight;

	@Override
	public void resize(int w, int h) {
		mLastWidth = w;
		mLastHeight = h;
	}

	@Override
	public void enterFullScreen() {
		if (mDisplayMetrics == null) {
			updateDisplayMetric();
		}
		mClient.onEnterFullScreen();
		mClient.onSizeChanged(mDisplayMetrics.widthPixels,
				mDisplayMetrics.heightPixels);
	}

	@Override
	public void exitFullScreen() {
		mClient.onExitFullScreen();
		mClient.onSizeChanged(mLastWidth, mLastHeight);
	}

	@Override
	public void seek(int progress, int max) {
		int position = progress * mMediaPlayer.duration() / max;
		mMediaPlayer.seek(position);
	}

	@Override
	public void start() {
		// 1) check state
		if (mState != State.Init && mState != State.Finished) {
			Log.e(TAG, "Invalid state: start on " + mState);
			return;
		}
		if (mMediaPlayer == null) {
			return;
		}
		mMediaPlayer.start();
	}

	@Override
	public void reset() {
		mMediaPlayer.reset();
	}

	@Override
	public void uri(String uri) {
		mMediaPlayer.uri(uri);
	}

	private Client mClient;

	@Override
	public void client(Client client) {
		mClient = client;
	}

	/**
	 * IMediaPlayer.Listener
	 */
	@Override
	public void onStart() {
		switchState(State.Playing);
		mClient.onStart();
		notifyTimeChanged();
	}

	private void notifyTimeChanged() {
		mClient.onReceivedTimeInfo(
				mTimeFormatter.format(mMediaPlayer.duration()),
				mTimeFormatter.format(mMediaPlayer.current()));
	}

	@Override
	public void onStop() {
		mClient.onStop();
	}

	@Override
	public void onPause() {
		mClient.onPause();
	}

	@Override
	public void onResume() {
		mClient.onResume();
	}

	@Override
	public void onProgressChanged(int position) {
		int progress = position * 100 / duration();
		mClient.onProgressChanged(progress);
		notifyTimeChanged();
	}

	@Override
	public void onLoadStart(String msg) {
		mClient.onLoadStart(msg);
	}

	@Override
	public void onLoadStop() {
		mClient.onLoadStop();
	}

	@Override
	public void onSizeChanged(int w, int h) {
		if (mLastHeight == 0 && mLastWidth == 0) {
			mLastWidth = w;
			mLastHeight = h;
		}
		mClient.onSizeChanged(w, h);
	}

	@Override
	public void onError(int error) {
		switchState(State.Error);
		mClient.onError(error);
	}

	@Override
	public void onReset() {
		mClient.onReset();
	}

	@Override
	public void onMetaInfo(IMetaInfo metaInfo) {
		mClient.onMetaInfo(metaInfo);
	}

	@Override
	public void onComplete() {
		// TODO: config it
		mMediaPlayer.seek(0);
		mClient.onComplete();
		switchState(State.Finished);
	}

	@Override
	public void onBufferingUpdate(int percent) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {

		} else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {

		}
		updateDisplayMetric();
	}

	/**
	 * Time formatter
	 */
	private ITimeFormatter mTimeFormatter;

	public MediaPlayerController timeFormatter(ITimeFormatter timeFormatter) {
		mTimeFormatter = timeFormatter;
		return this;
	}

}
