package com.owo.mediaplayer.ui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

import com.owo.app.theme.Theme;
import com.owo.base.common.Callback;
import com.owo.base.pattern.Singleton;
import com.owo.mediaplayer.core.VideoSurfaceView;
import com.owo.mediaplayer.interfaces.IMediaPlayerController;
import com.owo.mediaplayer.interfaces.IMetaInfo;
import com.owo.ui.shape.VF;
import com.owo.ui.shape.VF.ViewID;

public abstract class AbsMediaPlayerWidget extends FrameLayout implements
		IMediaPlayerController.Client {

	private static final String TAG = "AbsMediaPlayerWidget";
	protected IMediaPlayerController mController;

	protected VideoSurfaceView mSurfaceView;

	protected SeekBar mSeekBar;
	protected View mPause, mResume;
	protected View mStart, mStop;
	protected View mPre, mNext;
	protected View mFastForward, mFastBackward;
	protected View mEnterFullScreen, mExitFullScreen;
	protected View mLock, mUnLock;
	protected TextView mEndTime, mCurrentTime;
	protected TextView mLoadingText;
	protected TextView mTitle;
	protected ProgressBar mLoadingBar;

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
			Log.v(TAG, "surfaceDestroyed");
			mController.detachSurface();
		}

		@Override
		public void surfaceCreated(SurfaceHolder holder) {
			Log.v(TAG, "surfaceCreated");
			holder.setKeepScreenOn(true);
			if (mCreateCallback != null) {
				mCreateCallback.run(holder);
				mCreateCallback = null;
			}
			mController.attachSurface(holder);
		}

		@Override
		public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
			Log.v(TAG, "surfaceDestroyed[format:" + format + "][width:" + width + "][height:"
					+ height + "]");
		}
	};

	private Callback<SurfaceHolder> mCreateCallback;

	public void createSurfaceView(Callback<SurfaceHolder> callback) {
		if (mSurfaceView != null) {
			removeView(mSurfaceView);
		}
		mCreateCallback = callback;
		mSurfaceView = new VideoSurfaceView(getContext());
		mSurfaceView.getHolder().addCallback(mSurfaceHolderCallback);
		addView(mSurfaceView, 0, new FrameLayout.LayoutParams(
				FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT));
	}

	@SuppressLint("NewApi")
	private void createUIComponents() {
		final Context context = getContext();

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

		mLock = VF.of(context, ViewID.Lock);
		mUnLock = VF.of(context, ViewID.UnLock);

		mEndTime = new TextView(context);
		mCurrentTime = new TextView(context);
		mTitle = new TextView(context);
		mLoadingText = new TextView(context);
		mLoadingBar = new ProgressBar(context);
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
		int textColor = Singleton.of(Theme.class).textColor();
		mEndTime.setTextColor(textColor);
		mCurrentTime.setTextColor(textColor);
	}

	protected void initUIState() {
		mExitFullScreen.setVisibility(GONE);
	}

	protected abstract void setupLayout();

	protected int maxProgress() {
		return 100;
	}

	public void setMPController(IMediaPlayerController mediaPlayerController) {
		mController = mediaPlayerController;
		mController.client(this);
	}

	protected boolean mIsDraggingSeekBar;
	private OnSeekBarChangeListener mOnSeekBarChangeListener = new OnSeekBarChangeListener() {
		@Override
		public void onStopTrackingTouch(SeekBar seekBar) {
			mIsDraggingSeekBar = false;
		}

		@Override
		public void onStartTrackingTouch(SeekBar seekBar) {
			mIsDraggingSeekBar = true;
		}

		@Override
		public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
			if (fromUser) {
				mController.seek(progress, maxProgress());
			}
		}
	};

	private OnClickListener mOnClickListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			assert (mController != null);
			if (v == mPause) {
				mController.pause();
			} else if (v == mResume) {
				mController.resume();
			} else if (v == mStart) {
				mController.start();
			} else if (v == mStop) {
				mController.stop();
			} else if (v == mPre) {
				mController.pre();
			} else if (v == mNext) {
				mController.next();
			} else if (v == mFastForward) {
				mController.fastForward();
			} else if (v == mFastBackward) {
				mController.fastBackward();
			} else if (v == mEnterFullScreen) {
				mController.enterFullScreen();
			} else if (v == mExitFullScreen) {
				mController.exitFullScreen();
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
		if (metaInfo.width() > 0 && metaInfo.height() > 0) {
			mSurfaceView.setSize(metaInfo.width(), metaInfo.height());
		}
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
