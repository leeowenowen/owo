package com.owo.ui.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.view.View;

import com.owo.base.util.DimensionUtil;

public class ShapeImageView extends View {
	public static final int TYPE_CIRCLE = 0;
	public static final int TYPE_ROUND_RECT = 1;

	private final int mType;

	public ShapeImageView(Context context) {
		this(context, TYPE_CIRCLE);
	}

	public ShapeImageView(Context context, int type) {
		super(context);
		mType = type;
		init();
	}

	private Bitmap mBgBitmap;
	private Rect mBitmapRect = new Rect();
	private PaintFlagsDrawFilter mDrawFilter = new PaintFlagsDrawFilter(0, Paint.ANTI_ALIAS_FLAG
			| Paint.FILTER_BITMAP_FLAG);
	private Paint mPaint;
	private Bitmap mDestBitmap = null;
	private PorterDuffXfermode mXfermode = new PorterDuffXfermode(PorterDuff.Mode.SRC_IN);

	@SuppressLint("NewApi")
	private void init() {
		try {
			if (android.os.Build.VERSION.SDK_INT >= 11) {
				setLayerType(LAYER_TYPE_SOFTWARE, null);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		mPaint = new Paint();
		mPaint.setStyle(Paint.Style.STROKE);
		mPaint.setFlags(Paint.ANTI_ALIAS_FLAG);
		mPaint.setAntiAlias(true);
	}

	public void setImageBitmap(Bitmap bitmap) {
		mBgBitmap = bitmap;
	}

	private Bitmap makeDst(int w, int h) {
		Bitmap bg = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
		Canvas canvas = new Canvas(bg);
		Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
		paint.setColor(Color.parseColor("#ffffffff"));
		switch (mType) {
		case TYPE_CIRCLE:
			canvas.drawOval(new RectF(0, 0, w, h), paint);
			break;
		case TYPE_ROUND_RECT:
			canvas.drawRoundRect(new RectF(0, 0, w, h), DimensionUtil.w(10), DimensionUtil.w(10),
					paint);
			break;
		default:
			break;
		}

		return bg;
	}

	@Override
	protected void onDraw(Canvas canvas) {

		if (null == mBgBitmap) {
			return;
		}

		if (null == mDestBitmap) {
			mDestBitmap = makeDst(getWidth(), getHeight());
		}

		mBitmapRect.set(0, 0, getWidth(), getHeight());
		canvas.save();
		canvas.setDrawFilter(mDrawFilter);
		canvas.drawBitmap(mDestBitmap, 0, 0, mPaint);
		mPaint.setXfermode(mXfermode);
		canvas.drawBitmap(mBgBitmap, null, mBitmapRect, mPaint);
		mPaint.setXfermode(null);
		canvas.restore();
	}
}
