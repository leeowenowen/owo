package com.owo.ui.shape;

import java.util.HashMap;

import android.content.Context;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.StateListDrawable;
import android.view.View;

import com.owo.app.theme.Theme;

/**
 * View Factory
 */
public class VF {
	public static enum ViewID {
		Start, //
		Stop, //
		Pause, //
		Resume, //
		FastForward, //
		FastBackward, //
		Pre, //
		Next, //
		EnterFullScreen, //
		ExitFullScreen, //
		Lock, //
		UnLock, //
		Search, //
		Menu, //
	}

	private static HashMap<ViewID, HalfRectShape> mShapes;
	private static HashMap<ViewID, HalfRectShape> mDarkShapes;

	private static void ensure() {
		if (mShapes == null) {
			mShapes = new HashMap<VF.ViewID, HalfRectShape>();
			mShapes.put(ViewID.Start, new StartShape());
			mShapes.put(ViewID.Stop, new StopShape());
			mShapes.put(ViewID.Pause, new StopShape());
			mShapes.put(ViewID.Resume, new StartShape());
			mShapes.put(ViewID.FastForward, new PreShape());
			mShapes.put(ViewID.FastBackward, new PreShape());
			mShapes.put(ViewID.Pre, new PreShape());
			mShapes.put(ViewID.Next, new NextShape());
			mShapes.put(ViewID.EnterFullScreen, new EnterFullScreenShape());
			mShapes.put(ViewID.ExitFullScreen, new ExitFullScreenShape());
			mShapes.put(ViewID.Lock, new LockShape());
			mShapes.put(ViewID.UnLock, new UnLockShape());
			mShapes.put(ViewID.Search, new SearchShape());
			mShapes.put(ViewID.Menu, new MenuShape());

			mDarkShapes = new HashMap<VF.ViewID, HalfRectShape>();
			mDarkShapes.put(ViewID.Start, new StartShape());
			mDarkShapes.put(ViewID.Stop, new StopShape());
			mDarkShapes.put(ViewID.Pause, new StopShape());
			mDarkShapes.put(ViewID.Resume, new StartShape());
			mDarkShapes.put(ViewID.FastForward, new PreShape());
			mDarkShapes.put(ViewID.FastBackward, new PreShape());
			mDarkShapes.put(ViewID.Pre, new PreShape());
			mDarkShapes.put(ViewID.Next, new NextShape());
			mDarkShapes.put(ViewID.EnterFullScreen, new EnterFullScreenShape());
			mDarkShapes.put(ViewID.ExitFullScreen, new ExitFullScreenShape());
			mDarkShapes.put(ViewID.Lock, new LockShape());
			mDarkShapes.put(ViewID.UnLock, new UnLockShape());
			mDarkShapes.put(ViewID.Search, new SearchShape());
			mDarkShapes.put(ViewID.Menu, new MenuShape());
		}
	}

	public static View of(Context context, ViewID id) {
		ensure();
		return create(context, id);
	}

	@SuppressWarnings("deprecation")
	private static View create(Context context, ViewID id) {
		View view = new View(context);
		StateListDrawable drawable = new StateListDrawable();
		HalfRectShape shape = mDarkShapes.get(id);
		shape.bgColor(Theme.maskedBgColor());
		ShapeDrawable pressed = new ShapeDrawable(shape);
		drawable.addState(new int[] { android.R.attr.state_pressed }, pressed);
		drawable.addState(new int[] {}, new ShapeDrawable(mShapes.get(id)));
		view.setBackgroundDrawable(drawable);
		return view;
	}
}
