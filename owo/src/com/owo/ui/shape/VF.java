package com.owo.ui.shape;

import java.util.HashMap;

import android.content.Context;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.Shape;
import android.view.View;

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

	private static HashMap<ViewID, View> mViews = new HashMap<VF.ViewID, View>();
	private static HashMap<ViewID, Shape> mShapes;

	private static void ensure() {
		if (mShapes == null) {
			mShapes = new HashMap<VF.ViewID, Shape>();
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
		}
	}

	public static View of(Context context, ViewID id) {
		ensure();
		View v = mViews.get(id);
		if (v == null) {
			v = create(context, mShapes.get(id));
		}
		return v;
	}

	@SuppressWarnings("deprecation")
	private static View create(Context context, Shape shape) {
		View view = new View(context);
		view.setBackgroundDrawable(new ShapeDrawable(shape));
		// StateListDrawable drawable = new StateListDrawable();
		// drawable.addState(new int[] { android.R.attr.state_pressed }, null);
		// drawable.addState(new int[] {}, makeShapeDrawable(shape));

		return view;
	}
}
