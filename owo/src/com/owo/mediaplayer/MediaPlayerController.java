package com.owo.mediaplayer;

import android.content.res.Configuration;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.SurfaceHolder;

import com.owo.app.common.BaseHandler;
import com.owo.app.common.ContextManager;
import com.owo.mediaplayer.android.SysMediaPlayer;
import com.owo.mediaplayer.interfaces.IMediaPlayer;
import com.owo.mediaplayer.interfaces.IMediaPlayerController;
import com.owo.mediaplayer.interfaces.IMetaInfo;
import com.owo.mediaplayer.interfaces.IPlayItem;
import com.owo.mediaplayer.interfaces.IPlayList;
import com.owo.mediaplayer.interfaces.ITimeFormatter;

//TODO: add pending state to avoid re-operation(re-reset, re-destroy, re-start)
public class MediaPlayerController implements IMediaPlayerController, IMediaPlayer.Listener {
	private static final String TAG = "MediaPlayerController";
	private IMediaPlayer mMediaPlayer;

	private enum State {
		Error("Error"), //
		Init("Init"), //
		Preparing("Preparing"), //
		Prepared("Prepared"), //
		Playing("Playing"), //
		Paused("Paused"), //
		Reseted("Reseted"), //
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
	public void attachSurface(SurfaceHolder surface) {
		mMediaPlayer.attachSurface(surface);
	}

	@Override
	public void detachSurface() {
		if (mMediaPlayer != null) {
			mMediaPlayer.detachSurface();
		}
	}

	// do nothing here
	@Override
	public void create() {
		mMediaPlayer = new SysMediaPlayer();
		mMediaPlayer.addListener(this);
		mMediaPlayer.create(ContextManager.context());
	}

	@Override
	public void destroy() {
		mMediaPlayer.reset();
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
		if (mState != State.Paused && mState != State.Finished && mState != State.Reseted) {
			Log.e(TAG, "Invalid resume: resume on " + mState);
			return;
		}

		mMediaPlayer.resume();
		switchState(State.Playing);
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

	public int curPlayItemIndex() {
		return mCurIndex;
	}

	public void playList(IPlayList list) {
		mPlayList = list;
		setCurPlayItem(mCurIndex, false);
	}

	public void playList(IPlayList list, int index) {
		mPlayList = list;
		setCurPlayItem(index, false);
	}

	public void setCurPlayItem(int index, boolean start) {
		mCurIndex = index;
		IPlayItem item = mPlayList.at(mCurIndex);
		uri(item.source());
		if (start) {
			start();
			checkPreNextState();
		}
	}

	private void checkPreNextState() {
		mClient.onCanPre(mCurIndex > 0);
		mClient.onCanNext(mCurIndex < mPlayList.size());
	}

	@Override
	public void pre() {
		if (mCurIndex > 0) {
			stopProgressUpdateTime();
			mMediaPlayer.reset();
			setCurPlayItem(--mCurIndex, true);
		}

		checkPreNextState();
	}

	@Override
	public void next() {
		if (mCurIndex < mPlayList.size() - 1) {
			stopProgressUpdateTime();
			mMediaPlayer.reset();
			setCurPlayItem(++mCurIndex, true);
		}
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
		mClient.onSizeChanged(w, h);
	}

	private boolean mIsInFullScreen;

	@Override
	public void enterFullScreen() {
		if (mDisplayMetrics == null) {
			updateDisplayMetric();
		}
		mClient.onEnterFullScreen();
		mClient.onSizeChanged(mDisplayMetrics.widthPixels, mDisplayMetrics.heightPixels);
		mIsInFullScreen = true;
	}

	@Override
	public void exitFullScreen() {
		mClient.onExitFullScreen();
		mClient.onSizeChanged(mLastWidth, mLastHeight);
		mIsInFullScreen = false;
	}

	@Override
	public void seek(int progress, int max) {
		int position = progress * mMediaPlayer.duration() / max;
		mMediaPlayer.seek(position);
	}

	@Override
	public void start() {
		// 1) check state
		if (mState != State.Init && mState != State.Finished && mState != State.Reseted) {
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
		startProgressUpdateTimer();
	}

	@Override
	public void onStop() {
		stopProgressUpdateTime();
		mClient.onStop();
	}

	@Override
	public void onPause() {
		stopProgressUpdateTime();
		mClient.onPause();
	}

	@Override
	public void onResume() {
		startProgressUpdateTimer();
		mClient.onResume();
	}

	@Override
	public void onProgressChanged(int position) {
		int progress = position * 100 / duration();
		mClient.onProgressChanged(progress);
		updateTimeInfo();
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
		if (mIsInFullScreen) {
			return;
		}
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
		switchState(State.Reseted);
		mClient.onReset();
	}

	@Override
	public void onMetaInfo(IMetaInfo metaInfo) {
		// TODO: check it later
		if (mIsInFullScreen) {
			return;
		}
		mClient.onMetaInfo(metaInfo);
	}

	@Override
	public void onComplete() {
		// TODO: make it a setting item
		stopProgressUpdateTime();
		mLastPosition = 0;
		mMediaPlayer.seek(0);
		mClient.onComplete();
		switchState(State.Finished);
		updateTimeInfo();
	}

	@Override
	public void onBufferingUpdate(int percent) {
		// TODO Auto-generated method stub

	}

	boolean mEnterFullScreenOnLandscape = true;

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		updateDisplayMetric();
		if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
			if (mEnterFullScreenOnLandscape) {
				enterFullScreen();
			}

		} else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
			if (mEnterFullScreenOnLandscape) {
				exitFullScreen();
			}
		}

	}

	/**
	 * Time formatter
	 */
	private ITimeFormatter mTimeFormatter = new DefaultTimeFormatter();

	public MediaPlayerController timeFormatter(ITimeFormatter timeFormatter) {
		mTimeFormatter = timeFormatter;
		return this;
	}

	private void updateTimeInfo() {
		mClient.onReceivedTimeInfo(mTimeFormatter.format(mMediaPlayer.duration()),
				mTimeFormatter.format(mMediaPlayer.current()));
	}

	private int mLastPosition;
	private Runnable mCheckProgressRunnable = new Runnable() {
		public void run() {
			int cur = mMediaPlayer.current();
			if (cur != mLastPosition) {
				onProgressChanged(cur);
				mLastPosition = cur;
			}
			startProgressUpdateTimer();
		}
	};

	// TODO: use scheduler service instead
	private void startProgressUpdateTimer() {
		BaseHandler.postDelayed(mCheckProgressRunnable, 200);
	}

	private void stopProgressUpdateTime() {
		BaseHandler.removeCallbacks(mCheckProgressRunnable);
	}

	@Override
	public IPlayList playList() {
		return mPlayList;
	}

	@Override
	public int currentPlayIndex() {
		return mCurIndex;
	}

	@Override
	public IPlayItem currentPlayItem() {
		return mPlayList.at(mCurIndex);
	}
}
