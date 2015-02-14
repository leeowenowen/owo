package com.owo.mediaplayer;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.SurfaceHolder;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

import com.owo.mediaplayer.core.VideoSurfaceView;
import com.owo.mediaplayer.interfaces.Callback;
import com.owo.mediaplayer.interfaces.IMediaPlayerController;
import com.owo.mediaplayer.interfaces.IMetaInfo;
import com.owo.mediaplayer.view.shape.Theme;
import com.owo.mediaplayer.view.shape.VF;
import com.owo.mediaplayer.view.shape.VF.ViewID;

public abstract class AbsMediaPlayerWidget extends FrameLayout implements
		IMediaPlayerController.Client {

	protected IMediaPlayerController mMediaPlayerController;

	protected VideoSurfaceView mSurfaceView;
	private boolean mSurfaceCreated;

	protected SeekBar mSeekBar;
	protected View mPause, mResume;
	protected View mStart, mStop;
	protected View mPre, mNext;
	protected View mFastForward, mFastBackward;
	protected View mEnterFullScreen, mExitFullScreen;
	protected TextView mEndTime, mCurrentTime;
	protected TextView mLoadingText;

	public AbsMediaPlayerWidget(Context context) {
		super(context);

		createUIComponents();
		initUIContent();
		setupListeners();
		setupLayout();
		initUIState();
	}

	private SurfaceHolder.Callback mSurfaceHolderCallback = new SurfaceHolder.Callback() {

		@Override
		public void surfaceDestroyed(SurfaceHolder holder) {
		}

		@Override
		public void surfaceCreated(SurfaceHolder holder) {
			holder.setKeepScreenOn(true);
			mSurfaceCreated = true;
			mCreateCallback.run(holder);
		}

		@Override
		public void surfaceChanged(SurfaceHolder holder, int format, int width,
				int height) {
		}
	};

	private Callback<SurfaceHolder> mCreateCallback;

	public void create(Callback<SurfaceHolder> callback) {
		if (mSurfaceCreated) {
			callback.run(mSurfaceView.getHolder());
			return;
		}
		mCreateCallback = callback;
	}

	@SuppressLint("NewApi")
	private void createUIComponents() {
		// setAlpha(0.5f);
		final Context context = getContext();
		mSurfaceView = new VideoSurfaceView(context);
		mSurfaceView.getHolder().addCallback(mSurfaceHolderCallback);

		mSeekBar = new SeekBar(context);
		mSeekBar.setMax(maxProgress());

		mPause = VF.of(context, ViewID.Pause);
		mResume = VF.of(context, ViewID.Resume);
		mStart = VF.of(context, ViewID.Start);
		mStop = VF.of(context, ViewID.Stop);
		mPre = VF.of(context, ViewID.Pre);
		mNext = VF.of(context, ViewID.Next);
		mFastForward = VF.of(context, ViewID.FastForward);
		mFastBackward = VF.of(context, ViewID.FastBackward);

		mEnterFullScreen = VF.of(context, ViewID.EnterFullScreen);
		mExitFullScreen = VF.of(context, ViewID.ExitFullScreen);

		mEndTime = new TextView(context);
		mCurrentTime = new TextView(context);
		mLoadingText = new TextView(context);
	}

	private void setupListeners() {
		mSeekBar.setOnSeekBarChangeListener(mOnSeekBarChangeListener);
		mPause.setOnClickListener(mOnClickListener);
		mResume.setOnClickListener(mOnClickListener);
		mStart.setOnClickListener(mOnClickListener);
		mStop.setOnClickListener(mOnClickListener);
		mPre.setOnClickListener(mOnClickListener);
		mNext.setOnClickListener(mOnClickListener);
		mFastForward.setOnClickListener(mOnClickListener);
		mFastBackward.setOnClickListener(mOnClickListener);
		mEnterFullScreen.setOnClickListener(mOnClickListener);
		mExitFullScreen.setOnClickListener(mOnClickListener);
	}

	protected void initUIContent() {
		mEndTime.setTextColor(Theme.textColor());
		mCurrentTime.setTextColor(Theme.textColor());
	}

	protected void initUIState() {
		mExitFullScreen.setVisibility(GONE);
	}

	protected abstract void setupLayout();

	protected int maxProgress() {
		return 100;
	}

	public void setMPController(IMediaPlayerController mediaPlayerController) {
		mMediaPlayerController = mediaPlayerController;
		mMediaPlayerController.client(this);
	}

	private OnSeekBarChangeListener mOnSeekBarChangeListener = new OnSeekBarChangeListener() {
		@Override
		public void onStopTrackingTouch(SeekBar seekBar) {
		}

		@Override
		public void onStartTrackingTouch(SeekBar seekBar) {
		}

		@Override
		public void onProgressChanged(SeekBar seekBar, int progress,
				boolean fromUser) {
			if (fromUser) {
				mMediaPlayerController.seek(progress, maxProgress());
			}
		}
	};

	private OnClickListener mOnClickListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			assert (mMediaPlayerController != null);
			if (v == mPause) {
				mMediaPlayerController.pause();
			} else if (v == mResume) {
				mMediaPlayerController.resume();
			} else if (v == mStart) {
				mMediaPlayerController.start();
			} else if (v == mStop) {
				mMediaPlayerController.stop();
			} else if (v == mPre) {
				mMediaPlayerController.pre();
			} else if (v == mNext) {
				mMediaPlayerController.next();
			} else if (v == mFastForward) {
				mMediaPlayerController.fastForward();
			} else if (v == mFastBackward) {
				mMediaPlayerController.fastBackward();
			} else if (v == mEnterFullScreen) {
				mMediaPlayerController.enterFullScreen();
			} else if (v == mExitFullScreen) {
				mMediaPlayerController.exitFullScreen();
			}
		}
	};

	@Override
	public void onStop() {
		mStart.setVisibility(VISIBLE);
		mStop.setVisibility(GONE);
		mPause.setVisibility(GONE);
		mResume.setVisibility(GONE);
	}

	@Override
	public void onStart() {
		mStart.setVisibility(GONE);
		mStop.setVisibility(VISIBLE);
		mPause.setVisibility(VISIBLE);
		mResume.setVisibility(GONE);
	}

	@Override
	public void onSizeChanged(int w, int h) {
		mSurfaceView.setSize(w, h);
	}

	@Override
	public void onResume() {
		mPause.setVisibility(VISIBLE);
		mResume.setVisibility(GONE);
	}

	@Override
	public void onPause() {
		mPause.setVisibility(GONE);
		mResume.setVisibility(VISIBLE);
	}

	@Override
	public void onProgressChanged(int progress) {
		mSeekBar.setProgress(progress);
	}

	@Override
	public void onLoadStart(String msg) {
		mLoadingText.setText(msg);
		mLoadingText.setVisibility(VISIBLE);
	}

	@Override
	public void onLoadStop() {
		mLoadingText.setText(null);
		mLoadingText.setVisibility(GONE);
	}

	@Override
	public void onError(int error) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onReset() {
		onStop();
		onProgressChanged(0);
	}

	protected IMetaInfo mMediaMetaInfo;

	@Override
	public void onMetaInfo(IMetaInfo metaInfo) {
		mMediaMetaInfo = metaInfo;
		mSurfaceView.setSize(metaInfo.width(), metaInfo.height());
	}

	@Override
	public void onEnterFullScreen() {
		mEnterFullScreen.setVisibility(GONE);
		mExitFullScreen.setVisibility(VISIBLE);
	}

	@Override
	public void onExitFullScreen() {
		mEnterFullScreen.setVisibility(VISIBLE);
		mExitFullScreen.setVisibility(GONE);
	}

	@Override
	public void onPre() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onCanPre(boolean flag) {
		mPre.setEnabled(flag);
	}

	@Override
	public void onNext() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onCanNext(boolean flag) {
		mNext.setEnabled(flag);
	}

	@Override
	public void onFastFward() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onFastBackward() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onComplete() {
		mStart.setVisibility(VISIBLE);
		mStop.setVisibility(GONE);
		mPause.setVisibility(GONE);
		mResume.setVisibility(VISIBLE);
		mSeekBar.setProgress(0);
	}

	@Override
	public void onReceivedTimeInfo(String total, String cur) {
		mEndTime.setText(total);
		mCurrentTime.setText(cur);
	}

	// @Override
	// public void onConfigurationChanged(Configuration newConfig) {
	// super.onConfigurationChanged(newConfig);
	// requestLayout();
	// }
	// @Override
	// public void dispatchConfigurationChanged(Configuration newConfig) {
	// super.dispatchConfigurationChanged(newConfig);
	// requestLayout();
	// forceLayout();
	// }
}
